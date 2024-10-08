package other.org.luaj.vm2.customs.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import other.org.luaj.vm2.customs.EventHook;
import pa.centric.events.Event;
import pa.centric.events.impl.render.EventRender;

public class EventRenderHook extends EventHook {

    private EventRender render;

    public EventRenderHook(Event event) {
        super(event);
        this.render = (EventRender) event;
    }

    public MatrixStack getMatrixStack() {
        return render.matrixStack;
    }

    public boolean is2D() {
        return render.isRender2D();
    }

    public boolean is3D() {
        return render.isRender3D();
    }

    public float getPartialTicks() {
        return render.partialTicks;
    }

    @Override
    public String getName() {
        return "render_event";
    }
}
