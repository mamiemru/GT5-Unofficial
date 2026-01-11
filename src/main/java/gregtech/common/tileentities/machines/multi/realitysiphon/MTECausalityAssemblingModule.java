package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_GLOW;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.common.blocks.BlockCasings13;

public class MTECausalityAssemblingModule extends MTERealitySiphonModuleBase<MTECausalityAssemblingModule> {

    private static final IStructureDefinition<MTECausalityAssemblingModule> STRUCTURE_DEFINITION = StructureDefinition
        .<MTECausalityAssemblingModule>builder()
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
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 12))
        .addElement(
            'E',
            ofChain(
                buildHatchAdder(MTECausalityAssemblingModule.class).atLeast(InputHatch, InputBus, OutputBus)
                    .casingIndex(((BlockCasings13) GregTechAPI.sBlockCasings13).getTextureIndex(11))
                    .hint(1)
                    .build(),
                onElementPass(MTECausalityAssemblingModule::onCasingAdded, ofBlock(GregTechAPI.sBlockCasings13, 11))))
        .build();

    private double recipeCausalityPerSec;

    public MTECausalityAssemblingModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTECausalityAssemblingModule(String aName) {
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
    public IStructureDefinition<MTECausalityAssemblingModule> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 2, 4, 0) && casingAmount >= 57 - 21;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        casingAmount = 0;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.causalityRecipes;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic() {

            @Override
            protected @Nonnull CheckRecipeResult validateRecipe(@Nonnull GTRecipe recipe) {
                recipeCausalityPerSec = recipe.getMetadataOrDefault(GTRecipeConstants.CAUSALITY_STREAM, 0.0);
                if (recipeCausalityPerSec
                    <= updateAndGetTargetSiphon().map(MTERealityFabricSiphon::getCausalityGeneratedPerSec)
                        .orElse(0.0)) {
                    return CheckRecipeResultRegistry.SUCCESSFUL;
                }
                return CheckRecipeResultRegistry.insufficientCausality(recipeCausalityPerSec);
            }

            @Override
            protected OverclockCalculator createOverclockCalculator(@Nonnull GTRecipe recipe) {
                double recipeCausalityRequired = recipe.getMetadataOrDefault(GTRecipeConstants.CAUSALITY_STREAM, 0.0);
                var siphonCausalityPerSec = updateAndGetTargetSiphon()
                    .map(MTERealityFabricSiphon::getCausalityGeneratedPerSec)
                    .orElse(0.0);
                double overclock = Math.max(1, siphonCausalityPerSec / recipeCausalityRequired);
                return new OverclockCalculator().setRecipeEUt(recipe.mEUt)
                    .setAmperage(availableAmperage)
                    .setEUt(availableVoltage)
                    .setMaxTierSkips(maxTierSkips)
                    .setDuration(recipe.mDuration)
                    .setDurationModifier(speedBoost)
                    .setEUtDiscount(euModifier)
                    .setAmperageOC(overclock > 1)
                    .setDurationDecreasePerOC(overclock)
                    .setEUtIncreasePerOC(overClockPowerIncrease);
            }

        };
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long tick) {
        super.onPostTick(aBaseMetaTileEntity, tick);
        if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isActive() && tick % 20 == 1) {
            var siphonOpt = updateAndGetTargetSiphon();
            var causalityPerSec = siphonOpt.map(MTERealityFabricSiphon::getCausalityGeneratedPerSec)
                .orElse(0.0);
            if (siphonOpt.isPresent() && causalityPerSec >= recipeCausalityPerSec) {
                if (!siphonOpt.get()
                    .consumeCausality(recipeCausalityPerSec)) stopMachine(ShutDownReasonRegistry.CAUSALITY_LOSS);
            } else {
                stopMachine(ShutDownReasonRegistry.CAUSALITY_LOSS);
            }
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Causality Crafter")
            .addInfo("Assembling with causality to make thing go the way you want")
            .addInfo("Like putting the triangle inside the the cylinder slot")
            .addInfo("Require a stream of causality from the Reality Fabric Siphon")
            .addInfo(
                "Doesn't use an energy hatch, instead receives energy from the " + EnumChatFormatting.YELLOW
                    + "Reality Fabric Siphon")
            .beginStructureBlock(5, 7, 8, false)
            .addController("Front center")
            .addCasingInfoExactly("Causality Attraction Engine", 16, false)
            .addCasingInfoExactly("Temporal Field Restriction Coil", 16, false)
            .addCasingInfoRange("Reality Attuned Casing", 57, 62, false)
            .addCasingInfoExactly("Causality Resistant Casing", 68, false)
            .addInputHatch("Any Reality Attuned Casing except on front and back", 1)
            .addInputBus("Any Reality Attuned Casing except on front and back", 1)
            .addOutputBus("Any Reality Attuned Casing except on front and back", 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTECausalityAssemblingModule(this.mName);
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
}
