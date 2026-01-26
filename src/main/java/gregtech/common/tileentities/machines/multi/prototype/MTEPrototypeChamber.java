package gregtech.common.tileentities.machines.multi.prototype;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static gregtech.api.enums.HatchElement.Energy;
import static gregtech.api.enums.HatchElement.InputBus;
import static gregtech.api.enums.HatchElement.InputHatch;
import static gregtech.api.enums.HatchElement.Maintenance;
import static gregtech.api.enums.HatchElement.MultiAmpEnergy;
import static gregtech.api.enums.HatchElement.OutputBus;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_LATEX;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_LATEX_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_LATEX_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_MULTI_LATEX_GLOW;
import static gregtech.api.util.GTStructureUtility.buildHatchAdder;

public class MTEPrototypeChamber extends MTEExtendedPowerMultiBlockBase<MTEPrototypeChamber> implements ISurvivalConstructable {

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<MTEPrototypeChamber> STRUCTURE_DEFINITION = StructureDefinition.<MTEPrototypeChamber>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            new String[][]{{
                " C~C "
            },{
                "CCCCC"
            },{
                "CCCCC"
            },{
                "CCCCC"
            },{
                " CCC "
            }} )
        .addElement(
            'C',
            buildHatchAdder(MTEPrototypeChamber.class)
                .atLeast(InputBus, InputHatch, OutputBus, Maintenance, Energy.or(MultiAmpEnergy))
                .casingIndex(176)
                .hint(1)
                .buildAndChain(onElementPass(e -> {}, ofBlock(GregTechAPI.sBlockCasings8, 0))))
        .build();

    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()  {
        }.setSpeedBonus(1F / 2F)
            .setMaxParallelSupplier(this::getTrueParallel)
            .setEuModifier(0.85F);
    }


    public MTEPrototypeChamber(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEPrototypeChamber(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<MTEPrototypeChamber> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEPrototypeChamber(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
                                 int colorIndex, boolean aActive, boolean redstoneLevel) {
        ITexture[] rTexture;
        if (side == aFacing) {
            if (aActive) {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings8, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_LATEX_ACTIVE)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_LATEX_ACTIVE_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            } else {
                rTexture = new ITexture[] {
                    Textures.BlockIcons
                        .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings8, 0)),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_LATEX)
                        .extFacing()
                        .build(),
                    TextureFactory.builder()
                        .addIcon(OVERLAY_FRONT_MULTI_LATEX_GLOW)
                        .extFacing()
                        .glow()
                        .build() };
            }
        } else {
            rTexture = new ITexture[] { Textures.BlockIcons
                .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings8, 0)) };
        }
        return rTexture;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Prototype")
            .toolTipFinisher();
        return tt;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 2, 0, 0);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 2, 0, 0, elementBudget, env, false, true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, 2, 0, 0);
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.cableRecipes;
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
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
    public boolean supportsSingleRecipeLocking() { return true; }

}
