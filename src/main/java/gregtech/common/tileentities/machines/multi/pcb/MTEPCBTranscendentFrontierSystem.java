package gregtech.common.tileentities.machines.multi.pcb;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PURIFICATION_PLANT;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PURIFICATION_PLANT_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PURIFICATION_PLANT_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PURIFICATION_PLANT_GLOW;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.INEIPreviewModifier;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;

public class MTEPCBTranscendentFrontierSystem extends MTEPCBUpgradeBase<MTEPCBTranscendentFrontierSystem>
    implements ISurvivalConstructable, INEIPreviewModifier {

    private static final String STRUCTURE_PIECE_TFS_UPGRADE = "transcendentFrontierSystem";
    private static final int HORIZONTAL_OFFSET = 10;
    private static final int VERTICAL_OFFSET = 20;
    private static final int DEPTH_OFFSET = 0;

    private static final String[][] structure = new String[][] {
        // spotless:off
        {"                     ", "                     ", "          A          ", "          A          ", "          A          ", "          A          ", "          A          ", "          A          ", "          A          ", "                     ", "                     ", "                     ", "                     ", "          A          ", "          A          ", "          A          ", "          A          ", "          A          ", "          A          ", "         AAA         ", "         A~A         ", "         AAA         ", "       ACACACA       "},
        {"                     ", "          A          ", "          A          ", "         DDD         ", "        DDDDD        ", "        KKKKK        ", "        DDDDD        ", "         DDD         ", "          A          ", "          A          ", "                     ", "                     ", "          A          ", "          A          ", "         DDD         ", "        DDDDD        ", "        KKKKK        ", "        DDDDD        ", "         DDD         ", "         ABA         ", "         ABA         ", "         AAA         ", "      CBBBABBBC      "},
        {"          A          ", "          A          ", "        DDDDD        ", "       DDDDDDD       ", "       DIIIIID       ", "       KIIIIIK       ", "       DIIIIID       ", "       DDIIIDD       ", "        DDDDD        ", "          A          ", "          A          ", "          A          ", "          A          ", "        DDDDD        ", "       DDDDDDD       ", "       DIIIIID       ", "       KIIIIIK       ", "       DIIIIID       ", "       DDIIIDD       ", "        DDDDD        ", "         ABA         ", "         AAA         ", "     ABBBBABBBBA     "},
        {"          A          ", "         DDD         ", "       DDDDDDD       ", "       DDIIIDD       ", "      DIIIIIIID      ", "      KII   IIK      ", "      DII   IID      ", "       DI   ID       ", "       DDIIIDD       ", "         DDD         ", "         KKK         ", "         KKK         ", "         DDD         ", "       DDDDDDD       ", "       DDIIIDD       ", "      DIIIIIIID      ", "      KII   IIK      ", "      DII   IID      ", "       DI   ID       ", "       DDIIIDD       ", "         DDD         ", "        AAAAA        ", " CACACBBBBABBBBCACAC "},
        {"          A          ", "        DDDDD        ", "       DDIIIDD       ", "      DDIIIIIDD      ", "      DII   IID      ", "      KI     IK      ", "      DI     ID      ", "      DI     ID      ", "       DI   ID       ", "        DIIID        ", "        KIIIK        ", "        KIIIK        ", "        DIIID        ", "       DDIIIDD       ", "      DDIIIIIDD      ", "      DII   IID      ", "    HHKI     IKHH    ", "   H  DI     ID  H   ", "   H  DI     ID  H   ", "   H   DI   ID   H   ", "   H    DIIID    H   ", "    H  AADDDAA  H    ", " ABBBABBBBABBBBABBBA "},
        {"       AAAAAAA       ", "      AADDDDDAA      ", "     AADDIIIDDAA     ", "     ADDIIIIIDDA     ", "     ADII   IIDA     ", "     AKI     IKA     ", "     ADI     IDA     ", "     ADI     IDA     ", "     AADI   IDAA     ", "      AADI IDAA      ", "       AKI IKA       ", "       AKI IKA       ", "      AADI IDAA      ", "     AADDI IDDAA     ", "     ADDII IIDDA     ", "    HADII   IIDAH    ", "   HGGKI     IKGGH   ", "  HGHADI     IDAHGH  ", "  HGHADI     IDAHGH  ", "  HGHAADI   IDAAHGH  ", "  HGHHAADIIIDAAHHGH  ", "   HGHHAADDDAAHHGH   ", " CBBBCAAAAAAAAACBBBC "},
        {"          A          ", "        DDDDD        ", "       DDIIIDD       ", "      DDIIIIIDD      ", "      DII   IID      ", "      KI     IK      ", "      DI     ID      ", "      DI     ID      ", "       DI   ID       ", "        DIIID        ", "        KIIIK        ", "        KIIIK        ", "        DIIID        ", "       DDIIIDD       ", "      DDIIIIIDD      ", "      DII   IID      ", "    HHKI     IKHH    ", "   H  DI     ID  H   ", "   H  DI     ID  H   ", "   H   DI   ID   H   ", "   H    DIIID    H   ", "    H  AADDDAA  H    ", " ABBBABBBBABBBBABBBA "},
        {"          A          ", "         DDD         ", "       DDDDDDD       ", "       DIIIIDD       ", "      DIIIIIIID      ", "      KII   IIK      ", "      DII   IID      ", "       DI   ID       ", "       DDIIIDD       ", "         DDD         ", "         KKK         ", "         KKK         ", "         DDD         ", "       DDDDDDD       ", "       DIIIIDD       ", "      DIIIIIIID      ", "      KII   IIK      ", "      DII   IID      ", "       DI   ID       ", "       DDIIIDD       ", "         DDD         ", "        AAAAA        ", " CACACBBBBABBBBCACAC "},
        {"          A          ", "          A          ", "        DDDDD        ", "       DDDDDDD       ", "       DIIIIID       ", "      HFIIIIIFH      ", "       DIIIIID       ", "       DDIIIDD       ", "        DDDDD        ", "          A          ", "          A          ", "          A          ", "          A          ", "        DDDDD        ", "       DDDDDDD       ", "       DIIIIID       ", "       KIIIIIK       ", "       DIIIIID       ", "       DDIIIDD       ", "        DDDDD        ", "          A          ", "         AAA         ", "   E ABBBBABBBBA E   "},
        {"                     ", "          A          ", "          A          ", "         DDD         ", "      H DDDDD H      ", "     HFHKKKKKHFH     ", "      H DDDDD H      ", "         DDD         ", "          A          ", "          A          ", "                     ", "                     ", "          A          ", "          A          ", "         DDD         ", "        DDDDD        ", "        KKKKK        ", "        DDDDD        ", "         DDD         ", "          A          ", "          A          ", "          H          ", "   E  CBBBABBBC  E   "},
        {"                     ", "                     ", "          A          ", "          A          ", "     H    A    H     ", "    HFH   A   HFH    ", "     H    A    H     ", "          A          ", "          A          ", "                     ", "                     ", "                     ", "                     ", "          A          ", "          A          ", "          A          ", "         HGH         ", "          A          ", "          A          ", "          A          ", "          H          ", "          H          ", "   E   ACACACA   E   "},
        {"                     ", "                     ", "                     ", "                     ", "    H           H    ", "   HFH         HFH   ", "   HH           HH   ", "                     ", "                     ", "                     ", "                     ", "  A               A  ", "                     ", "                     ", "                     ", "          H          ", "  A      HGH      A  ", "          H          ", "          H          ", "          H          ", "          H          ", "         HGH         ", "CACEE   ABBBA   EECAC"},
        {"                     ", "                     ", "                     ", "                     ", "                     ", "   HH           HH   ", "  HFH           HFH  ", "  HH             HH  ", "  H               H  ", "  H               H  ", "  H               H  ", " AAA             AAA ", "  H               H  ", "  H               H  ", "  H               H  ", "  H               H  ", " AAA      H      AAA ", "  H      HGH      H  ", "  H      HGH      H  ", "  H      HGH      H  ", "  H      HGH      H  ", "  H       H       H  ", "ABBBEEEECBBBCEEEEBBBA"},
        {"                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "  HH             HH  ", " HFH             HFH ", " HFH             HFH ", " HFH             HFH ", " HFH             HFH ", "AAFAA           AAFAA", " HFH             HFH ", " HFH             HFH ", " HFH             HFH ", " HFH             HFH ", "AAFAA           AAFAA", " HFH      H      HFH ", " HFH      H      HFH ", " HFH      H      HFH ", " HFH      H      HFH ", " HFH             HFH ", "CBBBC   ABBBA   CBBBC"},
        {"                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "  H               H  ", "  H               H  ", "  H               H  ", "  H               H  ", " AAA             AAA ", "  H               H  ", "  H               H  ", "  H               H  ", "  H               H  ", " AAA             AAA ", "  H               H  ", "  H               H  ", "  H               H  ", "  H               H  ", "  H               H  ", "ABBBA   CACAC   ABBBA"},
        {"                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "                     ", "  A               A  ", "                     ", "                     ", "                     ", "                     ", "  A               A  ", "                     ", "                     ", "                     ", "                     ", "                     ", "CACAC           CACAC"}
        //spotless:on
    };

    private static final IStructureDefinition<MTEPCBTranscendentFrontierSystem> STRUCTURE_DEFINITION = StructureDefinition
        .<MTEPCBTranscendentFrontierSystem>builder()
        .addShape(STRUCTURE_PIECE_TFS_UPGRADE, structure)
        .addElement('A', ofBlock(GregTechAPI.sBlockCasings1, 12)) // dtpf casing
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings1, 13)) // dtpf injection
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings1, 14)) // dtpf bridge
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings10, 11)) // black hole cassing
        .addElement('E', ofBlock(GregTechAPI.sBlockCasings11, 7)) // black plutonium pipe
        .addElement('F', ofBlock(GregTechAPI.sBlockCasings5, 13)) // eternal coil
        .addElement('G', ofBlock(GregTechAPI.sBlockCasings8, 14)) // blue cooled cassing
        .addElement('H', ofBlock(GregTechAPI.sBlockGlass1, 3)) // blue glass
        .addElement('I', ofBlock(GregTechAPI.sBlockMetal9, 3)) // spacetime block
        .addElement('K', ofBlock(GregTechAPI.sBlockGlass1, 4)) // purple glass
        .build();

    public MTEPCBTranscendentFrontierSystem(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEPCBTranscendentFrontierSystem(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_TFS_UPGRADE, stackSize, hintsOnly, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        int built = survivalBuildPiece(
            STRUCTURE_PIECE_TFS_UPGRADE,
            stackSize,
            HORIZONTAL_OFFSET,
            VERTICAL_OFFSET,
            DEPTH_OFFSET,
            elementBudget,
            env,
            true);
        if (built == -1) {
            GTUtility.sendChatToPlayer(env.getActor(), EnumChatFormatting.GREEN + "Auto placing done!");
            return 0;
        }
        return built;
    }

    @Override
    public IStructureDefinition<MTEPCBTranscendentFrontierSystem> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("PCB Factory Upgrade, TFS")
            .addInfo(EnumChatFormatting.GRAY + "It enables nanites to construct spacial circuitry.")
            .addInfo(EnumChatFormatting.GRAY + "Required for Cosmic and Temporally Transcendent boards.")
            .addInfo(
                EnumChatFormatting.GRAY + "Place the controller block within "
                    + EnumChatFormatting.RED
                    + MTEPCBFactory.UPGRADE_RANGE
                    + EnumChatFormatting.GRAY
                    + " blocks of the PCB Factory")
            .addInfo(EnumChatFormatting.GRAY + "Left click the PCB Factory controller with a data stick,")
            .addInfo(EnumChatFormatting.GRAY + "then right click this controller to link.")
            .addInfo(EnumChatFormatting.GRAY + "Can connect to many PCB Factories!")
            .addController("Front Center")
            .addStructureInfo(EnumChatFormatting.GRAY + "Does not require maintenance or power.")
            .toolTipFinisher();
        return tt;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEPCBTranscendentFrontierSystem(this.mName);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return new String[0]; // wtf why do I need this?
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean active, boolean redstoneLevel) {
        if (side == facing) {
            if (active) return new ITexture[] {
                Textures.BlockIcons
                    .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 12)),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PURIFICATION_PLANT_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PURIFICATION_PLANT_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] {
                Textures.BlockIcons
                    .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 12)),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PURIFICATION_PLANT)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PURIFICATION_PLANT_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons
            .getCasingTextureForId(GTUtility.getCasingTextureIndex(GregTechAPI.sBlockCasings1, 12)) };
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_TFS_UPGRADE, HORIZONTAL_OFFSET, VERTICAL_OFFSET, DEPTH_OFFSET);
    }
}
