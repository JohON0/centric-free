package pa.centric.events.impl.packet;

import net.minecraft.network.IPacket;
import pa.centric.events.Event;

public class EventPacket extends Event {

    private IPacket packet;

    private final PacketType packetType;

    public EventPacket(IPacket packet, PacketType packetType) {
        this.packet = packet;
        this.packetType = packetType;
    }

    public IPacket getPacket() {
        return packet;
    }

    public void setPacket(IPacket packet) {
        this.packet = packet;
    }

    public boolean isReceivePacket() {
        return this.packetType == PacketType.RECEIVE;
    }

    public boolean isSendPacket() {
        return this.packetType == PacketType.SEND;
    }

    public enum PacketType {
        SEND, RECEIVE
    }
}
