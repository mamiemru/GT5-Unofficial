package gregtech.common.tileentities.machines.multi.prototype;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatchEnergy;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.items.ItemComb;
import gtnhlanth.common.hatch.MTEHatchInputBeamline;
import gtnhlanth.common.hatch.MTEHatchOutputBeamline;
import gtnhlanth.common.tileentity.MTELINAC;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static gregtech.api.enums.GTValues.V;
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

public class MTEPrototype extends MTEExtendedPowerMultiBlockBase<MTEPrototype> implements ISurvivalConstructable {

    private int beamLevel = 0;

    private static final int ENERGY_CONSUMPTION = ItemComb.Voltage.UIV.getVoltage();
    private static final int MAX_BEAM_LEVEL = 10000;
    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<MTEPrototype> STRUCTURE_DEFINITION = StructureDefinition.<MTEPrototype>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            new String[][]{{
                " C~C "
            },{
                "CCCCC"
            },{
                "CCXCC"
            },{
                "CCCCC"
            },{
                " CCC "
            }} )
        .addElement(
            'C',
            buildHatchAdder(MTEPrototype.class)
                .atLeast(InputBus, InputHatch, OutputBus, Energy)
                .casingIndex(176)
                .hint(1)
                .buildAndChain(onElementPass(e -> {}, ofBlock(GregTechAPI.sBlockCasings8, 0))))
        .addElement(
            'X',
            buildHatchAdder(MTEPrototype.class).hatchClass(PrototypeHatch.class)
                .casingIndex(176)
                .hint(2)
                .adder(MTEPrototype::addChargeHatch)
                .build())
        .build();

    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()  {
        }.setSpeedBonus(1F / 2F)
            .setMaxParallelSupplier(this::getTrueParallel)
            .setEuModifier(0.85F);
    }


    public MTEPrototype(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEPrototype(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<MTEPrototype> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEPrototype(this.mName);
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

    private boolean addChargeHatch(IGregTechTileEntity te, int casingIndex) {
        if (te == null) return false;

        IMetaTileEntity mte = te.getMetaTileEntity();
        if (mte == null) return false;

        if (mte instanceof PrototypeHatch) {
            return ((PrototypeHatch) mte).connectToController(this);
        }

        return false;
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

    public int getCharge() {
        return beamLevel;
    }


    @NotNull
    @Override
    public CheckRecipeResult checkProcessing() {

        long mEutTemp = 0;
        int beamLevelTemp = 0;
        for (MTEHatchEnergy ea : mEnergyHatches){
            int tier = (int) ea.getInputTier();
            beamLevelTemp += Math.max(0, (tier - 10));
            mEutTemp += V[tier];
        }

        mEUt = (int) mEutTemp;

        if (mEUt <= 0) {
            return CheckRecipeResultRegistry.insufficientPower(TierEU.RECIPE_UIV);
        }

        beamLevel += beamLevelTemp;

        if (beamLevel > MAX_BEAM_LEVEL) {
            explodeMultiblock();
        }

        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    @Override
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
                                int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("beamLevel", beamLevel);
    }

    @Override
    public void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                             IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currenttip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        currenttip.add(
            "beamLevel: "
                + EnumChatFormatting.WHITE
                + tag.getInteger("beamLevel"));
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        // NBT can be loaded as any primitive type, so we can load it as long.
        this.beamLevel = aNBT.getInteger("beamLevel");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("beamLevel", this.beamLevel);
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
