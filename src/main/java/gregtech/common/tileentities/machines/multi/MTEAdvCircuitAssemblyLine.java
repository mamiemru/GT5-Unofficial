package gregtech.common.tileentities.machines.multi;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.Maintenance;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.recipe.RecipeMaps.advCircuitAssemblylineRecipes;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.chainAllGlasses;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;
import static gregtech.common.misc.WirelessNetworkManager.processInitialSettings;

import java.math.BigInteger;
import java.util.UUID;

import javax.annotation.Nonnull;

import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.gui.modularui.multiblock.MTEAdvancedCircuitAssemblyLineGui;
import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;
import gregtech.common.misc.GTStructureChannels;

public class MTEAdvCircuitAssemblyLine extends MTEEnhancedMultiBlockBase<MTEAdvCircuitAssemblyLine>
    implements ISurvivalConstructable {

    private UUID ownerUUID;
    private int multiplier = 1;
    BigInteger finalConsumption = BigInteger.ZERO;
    private static final String STRUCTURE_PIECE_MAIN = "main";

    private static final int HORIZONTAL_OFFSET = 3;
    private static final int VERTICAL_OFFSET = 3;
    private static final int DEPTH_OFFSET = 1;
    private static final String[][] structure = new String[][] {
        { "BBBBBBB", "B     B", "B     B", "B     B", "B     B", "B     B", "BBBBBBB" },
        { "BBBBBBB", "BGGGGGB", "BGGGGGB", "BGG~GGB", "BGGGGGB", "BGGGGGB", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A CCC A", "AECFCEA", "A CCC A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BBBBBBB", "BECECEB", "BEFFFEB", "BEFFFEB", "BEFFFEB", "BECECEB", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A CCC A", "AECFCEA", "A CCC A", "A CEC A", "BBBBBBB" },
        { "BAAAAAB", "A CEC A", "A     A", "AE F EA", "A     A", "A CEC A", "BBBBBBB" },
        { "BBBBBBB", "BBBBBBB", "BBDDDBB", "BBDODBB", "BBDDDBB", "BBCECBB", "BBBBBBB" },
        { "       ", " BBBBB ", " B   B ", " B   B ", " B   B ", " BBBBB ", " BBBBB " } };

    private static final IStructureDefinition<MTEAdvCircuitAssemblyLine> STRUCTURE_DEFINITION = StructureDefinition
        .<MTEAdvCircuitAssemblyLine>builder()
        .addShape(STRUCTURE_PIECE_MAIN, structure)
        .addElement('A', chainAllGlasses())
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings2, 0))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings2, 5))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings8, 12))
        .addElement('E', ofBlock(GregTechAPI.sBlockCasings8, 14))
        .addElement('F', ofBlock(GregTechAPI.sBlockCasings2, 9))
        .addElement(
            'O',
            ofChain(
                buildHatchAdder(MTEAdvCircuitAssemblyLine.class).atLeast(OutputBus)
                    .casingIndex(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings8, 12))
                    .dot(2)
                    .build()))
        .addElement(
            'G',
            ofChain(
                buildHatchAdder(MTEAdvCircuitAssemblyLine.class).atLeast(Maintenance, InputBus, InputHatch)
                    .casingIndex(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings8, 12))
                    .dot(1)
                    .build(),
                ofBlock(GregTechAPI.sBlockCasings8, 12)))
        .build();

    public MTEAdvCircuitAssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEAdvCircuitAssemblyLine(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEAdvCircuitAssemblyLine(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Advanced Circuit Assembly Line, Never Actually Coming, AdvCAL")
            .beginStructureBlock(7, 7, 13, false)
            .addSubChannelUsage(GTStructureChannels.BOROGLASS)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { casingTexturePages[0][16], TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { casingTexturePages[0][16], TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_ASSEMBLY_LINE)
                .extFacing()
                .build() };
        }

        return new ITexture[] { casingTexturePages[0][16] };
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public int getMaxParallelRecipes() {
        return multiplier;
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        // The voltage is only used for recipe finding
        logic.setAvailableVoltage(Long.MAX_VALUE);
        logic.setAvailableAmperage(1);
        logic.setAmperageOC(true);
        logic.setUnlimitedTierSkips();
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return advCircuitAssemblylineRecipes;
    }

    @Override
    public IStructureDefinition<MTEAdvCircuitAssemblyLine> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {

        if (!checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET)) return false;

        return mMaintenanceHatches.size() == 1;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            BigInteger recipeEU;

            @NotNull
            @Override
            protected CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                BigInteger availableEU = getUserEU(ownerUUID);
                long multiplier = (long) recipe.getMetadataOrDefault(GTRecipeConstants.EU_MULTIPLIER, 10);
                recipeEU = BigInteger.valueOf(multiplier * recipe.mEUt * recipe.mDuration);
                if (availableEU.compareTo(recipeEU) < 0) {
                    finalConsumption = BigInteger.ZERO;
                    return CheckRecipeResultRegistry.insufficientStartupPower(recipeEU);
                }
                maxParallel = availableEU.divide(recipeEU)
                    .min(BigInteger.valueOf(maxParallel))
                    .intValue();
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @NotNull
            @Override
            protected CheckRecipeResult onRecipeStart(@Nonnull GTRecipe recipe) {
                finalConsumption = recipeEU.multiply(BigInteger.valueOf(-calculatedParallels));
                // This will void the inputs if wireless energy has dropped
                // below the required amount between validateRecipe and here.
                if (!addEUToGlobalEnergyMap(ownerUUID, finalConsumption)) {
                    return CheckRecipeResultRegistry.insufficientStartupPower(finalConsumption);
                }
                // Energy consumed all at once from wireless net.
                overwriteCalculatedEut(0);
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }

            @Nonnull
            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                return OverclockCalculator.ofNoOverclock(recipe);
            }
        }.setMaxParallelSupplier(this::getTrueParallel);
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {

        super.onPreTick(aBaseMetaTileEntity, aTick);

        if (aBaseMetaTileEntity.isServerSide() && (aTick == 1)) {
            // Adds player to the wireless network if they do not already exist on it.
            ownerUUID = processInitialSettings(aBaseMetaTileEntity);
        }
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            HORIZONTAL_OFFSET,
            VERTICAL_OFFSET,
            DEPTH_OFFSET,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    protected @NotNull MTEMultiBlockBaseGui<?> getGui() {
        return new MTEAdvancedCircuitAssemblyLineGui(this);
    }

    @Override
    public boolean supportsBatchMode() {
        return true;
    }

    @Override
    public boolean supportsInputSeparation() {
        return true;
    }

    @Override
    public boolean supportsSingleRecipeLocking() {
        return true;
    }

}
