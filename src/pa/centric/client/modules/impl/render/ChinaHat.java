package pa.centric.client.modules.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import org.lwjgl.opengl.GL11;
import pa.centric.client.modules.Function;
import pa.centric.client.modules.ModuleAnnotation;
import pa.centric.client.modules.Type;
import pa.centric.client.helper.conduction;
import pa.centric.util.render.RenderUtils;
import pa.centric.events.Event;
import pa.centric.events.impl.render.EventRender;

import static java.lang.Math.*;

@ModuleAnnotation(name = "ChinaHat", category = Type.Render)
public class ChinaHat extends Function {

    @Override
    public void onEvent(Event event) {
        if (mc.player == null || mc.world == null) return;

        if (event instanceof EventRender e && e.isRender3D()) {

            if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                return;
            }

            EntityRendererManager rm = mc.getRenderManager();

            double x = mc.player.lastTickPosX + (mc.player.getPosX() - mc.player.lastTickPosX) * e.partialTicks - rm.info.getProjectedView().getX();
            double y = mc.player.lastTickPosY + (mc.player.getPosY() - mc.player.lastTickPosY) * e.partialTicks - rm.info.getProjectedView().getY() + 0.03;
            double z = mc.player.lastTickPosZ + (mc.player.getPosZ() - mc.player.lastTickPosZ) * e.partialTicks - rm.info.getProjectedView().getZ();

            float height = mc.player.getHeight();
            RenderSystem.pushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.shadeModel(GL11.GL_SMOOTH);
            RenderSystem.disableCull();

            RenderSystem.lineWidth(1.5f);
            RenderSystem.color4f(-1f, -1f, -1f, -1f);

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);

            float[] colors = null;

            for (int i = 0; i <= 360; i++) {
                colors = RenderUtils.IntColor.rgb(conduction.STYLE_MANAGER.getCurrentStyle().getColor(i));

                buffer.pos(x, y + height + 0.2, z)
                        .color(colors[0], colors[1], colors[2], 0.5F).endVertex();
                buffer.pos(x + cos(toRadians(i)) * 0.5, y + height, z + sin(toRadians(i)) * 0.5)
                        .color(colors[0], colors[1], colors[2], 0.5F).endVertex();
            }

            buffer.finishDrawing();
            WorldVertexBufferUploader.draw(buffer);
            RenderSystem.color4f(-1f, -1f, -1f, -1f);

            buffer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);

            for (int i = 0; i <= 360; i++) {
                buffer.pos(x + cos(toRadians(i)) * 0.5, y + height, z + sin(toRadians(i)) * 0.5)
                        .color(colors[0], colors[1], colors[2], 0.5F).endVertex();
            }

            buffer.finishDrawing();
            WorldVertexBufferUploader.draw(buffer);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            RenderSystem.enableAlphaTest();
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            RenderSystem.shadeModel(GL11.GL_FLAT);
            RenderSystem.popMatrix();



        }
    }
}
