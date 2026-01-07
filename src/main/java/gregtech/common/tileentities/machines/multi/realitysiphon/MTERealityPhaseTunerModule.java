package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_GLOW;
import static gregtech.api.enums.VoltageIndex.UIV;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ListWidget;
import com.google.common.collect.ImmutableMap;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.blocks.BlockCasings13;
import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;
import gregtech.common.gui.modularui.multiblock.base.MTERealitySiphonModuleBaseGui;
import gregtech.common.tileentities.machines.IHeatProducer;
import gregtech.common.tileentities.machines.MTEHeatSensor;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;

public class MTERealityPhaseTunerModule extends MTERealitySiphonModuleBase<MTERealityPhaseTunerModule>
    implements IHeatProducer {

    private static final IStructureDefinition<MTERealityPhaseTunerModule> STRUCTURE_DEFINITION = StructureDefinition
        .<MTERealityPhaseTunerModule>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][] { { "     ", "     ", "     ", "     ", "     ", "     ", "     ", " BBB " },
                    { "     ", "     ", "     ", "  B  ", "  B  ", "  B  ", " BBB ", "BCCCB" },
                    { "  B  ", "  B  ", "  B  ", " BCB ", "BEDEB", "BEDEB", "BCDCB", "BCDCB" },
                    { " BCB ", " BCB ", " BCB ", "BADAB", "EADAE", "EADAE", "EADAE", "BCDCB" },
                    { " B~B ", " EDE ", " EDE ", "BADAB", "EADAE", "EADAE", "EADAE", "BCDCB" },
                    { " BCB ", " EEE ", " EEE ", "BCECB", "EEEEE", "EEEEE", "EEEEE", "BCCCB" },
                    { " BBB ", " B B ", " B B ", "BB BB", "B   B", "B   B", "B   B", "BBBBB" } }))
        .addElement('A', ofBlock(Loaders.FRF_Coil_4, 0))
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings13, 10))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings13, 11))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 13))
        .addElement(
            'E',
            ofChain(
                buildHatchAdder(MTERealityPhaseTunerModule.class)
                    .atLeast(MTEHeatSensor.HeatSensorHatchElement.HeatSensor, InputHatch)
                    .casingIndex(((BlockCasings13) GregTechAPI.sBlockCasings13).getTextureIndex(11))
                    .hint(1)
                    .build(),
                onElementPass(MTERealityPhaseTunerModule::onCasingAdded, ofBlock(GregTechAPI.sBlockCasings13, 11))))
        .build();

    // Using fluidName (lowercase name without "fluid." prefix)
    // new Integer[]{0 required amount, 1 cooling min, 2 cooling max}
    private static final Map<String, Integer[]> COOLANT_FLUIDS = ImmutableMap.of(
        "cryotheum",
        new Integer[] { 5000, 1, 4 },
        "spatialfluid",
        new Integer[] { 2500, 4, 6 },
        "phononmedium",
        new Integer[] { 500, 8, 12 });

    // new Integer[]{0 required amount, 1 heat range min, 2 heat range max, 3 stabilization min, 4 stabilization max}
    private static final Map<String, Integer[]> PHASE_SHIFTING_FLUIDS = ImmutableMap.of(
        "temporalfluid",
        new Integer[] { 5000, 15, 20, 5, 15 },
        "protomatter",
        new Integer[] { 3000, 10, 15, 10, 25 },
        "molten.eternity",
        new Integer[] { 1000, 5, 10, 20, 25 },
        "molten.universium",
        new Integer[] { 10, 1, 5, 10, 10 });

    private static final int MAX_HEAT = 100;

    private final ArrayList<MTEHeatSensor> sensorHatches = new ArrayList<>();
    private int heat;
    private boolean overheated;

    public MTERealityPhaseTunerModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTERealityPhaseTunerModule(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 2, 4, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 2, 4, 0, elementBudget, env, false, true);
    }

    @Override
    public IStructureDefinition<MTERealityPhaseTunerModule> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 2, 4, 0) && casingAmount >= 57 - 21;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        sensorHatches.clear();
        casingAmount = 0;
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        if (getBaseMetaTileEntity().isAllowedToWork()) {
            ArrayList<FluidStack> fluids = getStoredFluids();

            boolean foundProcess = false;
            FluidStack foundCoolant = null;
            FluidStack foundShifting = null;

            // Searching fluids and if there is enough for a process (coolant or phase shifting fluid)
            for (FluidStack fluidStack : fluids) {
                var coolant = COOLANT_FLUIDS.get(
                    fluidStack.getFluid()
                        .getName());
                var shifting = PHASE_SHIFTING_FLUIDS.get(
                    fluidStack.getFluid()
                        .getName());
                if (foundCoolant == null && coolant != null && fluidStack.amount > 0) {
                    foundCoolant = fluidStack;
                    if (!foundProcess && fluidStack.amount >= coolant[0]) {
                        foundProcess = true;
                        foundCoolant = new FluidStack(fluidStack.getFluid(), coolant[0]);
                    }
                }
                if (foundShifting == null && shifting != null && fluidStack.amount > 0) {
                    foundShifting = fluidStack;
                    if (!foundProcess && fluidStack.amount >= shifting[0]) {
                        foundProcess = true;
                        foundShifting = new FluidStack(fluidStack.getFluid(), shifting[0]);
                    }
                }
            }

            if (foundProcess) {
                getBaseMetaTileEntity().setActive(true);

                if (foundCoolant != null && foundShifting != null) {
                    getBaseMetaTileEntity().setActive(false);
                    depleteInput(foundCoolant);
                    depleteInput(foundShifting);
                    return CheckRecipeResultRegistry.FLUID_COOLANT_MIXING;
                }

                // Cooling
                if (foundCoolant != null) {
                    if (depleteInput(foundCoolant)) {
                        var coolant = COOLANT_FLUIDS.get(
                            foundCoolant.getFluid()
                                .getName());
                        var range = coolant[2] - coolant[1];
                        var cooling = coolant[1] + (range > 0 ? getBaseMetaTileEntity().getRandomNumber(range) : 0);
                        heat = Math.max(0, heat - cooling);

                        if (overheated && heat == 0) overheated = false;

                        mEfficiencyIncrease = 10000;
                        mMaxProgresstime = 20;
                        return CheckRecipeResultRegistry.SUCCESSFUL;
                    }
                }

                // Phase shifting + heat
                if (foundShifting != null) {
                    var siphon = updateAndGetTargetSiphon();
                    if (siphon.isPresent()) {
                        if (depleteInput(foundShifting)) {
                            var shifting = PHASE_SHIFTING_FLUIDS.get(
                                foundShifting.getFluid()
                                    .getName());
                            var heatRange = shifting[2] - shifting[1];
                            heat += shifting[1]
                                + (heatRange > 0 ? getBaseMetaTileEntity().getRandomNumber(heatRange) : 0);

                            if (heat >= MAX_HEAT) {
                                overheated = true;
                            }

                            if (overheated) {
                                stopMachine(ShutDownReasonRegistry.OVERHEATED);
                                return CheckRecipeResultRegistry.NO_RECIPE;
                            }

                            var shiftRange = shifting[4] - shifting[3];
                            siphon.get()
                                .addRealityPhase(
                                    shifting[3]
                                        + (shiftRange > 0 ? getBaseMetaTileEntity().getRandomNumber(shiftRange) : 0));

                            mEUt = (int) (-V[UIV] * 8);
                            mEfficiencyIncrease = 10000;
                            mMaxProgresstime = 20;
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                    }

                }
            }
        }

        mEfficiencyIncrease = 0;
        mMaxProgresstime = 0;
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if (aTick % 20 == 0) updateAndGetTargetSiphon();

            for (MTEHeatSensor hatch : sensorHatches) {
                hatch.updateRedstoneOutput(heat);
            }
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final String bold = EnumChatFormatting.BOLD.toString();
        final String reset = EnumChatFormatting.RESET.toString() + EnumChatFormatting.GRAY;
        final String heatColor = EnumChatFormatting.RED.toString();
        final String phaseColor = EnumChatFormatting.GREEN.toString();
        final String coolingColor = EnumChatFormatting.AQUA.toString();
        final String machineColor = EnumChatFormatting.YELLOW.toString();
        final String fluidColor = EnumChatFormatting.WHITE.toString();
        final String fluidName = EnumChatFormatting.LIGHT_PURPLE.toString();

        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Phase Tuner")
            .addInfo(
                "Calibrate the " + phaseColor
                    + "Reality Phase"
                    + reset
                    + " of a "
                    + machineColor
                    + "Reality Fabric Siphon"
                    + reset
                    + ".")
            .addInfo("Consume " + phaseColor + "Phase Shifting " + fluidColor + "Fluids" + reset + " to do so.")
            .addInfo(
                "Changing the " + phaseColor
                    + "Reality Phase"
                    + reset
                    + " will generate "
                    + heatColor
                    + "heat"
                    + reset
                    + ",")
            .addInfo("that will need to be " + coolingColor + "cooled" + reset + ".")
            .addInfo(
                "To " + coolingColor
                    + "cool"
                    + reset
                    + " the machine, it "
                    + bold
                    + EnumChatFormatting.DARK_RED
                    + "MUST NOT"
                    + reset
                    + " have")
            .addInfo("any " + phaseColor + "Phase Shifting " + fluidColor + "Fluids" + reset + " inside.")
            .addInfo("Doesn't use an energy hatch, instead receives")
            .addInfo("energy from the " + machineColor + "Reality Fabric Siphon" + reset + ".")
            .addSeparator()
            .addInfo(phaseColor + "Catalyst" + reset + ":");
        PHASE_SHIFTING_FLUIDS.forEach((fluid, values) -> {
            tt.addInfo(
                " - " + fluidName
                    + StatCollector.translateToLocal("fluid." + fluid)
                    + reset
                    + ", "
                    + fluidColor
                    + values[0]
                    + "L");
            String info = "    Phase shifting ";
            if (Objects.equals(values[1], values[2])) {
                info += phaseColor + values[1] + reset;
            } else {
                info += "from " + phaseColor + values[1] + reset + " to " + phaseColor + values[2] + reset;
            }
            tt.addInfo(info);
            info = "    Heat ";
            if (Objects.equals(values[3], values[4])) {
                info += values[1];
            } else {
                info += reset + "from " + heatColor + values[3] + reset + " to " + heatColor + values[4];
            }

            tt.addInfo(info + reset);
        });

        tt.addSeparator()
            .addInfo(coolingColor + "Coolant" + reset + ":");
        COOLANT_FLUIDS.forEach((fluid, values) -> {
            tt.addInfo(
                " - " + fluidName
                    + StatCollector.translateToLocal("fluid." + fluid)
                    + reset
                    + ", "
                    + fluidColor
                    + values[0]
                    + "L");
            String info = "    Cooling ";
            if (Objects.equals(values[1], values[2])) {
                info += coolingColor + values[1];
            } else {
                info += "from " + coolingColor + values[1] + reset + " to " + coolingColor + values[2];
            }

            tt.addInfo(info);
        });

        tt.beginStructureBlock(5, 7, 8, false)
            .addController("Front center")
            .addCasingInfoExactly("Reality Shifter Casing", 16, false)
            .addCasingInfoExactly("Temporal Field Restriction Coil", 16, false)
            .addCasingInfoRange("Reality Attuned Casing", 57, 62, false)
            .addCasingInfoExactly("Causality Resistant Casing", 68, false)
            .addInputHatch("Any Reality Attuned Casing except on front and back", 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTERealityPhaseTunerModule(this.mName);
    }

    @Override
    protected @NotNull MTEMultiBlockBaseGui<?> getGui() {
        return new MTERealitySiphonModuleBaseGui<>(this) {

            private final IntSyncValue heatSyncer = new IntSyncValue(() -> multiblock.heat);
            private final BooleanSyncValue overheatedSyncer = new BooleanSyncValue(() -> multiblock.overheated);

            @Override
            protected ListWidget<IWidget, ?> createTerminalTextWidget(PanelSyncManager syncManager,
                ModularPanel parent) {
                return super.createTerminalTextWidget(syncManager, parent)
                    .child(
                        IKey.dynamic(
                            () -> StatCollector
                                .translateToLocalFormatted("GT5U.gui.text.heat_value", heatSyncer.getValue()))
                            .color(0xAAAAAA)
                            .asWidget())
                    .child(
                        IKey.dynamic(() -> StatCollector.translateToLocal("GT5U.gui.text.overheated"))
                            .color(COLOR_TEXT_RED.get())
                            .asWidget()
                            .setEnabledIf((useless) -> overheatedSyncer.getBoolValue()));
            }

            @Override
            protected void registerSyncValues(PanelSyncManager syncManager) {
                super.registerSyncValues(syncManager);
                syncManager.syncValue("heat", heatSyncer);
                syncManager.syncValue("overheated", overheatedSyncer);
            }
        };
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean active, boolean redstoneLevel) {
        if (side == facing) {
            if (active) return new ITexture[] { CASING_TEXTURE, TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { CASING_TEXTURE, TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_PYROLYSE_OVEN)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PYROLYSE_OVEN_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { CASING_TEXTURE };
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        heat = aNBT.getInteger("heat");
        overheated = aNBT.getBoolean("overheated");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("heat", heat);
        aNBT.setBoolean("overheated", overheated);
    }

    @Override
    public int getHeatSensorHatchNum() {
        return sensorHatches.size();
    }

    @Override
    public boolean addHeatSensorHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity != null && aTileEntity.getMetaTileEntity() instanceof MTEHeatSensor sensor) {
            sensor.updateTexture(aBaseCasingIndex);
            return sensorHatches.add(sensor);
        }
        return false;
    }

    @Override
    public ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity baseMetaTileEntity, EntityPlayer player,
        int logLevel, ArrayList<String> list) {
        list.add("Heat: " + heat);
        return list;
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currentTip, accessor, config);
        currentTip.add(
            "Heat: " + accessor.getNBTData()
                .getInteger("heat") + "%");
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("heat", heat);
    }
}
