package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_GLOW;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableMap;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasings13;
import gregtech.common.tileentities.machines.IHeatProducer;
import gregtech.common.tileentities.machines.MTEHeatSensor;

public class MTERealityPhaseTunerModule extends MTERealitySiphonModuleBase<MTERealityPhaseTunerModule> implements IHeatProducer {

    private static final IStructureDefinition<MTERealityPhaseTunerModule> STRUCTURE_DEFINITION = StructureDefinition
        .<MTERealityPhaseTunerModule>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][]{
                    {"     ","     ","     ","     ","     ","     ","     "," BBB "},
                    {"     ","     ","     ","  B  ","  B  ","  B  "," BBB ","BCCCB"},
                    {"  B  ","  B  ","  B  "," BCB ","BEDEB","BEDEB","BCDCB","BCDCB"},
                    {" BEB "," BCB "," BCB ","BADAB","EADAE","EADAE","EADAE","BCDCB"},
                    {" B~B "," EDE "," EDE ","BADAB","EADAE","EADAE","EADAE","BCDCB"},
                    {" BEB "," EEE "," EEE ","BCECB","EEEEE","EEEEE","EEEEE","BCCCB"},
                    {" BBB "," B B "," B B ","BB BB","B   B","B   B","B   B","BBBBB"}
                }
            )
        )
        .addElement('A', ofBlock(Loaders.FRF_Coil_4, 0))
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings13, 10))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings13, 11))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 13))
        .addElement('E',
            buildHatchAdder(MTERealityPhaseTunerModule.class)
                .atLeast(ImmutableMap.of(InputHatch, 1))
                .atLeast(MTEHeatSensor.HeatSensorHatchElement.HEAT_SENSOR)
                .dot(1)
                .casingIndex( ((BlockCasings13) GregTechAPI.sBlockCasings13).getTextureIndex(11) )
                .buildAndChain( onElementPass(MTERealityPhaseTunerModule::onCasingAdded, ofBlock(GregTechAPI.sBlockCasings13, 11)) )
        ).build();

    // Using fluidName (lowercase name without "fluid." prefix)
    // new Integer[]{0 required amount, 1 cooling min, 2 cooling max}
    private static Map<String,Integer[]> COOLANT_FLUIDS = ImmutableMap.of(
        "spatialfluid", new Integer[]{5000, 10,30},
        "phononmedium", new Integer[]{500, 10,35});

    // new Integer[]{0 required amount, 1 heat range min, 2 heat range max, 3 stabilization min, 4 stabilization max}
    private static Map<String,Integer[]> PHASE_SHIFTING_FLUIDS = ImmutableMap.of(
        "temporalfluid", new Integer[]{5000, 5,10, 1,30},
        "universium", new Integer[]{1, 5,5, 10,10});

    private int heat;
    private boolean overheat;

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
        casingAmount = 0;
        return checkPiece(STRUCTURE_PIECE_MAIN, 2, 4, 0) && casingAmount >= 57 - 19;
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        if(getBaseMetaTileEntity().isAllowedToWork()) {
            ArrayList<FluidStack> fluids = getStoredFluids();

            boolean foundProcess = false;
            FluidStack foundCoolant = null;
            FluidStack foundShifting = null;

            // Searching fluids and if there is enough for a process (coolant or phase shifting fluid)
            for(FluidStack fluidStack:fluids) {
                var coolant = COOLANT_FLUIDS.get(fluidStack.getFluid().getName());
                var shifting = PHASE_SHIFTING_FLUIDS.get(fluidStack.getFluid().getName());
                if (foundCoolant == null && coolant != null && fluidStack.amount > 0) {
                    foundCoolant = fluidStack;
                    if(!foundProcess && fluidStack.amount >= coolant[0]){
                        foundProcess = true;
                    }
                }
                if (foundShifting == null && shifting != null && fluidStack.amount > 0 ) {
                    foundShifting = fluidStack;
                    if(!foundProcess && fluidStack.amount >= shifting[0]){
                        foundProcess = true;
                    }
                }
            }

            if(foundProcess){
                getBaseMetaTileEntity().setActive(true);

                if( foundCoolant != null && foundShifting != null ){
                    getBaseMetaTileEntity().setActive(false);
                    depleteInput(foundCoolant);
                    depleteInput(foundShifting);
                    return CheckRecipeResultRegistry.NONE;
                }

                // Cooling
                if(foundCoolant != null) {
                    if(depleteInput(foundCoolant)) {
                        var coolant = COOLANT_FLUIDS.get(foundCoolant.getFluid().getName());
                        var range = coolant[2]-coolant[1];
                        var cooling = coolant[1] + (range > 0 ? getBaseMetaTileEntity().getRandomNumber(range) : 0);
                        heat = Math.max(0, heat - cooling);

                        if(overheat && heat == 0) overheat = false;

                        mEfficiencyIncrease = 10000;
                        mMaxProgresstime = 20;
                        return CheckRecipeResultRegistry.SUCCESSFUL;
                    }
                }

                // Phase shifting + heat
                if(foundShifting != null) {
                    this.siphon = getTargetSiphon();

                    if (this.siphon != null) {
                        if(depleteInput(foundShifting)) {
                            var shifting = PHASE_SHIFTING_FLUIDS.get(foundShifting.getFluid().getName());
                            var heatRange = shifting[2]-shifting[1];
                            heat += shifting[1] + (heatRange > 0 ? getBaseMetaTileEntity().getRandomNumber(heatRange) : 0);
                            var shiftRange = shifting[4]-shifting[3];

                            if(overheat) {
                                return CheckRecipeResultRegistry.OVERHEAT;
                            }

                            siphon.addRealityPhase( shifting[3] + (shiftRange > 0 ? getBaseMetaTileEntity().getRandomNumber(shiftRange) : 0) );

                            mEfficiencyIncrease = 10000;
                            mMaxProgresstime = 20;
                            return CheckRecipeResultRegistry.SUCCESSFUL;
                        }
                    }

                }
            }
        }
        this.siphon = null;

        mEfficiencyIncrease = 0;
        mMaxProgresstime = 0;
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {

            for (MTEHeatSensor hatch : sensorHatches) {
                hatch.updateRedstoneOutput(heat);
            }
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Phase Tuner")
            .addInfo("Change the reality phase of a Reality Fabric Siphon")
            .addInfo("Consume catalyst to do so")
            .addInfo("Changing the reality phase will generate heat, that will need to be cooled")
            .addInfo("To cool the machine, it MUST NOT have any catalyst inside")
            .addSeparator()
            .addInfo("Catalyst:");
        PHASE_SHIFTING_FLUIDS.forEach( (fluid, values)->{
            tt.addInfo(" - " + StatCollector.translateToLocal("fluid."+fluid) + ", " + values[0] + "L");
            String info = "   Phase shifting ";
            if(Objects.equals(values[1], values[2])){
                info += values[1];
            }else{
                info += "from " + values[1] + " to " + values[2];
            }
            info += ", heat ";
            if(Objects.equals(values[3], values[4])){
                info += values[1];
            }else{
                info += "from " + values[3] + " to " + values[4];
            }

            tt.addInfo(info);
        });

        tt.addSeparator()
            .addInfo("Coolant:");
        COOLANT_FLUIDS.forEach( (fluid, values)->{
            tt.addInfo(" - " + StatCollector.translateToLocal("fluid."+fluid) + " (" + values[0] + "L)");
            String info = "   Cooling ";
            if(Objects.equals(values[1], values[2])){
                info += values[1];
            }else{
                info += "from " + values[1] + " to " + values[2];
            }

            tt.addInfo(info);
        });

        tt.beginStructureBlock(5, 7, 8, false)
            .addController("Front center")
            .addCasingInfoExactly("Reality Shifter Casing", 16, false)
            .addCasingInfoExactly("Temporal Field Restriction Coil", 16, false)
            .addCasingInfoRange("Reality Attuned Casing", 57, 62, false)
            .addCasingInfoExactly("Causality Resistant Casing", 68, false)
            .addInputHatch("Any Casing excepting on back around Reality Shifter Casing", 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTERealityPhaseTunerModule(this.mName);
    }

    @Override
    protected void drawTexts(DynamicPositionedColumn screenElements, SlotWidget inventorySlot) {
        super.drawTexts(screenElements, inventorySlot);
        screenElements
            .widget(
                new TextWidget().setStringSupplier(
                        () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.heat_value", heat))
                    .setDefaultColor(EnumChatFormatting.RED)
                    .setEnabled(widget -> mMachine))
            .widget(new FakeSyncWidget.IntegerSyncer(() -> heat, heat -> this.heat = heat));
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active, boolean redstoneLevel) {
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
        aNBT.setInteger("heat", heat);
        aNBT.setBoolean("overheat", overheat);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        heat = aNBT.getInteger("heat");
        overheat = aNBT.getBoolean("overheat");
    }
}
