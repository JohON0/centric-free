package pa.centric.client.modules;

import lombok.Getter;
import pa.centric.client.modules.impl.combat.*;
import pa.centric.client.modules.impl.movement.*;
import pa.centric.client.modules.impl.player.*;
import pa.centric.client.modules.impl.render.*;
import pa.centric.client.modules.impl.util.*;
import pa.centric.client.modules.impl.util.NoCommands;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FunctionManager {

    @Getter
    private final List<Function> functions = new CopyOnWriteArrayList<>();
    public final ArrowsFunction arrowsFunction;
    public final FullBrightFunction fullBrightFunction;
    public final SprintFunction sprintFunction;
    public final FlightFunction flightFunction;
    public final StrafeFunction strafeFunction;
    public final TimerFunction timerFunction;
    public final AutoPotionFunction autoPotionFunction;
    public final AutoRespawnFunction autoRespawnFunction;
    public final VelocityFunction velocityFunction;
    public final MiddleClickPearlFunction middleClickPearlFunction;
    public final AutoTotemFunction autoTotemFunction;
    public final InventoryMoveFunction inventoryMoveFunction;
    public final NoPushFunction noPushFunction;
    public final HitBoxFunction hitBoxFunction;
    public final Hider hider;
    public final NoSlowFunction noSlowFunction;
    public final AuraFunction auraFunction;
    public final FastBreakFunction fastBreakFunction;
    public final XRayFunction xRayFunction;
    public final SwingAnimationFunction swingAnimationFunction;
    public final AutoGAppleFunction autoGApple;
    public final NoRenderFunction noRenderFunction;
    public final GappleCooldownFunction gappleCooldownFunction;
    public final Optimization optimization;
    public final ItemScroller itemScroller;
    public final NameTags espFunction;
    public final NoInteractFunction noInteractFunction;
    public final CustomWorld customWorld;
    public final ClientSounds clientSounds;
    public final Crosshair crosshair;
    public final NameProtect nameProtect;
    public final NoCommands noCommands;
    public final UnHookFunction unhook;

    public final AutoExplosionFunction autoExplosionFunction;

    public final HitColor hitColor;
    public final FreeCam freeCam;

    public final ClickGui clickGui;
    public final ChestStealer chestStealer;
    public Hud hud;

    public FunctionManager() {
        //  ���������� ������� � ������ �������
        this.functions.addAll(Arrays.asList(
                this.clickGui = new ClickGui(),
                this.crosshair = new Crosshair(),
                this.arrowsFunction = new ArrowsFunction(),
                this.chestStealer = new ChestStealer(),
                this.fullBrightFunction = new FullBrightFunction(),
                this.noRenderFunction = new NoRenderFunction(),
                this.sprintFunction = new SprintFunction(),
                this.flightFunction = new FlightFunction(),
                this.hider = new Hider(),
                new LeaveTracker(),
                this.strafeFunction = new StrafeFunction(),
                this.timerFunction = new TimerFunction(),
                this.velocityFunction = new VelocityFunction(),
                this.middleClickPearlFunction = new MiddleClickPearlFunction(),
                this.autoTotemFunction = new AutoTotemFunction(),
                this.inventoryMoveFunction = new InventoryMoveFunction(),
                this.autoRespawnFunction = new AutoRespawnFunction(),
                this.noPushFunction = new NoPushFunction(),
                this.hitBoxFunction = new HitBoxFunction(),
                this.noSlowFunction = new NoSlowFunction(),
                this.fastBreakFunction = new FastBreakFunction(),
                this.xRayFunction = new XRayFunction(),
                this.autoPotionFunction = new AutoPotionFunction(),
                this.swingAnimationFunction = new SwingAnimationFunction(),
                this.autoGApple = new AutoGAppleFunction(),
                this.gappleCooldownFunction = new GappleCooldownFunction(),
                this.optimization = new Optimization(),
                this.itemScroller = new ItemScroller(),
                new EventDelayModule(),
                new BlocksOutline(),
                this.espFunction = new NameTags(),
                this.noInteractFunction = new NoInteractFunction(),
                this.customWorld = new CustomWorld(),
                this.clientSounds = new ClientSounds(),
                this.nameProtect = new NameProtect(),
                this.hitColor = new HitColor(),
                new ElytraSwap(),
                this.auraFunction = new AuraFunction(),
                new WaterSpeed(),
                new RWJoiner(),
                new HolyJoiner(),
                unhook = new UnHookFunction(),
                new AutoTool(),
                new AutoDuel(),
                new FTAssistent(),
//                new AutoMyst(),
                new Tracers(),
                new NoFriendDamage(),
                new ItemESP(),
                new PearlPrediction(),
                new AutoTpacceptFunction(),
                new DragonFlyFunction(),
                new MiddleClickFriendFunction(),
                new JumpCircleFunction(),
                this.autoExplosionFunction = new AutoExplosionFunction(),
                new AutoAncherFunction(),
                new TrailsFunction(),
                new SpeedFunction(),
                new ElytraFly(),
                new AntiAFKFunction(),
                new ItemSwapFixFunction(),
                new DeathCoordsFunction(),
                new SpiderFunction(),
                freeCam = new FreeCam(),
                new NoClip(),
                new BlockESP(),
                new JesusFunction(),
                new Blink(),
                new AutoArmor(),
                this.noCommands = new NoCommands(),
                new TriggerBot(),
                new AutoLeave(),
                new BackTrack(),
                new HitSound(),
                new Particles(),
                new Chams(),
                new NoDelay(),
                new AutoFish(),
                new AutoEat(),
                new Bypass(),
                new ChinaHat(),
                new RWHelper(),
                new XCarry(),
                hud = new Hud()
        ));
    }

    public Function get(String name) {
        for (Function function : functions) {
            if (function != null && function.name.equalsIgnoreCase(name)) {
                return function;
            }
        }
        return null;
    }
}
