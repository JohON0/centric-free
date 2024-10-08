package pa.centric.client.modules.impl.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import pa.centric.client.modules.Function;
import pa.centric.client.modules.ModuleAnnotation;
import pa.centric.client.modules.Type;
import pa.centric.events.Event;
import pa.centric.events.impl.player.EventUpdate;

@ModuleAnnotation(name = "AutoTool", category = Type.Player)
public class AutoTool extends Function {

    private int oldSlot = -1;
    private boolean status;

    @Override
    public void onEvent(final Event event) {
        if (mc.player == null || mc.world == null) return;

        if (event instanceof EventUpdate) {
            if (mc.objectMouseOver != null && mc.gameSettings.keyBindAttack.pressed) {
                int bestSlot = findBestSlot();

                if (bestSlot == -1) return;

                status = true;

                if (oldSlot == -1) oldSlot = mc.player.inventory.currentItem;

                mc.player.inventory.currentItem = findBestSlot();
            } else if (status) {
                mc.player.inventory.currentItem = oldSlot;
                reset();
            }
        }
    }

    private void reset() {
        oldSlot = -1;
        status = false;
    }

    private int findBestSlot() {
        if (mc.objectMouseOver instanceof BlockRayTraceResult) {
            BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) mc.objectMouseOver;
            Block block = mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock();

            int bestSlot = -1;
            float bestSpeed = 1.0f;

            for (int slot = 0; slot < 9; slot++) {
                ItemStack stack = mc.player.inventory.getStackInSlot(slot);
                float speed = stack.getDestroySpeed(block.getDefaultState());

                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = slot;
                }
            }
            return bestSlot;
        }
        return -1;
    }

    @Override
    protected void onDisable() {
        reset();
        super.onDisable();
    }
}
