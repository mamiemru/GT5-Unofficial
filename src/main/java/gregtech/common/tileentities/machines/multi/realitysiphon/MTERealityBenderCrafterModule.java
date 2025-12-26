package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PYROLYSE_OVEN_GLOW;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import goodgenerator.loader.Loaders;
import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.MultiblockTooltipBuilder;

public class MTERealityBenderCrafterModule extends MTERealitySiphonModuleBase {

    private static final IStructureDefinition<MTERealitySiphonModuleBase> STRUCTURE_DEFINITION = StructureDefinition
        .<MTERealitySiphonModuleBase>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][]{
                    {"     ","     ","     ","     ","     ","     ","     "," BBB "},
                    {"     ","     ","     ","  B  ","  B  ","  B  "," BBB ","BCCCB"},
                    {"  B  ","  B  ","  B  "," BCB ","BCDCB","BCDCB","BCDCB","BCDCB"},
                    {" BCB "," BCB "," BCB ","BADAB","CADAC","CADAC","CADAC","BCDCB"},
                    {" B~B "," CDC "," CDC ","BADAB","CADAC","CADAC","CADAC","BCDCB"},
                    {" BCB "," CCC "," CCC ","BCCCB","CCCCC","CCCCC","CCCCC","BCCCB"},
                    {" BBB "," B B "," B B ","BB BB","B   B","B   B","B   B","BBBBB"}
                }
            )
        )
        .addElement('A', ofBlock(Loaders.FRF_Coil_4, 0))
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings13, 10))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings13, 11))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 12))
        .build();

    public MTERealityBenderCrafterModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTERealityBenderCrafterModule(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {

    }

    @Override
    public IStructureDefinition<MTERealitySiphonModuleBase> getStructureDefinition() {
        return null;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return null;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return false;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return null;
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
}
