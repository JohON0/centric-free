package pa.centric.events.impl.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import pa.centric.events.Event;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EventPlaceAnchorByPlayer extends Event {

    private final Block block;
    private final BlockPos pos;

}
