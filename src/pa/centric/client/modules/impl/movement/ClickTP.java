package pa.centric.client.modules.impl.movement;

import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import pa.centric.client.modules.Function;
import pa.centric.client.modules.ModuleAnnotation;
import pa.centric.client.modules.Type;
import pa.centric.events.Event;
import pa.centric.events.impl.game.EventMouseTick;
import pa.centric.events.impl.render.EventRender;
import pa.centric.util.render.ColorUtil;
import pa.centric.util.render.RenderUtils;

@ModuleAnnotation(name = "Click TP", category = Type.Movement)
public class ClickTP extends Function {

    private BlockRayTraceResult result;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMouseTick e) {
            if (e.getButton() == 2 && result != null) {
                Vector3d vec = Vector3d.copyCenteredHorizontally(result.getPos().up());

                double x = vec.x;
                double y = vec.y;
                double z = vec.z;

                for (int i = 0; i < 5; i++) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                }
                for (int i = 0; i < 10; i++) {
                    mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
                }
            }
        }

        if (event instanceof EventRender) {
            result = (BlockRayTraceResult) mc.player.pick(100, 1, false);
            if (result.getType() == RayTraceResult.Type.MISS) result = null;
            if (result != null)
                RenderUtils.Render3D.drawBlockBox(result.getPos(), ColorUtil.rgba(128,255,128,255));
        }
    }
}
