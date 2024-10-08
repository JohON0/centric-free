package pa.centric.client.modules.impl.render;

import net.minecraft.block.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import pa.centric.Centric;
import pa.centric.client.modules.Function;
import pa.centric.client.modules.ModuleAnnotation;
import pa.centric.client.modules.Type;
import pa.centric.client.modules.settings.imp.BooleanOption;
import pa.centric.client.modules.settings.imp.MultiBoxSetting;
import pa.centric.client.modules.settings.imp.SliderSetting;
import pa.centric.events.impl.packet.EventPacket;
import pa.centric.events.impl.player.EventInput;
import pa.centric.events.impl.player.EventMotion;
import pa.centric.events.impl.render.EventRender;
import pa.centric.util.IMinecraft;
import pa.centric.util.font.FontUtils;
import pa.centric.util.render.ColorUtil;
import pa.centric.util.render.RenderUtils;
import pa.centric.events.Event;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


// pasta
@ModuleAnnotation(name = "XRay", category = Type.Render)
public class XRayFunction extends Function {

    CopyOnWriteArrayList<BlockPos> waiting = new CopyOnWriteArrayList<>();

    public SliderSetting up = new SliderSetting("�����", 5, 0, 30, 1);

    public SliderSetting down = new SliderSetting("����", 5, 0, 30, 1);
    public SliderSetting radius = new SliderSetting("������", 20, 0, 30, 1);
    public SliderSetting delay = new SliderSetting("��������", 13, 0, 40, 1);
    public SliderSetting skip = new SliderSetting("������� ������", 3, 1, 5, 1);


    public MultiBoxSetting ores = new MultiBoxSetting("��������",
            new BooleanOption("�����", false),
            new BooleanOption("������", false),
            new BooleanOption("��������", false),
            new BooleanOption("������", false),
            new BooleanOption("��������", false),
            new BooleanOption("�����", false),
            new BooleanOption("�������", false),
            new BooleanOption("�������", false)
            );

    public XRayFunction() {
        addSettings(radius, up, down, delay,skip, ores);
    }

    CopyOnWriteArrayList<BlockPos> clicked = new CopyOnWriteArrayList<>();

    BlockPos clicking;
    Thread thread;

    @Override
    protected void onDisable() {
        super.onDisable();
        if (thread != null) {
            thread.interrupt();
            thread.stop();
        }
        clicking = null;
        clicked.clear();
        waiting.clear();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        waiting = removeEveryOther(getBlocks(), skip.getValue().intValue());
        thread = new Thread(() -> {
            if (IMinecraft.mc.player != null) {
                for (BlockPos click : waiting) {
                    IMinecraft.mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, click, Direction.UP));
                    clicked.add(click);
                    clicking = click;
                    try {
                        Thread.sleep(delay.getValue().intValue());
                    } catch (InterruptedException e) {
                        System.out.println(Centric.prefix + "Anti Xray: " + e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }

    private int isValid(BlockPos pos) {

        BlockState state = IMinecraft.mc.world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof OreBlock ore) {
            if (ore == Blocks.COAL_ORE && ores.get(0)) {
                return ColorUtil.rgba(12, 12, 12, 255);
            }
            if (ore == Blocks.IRON_ORE && ores.get(1)) {
                return ColorUtil.rgba(122, 122, 122, 255);
            }
            if (ore == Blocks.REDSTONE_ORE && ores.get(2)) {
                return ColorUtil.rgba(255, 82, 82, 255);
            }
            if (ore == Blocks.GOLD_ORE && ores.get(3)) {
                return ColorUtil.rgba(247, 255, 102, 255);
            }
            if (ore == Blocks.EMERALD_ORE && ores.get(4)) {
                return ColorUtil.rgba(116, 252, 101, 255);
            }
            if (ore == Blocks.DIAMOND_ORE && ores.get(5)) {
                return ColorUtil.rgba(77, 219, 255, 255);
            }
            if (ore == Blocks.ANCIENT_DEBRIS && ores.get(6)) {
                return ColorUtil.rgba(105, 60, 12, 255);
            }
        }
        if (block == Blocks.CHEST && ores.get(7)) {
            return ColorUtil.rgba(255, 255, 255, 255);
        }
        return -1;
    }

    private float animation;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventInput e) {
            if (thread != null) {
                if (thread.isAlive()) {
                    e.setStrafe(0);
                    e.setForward(0);
                    e.setJump(false);
                    e.setSneak(false);
                }
            }
        }


        if (event instanceof EventPacket e) {
            if (thread != null) {
                if (thread.isAlive()) {
                    if (e.isSendPacket()) {
                        if (e.getPacket() instanceof CPlayerPacket) {
                            e.setCancel(true);
                        }
                        if (e.getPacket() instanceof CAnimateHandPacket) {
                            e.setCancel(true);
                        }
                        if (e.getPacket() instanceof CPlayerTryUseItemPacket) {
                            e.setCancel(true);
                        }
                        if (e.getPacket() instanceof CHeldItemChangePacket) {
                            e.setCancel(true);
                        }
                    }
                }
            }
        }
        if (event instanceof EventMotion e) {
            if (thread != null) {
                if (thread.isAlive()) {
                    e.setCancel(true);
                }
            }
        }
        if (event instanceof EventRender e) {

            if (e.isRender2D()) {
                float width = 100;
                float heigth = 15;

                RenderUtils.Render2D.drawRoundedCorner(e.scaledResolution.getScaledWidth() / 2f - width / 2, 10, width, heigth, 4, ColorUtil.rgba(10, 10, 10, 128));
                float x = e.scaledResolution.getScaledWidth() / 2f - width / 2;
                FontUtils.sfbold[16].drawString(e.matrixStack, clicked.size() + "/" + waiting.size(), x + 5, 15, -1);

                long millis = (waiting.size() * delay.getValue().intValue()) - clicked.size() * delay.getValue().intValue();

                String time = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                ;

                FontUtils.sfbold[16].drawString(e.matrixStack, time, x + width - FontUtils.sfbold[16].getWidth(time) - 5, 15, -1);
            }

            if (e.isRender3D()) {
                if (clicked != null && clicking != null) {
                    RenderUtils.Render3D.drawBlockBox(clicking, ColorUtil.rgba(83, 252, 154, 255));
                    for (BlockPos click : clicked) {
                        int color = isValid(click);
                        if (color != -1) {
                            RenderUtils.Render3D.drawBlockBox(click, color);
                        }
                    }
                }
            }
        }
    }

    private static <T> CopyOnWriteArrayList<T> removeEveryOther(CopyOnWriteArrayList<T> inList, int offset) {
        if (offset == 1) return inList;
        CopyOnWriteArrayList<T> outList = new CopyOnWriteArrayList<>();
        @SuppressWarnings("unchecked")
        T[] ts = (T[]) inList.toArray();
        for (int i = 0; i < ts.length; i++) {
            if (i % offset == 0) {
                outList.add(ts[i]);
            }
        }
        return outList;
    }

    CopyOnWriteArrayList<BlockPos> getBlocks() {
        CopyOnWriteArrayList<BlockPos> blocks = new CopyOnWriteArrayList<>();
        BlockPos start = IMinecraft.mc.player.getPosition();
        int dis = radius.getValue().intValue();
        int up = this.up.getValue().intValue();
        int down = this.down.getValue().intValue();
        for (int y = up; y >= -down; y--)
            for (int x = dis; x >= -dis; x--)

                for (int z = dis; z >= -dis; z--) {
                    BlockPos pos = start.add(x, y, z);

                    if (pos.getY() > 0) {
                        Block block = IMinecraft.mc.world.getBlockState(pos).getBlock();
                        if (block instanceof AirBlock) continue;
                        blocks.add(pos);
                    }

                }
        Collections.shuffle(blocks);
        return blocks;
    }

}
