package pa.centric.client.modules.impl.player;

import pa.centric.client.modules.Function;
import pa.centric.client.modules.ModuleAnnotation;
import pa.centric.client.modules.Type;
import pa.centric.client.modules.settings.imp.BooleanOption;
import pa.centric.client.modules.settings.imp.MultiBoxSetting;
import pa.centric.events.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author JohON0
 */
@ModuleAnnotation(name = "NoInteract", category = Type.Player)
public class NoInteractFunction extends Function {
    public BooleanOption allBlocks = new BooleanOption("��� �����", false);
    public MultiBoxSetting ignoreInteract = new MultiBoxSetting("�������",
            new BooleanOption("������", true),
            new BooleanOption("�������", true),
            new BooleanOption("�����", true),
            new BooleanOption("������", true),
            new BooleanOption("�������", true),
            new BooleanOption("����������", true),
            new BooleanOption("������ �����", true),
            new BooleanOption("��������", true),
            new BooleanOption("����", true),
            new BooleanOption("�����", true),
            new BooleanOption("�������", true),
            new BooleanOption("����������", true),
            new BooleanOption("������", true)).setVisible(() -> !allBlocks.get());

    public NoInteractFunction() {
        addSettings(allBlocks, ignoreInteract);
    }


    public Set<Integer> getBlocks() {
        Set<Integer> blocks = new HashSet<>();
        addBlocksForInteractionType(blocks, 1, 147, 329, 270);
        addBlocksForInteractionType(blocks, 2, 173, 161, 485, 486, 487, 488, 489, 720, 721);
        addBlocksForInteractionType(blocks, 3, 183, 308, 309, 310, 311, 312, 313, 718, 719, 758);
        addBlocksForInteractionType(blocks, 4, 336);
        addBlocksForInteractionType(blocks, 5, 70, 342, 508);
        addBlocksForInteractionType(blocks, 6, 74);
        addBlocksForInteractionType(blocks, 7, 151);
        addBlocksForInteractionType(blocks, 8, 222, 223, 224, 225, 226, 227, 712, 713, 379);
        addBlocksForInteractionType(blocks, 9, 154, 670);
        addBlocksForInteractionType(blocks, 10, 250, 475, 476, 477, 478, 479, 714, 715);
        addBlocksForInteractionType(blocks, 11, 328, 327, 326);
        addBlocksForInteractionType(blocks, 12, 171);
        return blocks;
    }

    private void addBlocksForInteractionType(Set<Integer> blocks, int interactionType, Integer... blockIds) {
        if (ignoreInteract.get(interactionType)) {
            blocks.addAll(Arrays.asList(blockIds));
        }
    }

    @Override
    public void onEvent(Event event) {

    }
}
