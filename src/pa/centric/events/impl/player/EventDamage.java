package pa.centric.events.impl.player;

import pa.centric.events.Event;

public class EventDamage extends Event {
    private final DamageType damageType;

    public EventDamage(DamageType damageType) {
        this.damageType = damageType;
    }

    public DamageType getDamageType() {
        return this.damageType;
    }

    public enum DamageType {
        FALL,
        ARROW,
        ENDER_PEARL;
    }
}
