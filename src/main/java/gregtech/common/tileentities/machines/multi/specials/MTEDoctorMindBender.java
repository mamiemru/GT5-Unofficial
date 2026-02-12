package gregtech.common.tileentities.machines.multi.specials;

import static com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil.formatNumber;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_DTPF_OFF;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_DTPF_ON;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FUSION1_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;
import static gregtech.api.util.GTStructureUtility.chainAllGlasses;
import static gregtech.common.misc.WirelessNetworkManager.addEUToGlobalEnergyMap;
import static gregtech.common.misc.WirelessNetworkManager.getUserEU;
import static gregtech.common.misc.WirelessNetworkManager.processInitialSettings;
import static kekztech.util.Util.toStandardForm;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.math.BigInteger;
import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableMap;
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
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.common.gui.modularui.multiblock.MTEDoctorMindBenderrGui;
import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;

public class MTEDoctorMindBender extends MTEEnhancedMultiBlockBase<MTEDoctorMindBender>
    implements ISurvivalConstructable {

    private static final int MACHINEMODE_BENDER = 0;
    private static final int MACHINEMODE_FORMER = 1;

    private static final String[][] structure = new String[][] {
        { "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ",
            "         ", "         ", "         ", "  CC~CC  " },
        { "  CCCCC  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ",
            "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", " CCCCCCC " },
        { " CCFFFCC ", " BB E BB ", " BB E BB ", " BBEEEBB ", " BB E BB ", " BB E BB ", " BB E BB ", " BB E BB ",
            " BBEEEBB ", " BB E BB ", " BB E BB ", "CCCCCCCCC" },
        { " CFFFFFC ", " A DDD A ", " A DDD A ", " AEDDDEA ", " A DDD A ", " A DDD A ", " A DDD A ", " A DDD A ",
            " AEDDDEA ", " A DDD A ", " A DDD A ", "CCCCCCCCC" },
        { " CFFFFFC ", " AEDDDEA ", " AEDDDEA ", " AEDDDEA ", " AEDDDEA ", " AEDDDEA ", " AEDDDEA ", " AEDDDEA ",
            " AEDDDEA ", " AEDDDEA ", " AEDDDEA ", "CCCCCCCCC" },
        { " CFFFFFC ", " A DDD A ", " A DDD A ", " AEDDDEA ", " A DDD A ", " A DDD A ", " A DDD A ", " A DDD A ",
            " AEDDDEA ", " A DDD A ", " A DDD A ", "CCCCCCCCC" },
        { " CCFFFCC ", " BB E BB ", " BB E BB ", " BBEEEBB ", " BB E BB ", " BB E BB ", " BB E BB ", " BB E BB ",
            " BBEEEBB ", " BB E BB ", " BB E BB ", "CCCCCCCCC" },
        { "  CCCCC  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", "  BAAAB  ",
            "  BAAAB  ", "  BAAAB  ", "  BAAAB  ", " CCCCCCC " },
        { "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ",
            "         ", "         ", "         ", "  CCCCC  " } };

    private static final String STRUCTURE_PIECE_MAIN = "MAIN";
    private static final IStructureDefinition<MTEDoctorMindBender> STRUCTURE_DEFINITION = StructureDefinition
        .<MTEDoctorMindBender>builder()
        .addShape(STRUCTURE_PIECE_MAIN, structure)
        .addElement(
            'F',
            buildHatchAdder(MTEDoctorMindBender.class).atLeast(ImmutableMap.of(OutputBus, 1, InputBus, 1))
                .casingIndex(4)
                .hint(1)
                .buildAndChain(GregTechAPI.sBlockCasings2, 0))
        .addElement('A', chainAllGlasses())
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings10, 11))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings2, 0))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings5, 13))
        .addElement('E', ofBlock(GregTechAPI.sBlockCasings8, 14))
        .build();

    private UUID ownerUUID;

    public MTEDoctorMindBender(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEDoctorMindBender(String aName) {
        super(aName);
    }

    @Override
    public boolean supportsPowerPanel() {
        return false;
    }

    @Override
    public IStructureDefinition<MTEDoctorMindBender> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    public String getMachineType() {
        return "Bending Machine, Forming Press, IMP";
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType(getMachineType())
            .addBulkMachineInfo(4, 6f, 1f)
            .addPollutionAmount(getPollutionPerSecond(null))
            .beginStructureBlock(9, 14, 9, true)
            .addController("Front Center")
            .addCasingInfoMin("Material Press Machine Casings", 6, false)
            .addInputHatch("Any Casing", 1)
            .addInputBus("Any Casing", 1)
            .addOutputBus("Any Casing", 1)
            .addEnergyHatch("Any Casing", 1)
            .addMaintenanceHatch("Any Casing", 1)
            .addMufflerHatch("Any Casing", 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEDoctorMindBender(mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { casingTexturePages[0][0], TextureFactory.builder()
                .addIcon(OVERLAY_DTPF_ON)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FUSION1_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { casingTexturePages[0][0], TextureFactory.builder()
                .addIcon(OVERLAY_DTPF_OFF)
                .extFacing()
                .build() };
        }

        return new ITexture[] { casingTexturePages[0][0] };
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    private int multiplier = 1;
    BigInteger finalConsumption = BigInteger.ZERO;

    @Override
    public RecipeMap<?> getRecipeMap() {
        return (machineMode == MACHINEMODE_FORMER) ? RecipeMaps.formingPressRecipes : RecipeMaps.benderRecipes;
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
                return OverclockCalculator.ofNoOverclock(recipe.mEUt, 64);
            }
        }.setMaxParallelSupplier(this::getTrueParallel);
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
        logic.setAmperageOC(false);
        logic.setUnlimitedTierSkips();
    }

    private static final int HORIZONTAL_OFFSET = 4;
    private static final int VERTICAL_OFFSET = 11;
    private static final int DEPTH_OFFSET = 0;

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
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
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET);
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 0;
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
    protected @NotNull MTEMultiBlockBaseGui<?> getGui() {
        return new MTEDoctorMindBenderrGui(this).withMachineModeIcons(
            GTGuiTextures.OVERLAY_BUTTON_MACHINEMODE_BENDING,
            GTGuiTextures.OVERLAY_BUTTON_MACHINEMODE_FORMING);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("eMultiplier", multiplier);
        aNBT.setBoolean("mFormingMode", machineMode == MACHINEMODE_FORMER);
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        multiplier = aNBT.getInteger("eMultiplier");
        machineMode = aNBT.getBoolean("mFormingMode") ? MACHINEMODE_FORMER : MACHINEMODE_BENDER;
        super.loadNBTData(aNBT);
    }

    @Override
    public String getMachineModeName() {
        return translateToLocal("GT5U.GTPP_MULTI_INDUSTRIAL_PLATE_PRESS.mode." + machineMode);
    }

    public BigInteger getTheoriticalEnergyConsumption() {
        return mMaxProgresstime == 0 ? BigInteger.ZERO : finalConsumption.divide(BigInteger.valueOf(-mMaxProgresstime));
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            StatCollector.translateToLocal("GT5U.multiblock.Mode") + ": "
                + EnumChatFormatting.BLUE
                + getMachineModeName()
                + EnumChatFormatting.RESET,
            StatCollector.translateToLocal("GT5U.multiblock.Progress") + ": "
                + EnumChatFormatting.GREEN
                + formatNumber(mProgresstime / 20)
                + EnumChatFormatting.RESET
                + " s / "
                + EnumChatFormatting.YELLOW
                + formatNumber(mMaxProgresstime / 20)
                + EnumChatFormatting.RESET
                + " s",
            StatCollector.translateToLocal("GT5U.multiblock.usage") + ": "
                + EnumChatFormatting.RED
                + toStandardForm(getTheoriticalEnergyConsumption())
                + EnumChatFormatting.RESET
                + " EU/t",
            StatCollector.translateToLocal("GT5U.multiblock.recipesDone") + ": "
                + EnumChatFormatting.GREEN
                + formatNumber(recipesDone)
                + EnumChatFormatting.RESET };
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return false;
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
        int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setString("mode", getMachineModeName());
    }

}
