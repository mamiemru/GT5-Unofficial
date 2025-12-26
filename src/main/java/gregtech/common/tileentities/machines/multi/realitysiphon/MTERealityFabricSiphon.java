package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.util.GTBoundingBoxUtil.withOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.google.common.io.ByteArrayDataInput;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.alignment.enumerable.Rotation;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTBoundingBoxUtil;
import gregtech.api.util.GTUtility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasingsAbstract;
import gregtech.common.render.IMTERenderer;
import gregtech.common.render.RealityFabricSiphonRenderer;
import gregtech.common.tileentities.debug.MTEAdvDebugStructureWriter;
import io.netty.buffer.ByteBuf;

public class MTERealityFabricSiphon extends MTEEnhancedMultiBlockBase<MTERealityFabricSiphon>
    implements ISurvivalConstructable, IMTERenderer {

    private static final ITexture CASING_TEXTURE = casingTexturePages[16][96 + 10];

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<MTERealityFabricSiphon> STRUCTURE_DEFINITION = StructureDefinition
        .<MTERealityFabricSiphon>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][]{
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                 AAA                 ","                A   A                ","                A   A                ","                A   A                ","                 AAA                 ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                ABBBA                ","                B   B                ","                B   B                ","                B   B                ","                ABBBA                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F   F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F   F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F   F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F A F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F D F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F D F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F D F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BFFFB                ","                F   F                ","                F D F                ","                F   F                ","                BFFFB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                BBBBB                ","                B   B                ","                B D B                ","                B   B                ","                BBBBB                ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                  B                  ","                 BDB                 ","                BDDDB                ","                 BDB                 ","                  B                  ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                 BBB                 ","                BBDBB                ","               BBD DBB               ","               BD   DB               ","               BBD DBB               ","                BBDBB                ","                 BBB                 ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                 BBB                 ","                BBDBB                ","               BBD DBB               ","              BBD   DBB              ","              BD     DB              ","              BBD   DBB              ","               BBD DBB               ","                BBDBB                ","                 BBB                 ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                 B B                 ","                BDDDB                ","               BD   DB               ","              BD     DB              ","               D     D               ","              BD     DB              ","               BD   DB               ","                BDDDB                ","                 B B                 ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","               B B B B               ","              BBBDDDBBB              ","               BD   DB               ","              BD     DB              ","               D     D               ","              BD     DB              ","               BD   DB               ","              BBBDDDBBB              ","               B B B B               ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","              B       B              ","             BBB B B BBB             ","              BBBDDDBBB              ","               BD   DB               ","              BD     DB              ","               D     D               ","              BD     DB              ","               BD   DB               ","              BBBDDDBBB              ","             BBB B B BBB             ","              B       B              ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","             B         B             ","            BBB       BBB            ","             B   B B   B             ","                BDDDB                ","               BD   DB               ","              BD     DB              ","               D     D               ","              BD     DB              ","               BD   DB               ","                BDDDB                ","             B   B B   B             ","            BBB       BBB            ","             B         B             ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            BB         BB            ","            B           B            ","                 BBB                 ","                BDDDB                ","               BD   DB               ","              BD     DB              ","              BD     DB              ","              BD     DB              ","               BD   DB               ","                BDDDB                ","                 BBB                 ","            B           B            ","            BB         BB            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           BB           BB           ","                                     ","                                     ","                 BBB                 ","                BDDDB                ","               BD   DB               ","               BD   DB               ","               BD   DB               ","                BDDDB                ","                 BBB                 ","                                     ","                                     ","           BB           BB           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","                                     ","                                     ","                                     ","                 DDD                 ","                D   D                ","                D   D                ","                D   D                ","                 DDD                 ","                                     ","                                     ","                                     ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","                                     ","                                     ","                 BBB                 ","                BDDDB                ","               BD   DB               ","               BD   DB               ","               BD   DB               ","                BDDDB                ","                 BBB                 ","                                     ","                                     ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           BB           BB           ","          BB             BB          ","                                     ","                                     ","                BAAAB                ","               B DDD B               ","               ADBABDA               ","               ADAAADA               ","               ADBABDA               ","               B DDD B               ","                BAAAB                ","                                     ","                                     ","          BB             BB          ","           BB           BB           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","                ABBBA                ","               ABAAABA               ","               BA   AB               ","               BA   AB               ","               BA   AB               ","               ABAAABA               ","                ABBBA                ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","                BB BB                ","               BBFFFBB               ","               BF   FB               ","                F   F                ","               BF   FB               ","               BBFFFBB               ","                BB BB                ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                  B                  ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","               BB   BB               ","               BBFFFBB               ","                F   F                ","    B           F   F           B    ","                F   F                ","               BBFFFBB               ","               BB   BB               ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                  B                  ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                 BCB                 ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","               B     B               ","                BFFFB                ","    B           F   F           B    ","    C           F   F           C    ","    B           F   F           B    ","                BFFFB                ","               B     B               ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                 BCB                 ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                 BEB                 ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","               B     B               ","                BFFFB                ","    B           F   F           B    ","    E           F   F           E    ","    B           F   F           B    ","                BFFFB                ","               B     B               ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                 BEB                 ","                                     ","                                     ","                                     ","                                     "},
                    {"                                     ","                                     ","                                     ","                                     ","                 BCB                 ","                                     ","                                     ","                                     ","                                     ","                                     ","            B           B            ","           B             B           ","          B               B          ","                                     ","                                     ","               B     B               ","                BF~FB                ","    B           F   F           B    ","    C           F   F           C    ","    B           F   F           B    ","                BFFFB                ","               B     B               ","                                     ","                                     ","          B               B          ","           B             B           ","            B           B            ","                                     ","                                     ","                                     ","                                     ","                                     ","                 BCB                 ","                                     ","                                     ","                                     ","                                     "},
                    {"                BBBBB                ","               BBCCCBB               ","               BCCCCCB               ","              BBCCCCCBB              ","              BCCBBBCCB              ","             BBCCB BCCBB             ","           BBBCCCB BCCCBBB           ","          BBCCCCBB BBCCCCBB          ","         BBCCCCCB   BCCCCCBB         ","        BBCCCCCCB   BCCCCCCBB        ","       BBCCBBBCCB   BCCBBBCCBB       ","      BBCCBBBCCCBBBBBCCCBBBCCBB      ","      BCCCBBCCCCCCCCCCCCCBBCCCB      ","     BBCCCBCCCCCCCCCCCCCCCBCCCBB     ","   BBBCCCCCCCCBBCCCCCBBCCCCCCCCBBB   "," BBBCCCCCCCCCCBBBCCCBBBCCCCCCCCCCBBB ","BBCCCCCBBBBBCCCBBBBBBBCCCBBBBBCCCCCBB","BCCCBBBB   BCCCCBAAABCCCCB   BBBBCCCB","BCCCB      BCCCCBABABCCCCB      BCCCB","BCCCBBBB   BCCCCBAAABCCCCB   BBBBCCCB","BBCCCCCBBBBBCCCBBBBBBBCCCBBBBBCCCCCBB"," BBBCCCCCCCCCCBBBCCCBBBCCCCCCCCCCBBB ","   BBBCCCCCCCCBBCCCCCBBCCCCCCCCBBB   ","     BBCCCBCCCCCCCCCCCCCCCBCCCBB     ","      BCCCBBCCCCCCCCCCCCCBBCCCB      ","      BBCCBBBCCCBBBBBCCCBBBCCBB      ","       BBCCBBBCCB   BCCBBBCCBB       ","        BBCCCCCCB   BCCCCCCBB        ","         BBCCCCCB   BCCCCCBB         ","          BBCCCCBB BBCCCCBB          ","           BBBCCCB BCCCBBB           ","             BBCCB BCCBB             ","              BCCBBBCCB              ","              BBCCCCCBB              ","               BCCCCCB               ","               BBCCCBB               ","                BBBBB                "}
                }
            )
        )
        .addElement('A', ofBlock(GregTechAPI.sBlockCasings1, 14))
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings13, 10))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings13, 11))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 12))
        .addElement('E', HatchElementBuilder.<MTERealityFabricSiphon>builder()
            .atLeast(RealityFabricSiphonModule.SiphonModule)
            .casingIndex( ((BlockCasingsAbstract) GregTechAPI.sBlockCasings13).getTextureIndex(11) )
            .dot(1)
            .buildAndChain(GregTechAPI.sBlockCasings13, 11)
             )
        .addElement('F', ofBlock(GregTechAPI.sBlockGlass1, 7))
        .build();

    private static final AxisAlignedBB[][] BOUNDING_BOXES = GTBoundingBoxUtil.precomputeAABB(
        AxisAlignedBB.getBoundingBox(-33, 0, -32, 2, 200, 35), // Base bounding box
        0, 0, 2   // offset for rotating the bounding box (middle of the render)
    );

    private final List<MTERealitySiphonModuleBase> mModuleHatches = new ArrayList<>();

    private boolean needToSyncToClient = false;
    private byte renderMode = 3;

    private double targetRealityPhase = 100;
    private double machineRealityPhase = 0;
    private double causality = 0;

    private double causalityGenerated = 0;
    private double nextCausalityGenerationCalculation = 0;
    private double consumedCausality = 0;

    public MTERealityFabricSiphon(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTERealityFabricSiphon(String aName) {
        super(aName);
    }

    /**
     * Add module to the module list
     *
     * @param aTileEntity      Project module
     * @param aBaseCasingIndex Index of the casing texture it should take
     * @return True if input entity is a valid module and could be added, else false
     */
    public boolean addModuleToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;

        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;

        if (aMetaTileEntity instanceof MTERealitySiphonModuleBase siphonModule) {
            siphonModule.setTargetSiphon(this);
            return mModuleHatches.add(siphonModule);
        }
        return false;
    }

    public int getNumberOfModules() {
        return mModuleHatches != null ? mModuleHatches.size() : 0;
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, 18, 28, 16);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, ISurvivalBuildEnvironment env) {
        if (mMachine) return -1;
        return survivalBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 18, 28, 16, elementBudget, env, false, true);
    }

    @Override
    public IStructureDefinition<MTERealityFabricSiphon> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mModuleHatches.clear();
        return checkPiece(STRUCTURE_PIECE_MAIN, 18, 28, 16);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTERealityFabricSiphon(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing, int colorIndex, boolean active, boolean redstoneLevel) {
        if (side == facing) {
            if (active) return new ITexture[] { CASING_TEXTURE, TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { CASING_TEXTURE, TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_REALITY_FABRIC_SIPHON)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_REALITY_FABRIC_SIPHON_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { CASING_TEXTURE };
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        final MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Reality Siphon")
            .addInfo("Siphoning the fabric of reality to condense it into usable form")
            .addInfo("Require to be tuned to the reality phase and provided with a catalyst")
            .addInfo("The closer the tuned reality phase is to the Reality Phase Coefficient, the better the efficiency")
            .addInfo("The tuned reality phase must be at least 30 phase close to the coefficient to work")
            .addInfo("By doing so, a reality siphon is able to be created to collect a stream of reality fabric")
            .addInfo("Tuning the reality phase is done by an external module, up to 4 modules can be install")
            .addInfo("Is it safe ? That's not a question to ask...")
            .addSeparator()
            .beginStructureBlock(37, 30, 37, false)
            .addController("Front center")
            .addCasingInfoRange("Reality Attuned Casing", 400, 404, false)
            .addCasingInfoRange("Reality Phase Tuner", 0, 4, false)
            .addCasingInfoExactly("Causality Resistant Glass", 155, false)
            .addCasingInfoExactly("Causality Resistant Casing", 853, false)
            .addCasingInfoExactly("Causality Attraction Engine", 158, false)
            .addCasingInfoExactly("Dimensional Bridge", 62, false)
            .addInputHatch("Any bottom layer casing", 1)
            .addOutputBus("Any bottom layer casing", 1)
            .toolTipFinisher();
        return tt;
    }

    @Override
    public void renderTESR(double x, double y, double z, float timeSinceLastTick) {
        RealityFabricSiphonRenderer.renderTileEntityAt(this, x, y, z, timeSinceLastTick);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox(int x, int y, int z) {
        return withOffset(BOUNDING_BOXES[getDirection().ordinal()][getRotation().getIndex()], x, y, z);
    }

    @Override
    public double getMaxRenderDistanceSquared(){
        return 65536.0D; // 256 block range
    }

    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.TECTECH_MACHINES_FX_WHOOUM;
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        if (getBaseMetaTileEntity().isAllowedToWork()) {
            mEfficiencyIncrease = 10000;
            mMaxProgresstime = 200;

            consumedCausality = 0;
            causality += causalityGenerated;
            //TODO check when like 1000 then create causality crystal
            causalityGenerated = nextCausalityGenerationCalculation;
            nextCausalityGenerationCalculation = getCurrentCausalityGenerated();
            if( machineRealityPhase > 0) machineRealityPhase -= Math.min(machineRealityPhase, 50);

            return CheckRecipeResultRegistry.SUCCESSFUL;
        }

        mEfficiencyIncrease = 0;
        mMaxProgresstime = 0;
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            if(needToSyncToClient) {
                needToSyncToClient = false;
                sendRenderDataToClient(this);
            }
        }
    }

    public double getCurrentCausalityGenerated() {
        double phaseDifference = Math.abs(machineRealityPhase - targetRealityPhase);
        return Math.max(0, 1 - phaseDifference / 30.0);
    }

    public void addRealityPhase(double phase) {
        machineRealityPhase += phase;
        needToSyncToClient = true;
    }

    /**
     * Consume generated causality, return false if not enough
     * @param causality Amount of causality to consume
     * @return true if success, false otherwise
     */
    public boolean consumeCausality(double causality){
        if( (causalityGenerated - consumedCausality - causality) < 0 ) return false;
        consumedCausality += causality;
        return true;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ, ItemStack aTool) {
        renderMode = (byte) ((renderMode + 0b1) % 4);
        String siphonStatus = renderSiphon() ? GTUtility.trans("088", "Enabled") : GTUtility.trans("087", "Disabled");
        String coreStatus = renderCore() ? GTUtility.trans("088", "Enabled") : GTUtility.trans("087", "Disabled");
        GTUtility.sendChatToPlayer(aPlayer, String.format("Rendering: Siphon %s, Core %s", siphonStatus, coreStatus));
        sendRenderDataToClient(this);
    }

    @Override
    protected boolean useMui2() {
        return false;
    }

    @Override
    protected void drawTexts(DynamicPositionedColumn screenElements, SlotWidget inventorySlot) {
        super.drawTexts(screenElements, inventorySlot);

        screenElements.widget(TextWidget
                    .dynamicString(
                        () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.attached_modules", getNumberOfModules()))
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()));

        screenElements.widget(
            new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.current_reality_phase", round(machineRealityPhase)))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive())
        ).widget(new FakeSyncWidget.DoubleSyncer(() -> machineRealityPhase, realityPhase -> this.machineRealityPhase = realityPhase));

        screenElements.widget(
            new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.accumulated_causality", round(causality)))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive())
        ).widget(new FakeSyncWidget.DoubleSyncer(() -> causality, causality -> this.causality = causality));

        screenElements.widget(
            new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.generated_causality", round(causalityGenerated)))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive())
        ).widget(new FakeSyncWidget.DoubleSyncer(() -> causalityGenerated, causalityGenerated -> this.causalityGenerated = causalityGenerated));

        screenElements.widget(
            new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.consumed_causality", consumedCausality))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive())
        ).widget(new FakeSyncWidget.DoubleSyncer(() -> consumedCausality, consumedCausality -> this.consumedCausality = consumedCausality));

        screenElements.widget(
            new TextWidget().setStringSupplier(
                    () -> StatCollector.translateToLocalFormatted(
                        "GT5U.gui.text.remaining_generated_causality",
                        round(causalityGenerated-consumedCausality) ))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive())
        ).widget(new FakeSyncWidget.DoubleSyncer(() -> causalityGenerated, causalityGenerated -> this.causalityGenerated = causalityGenerated));
    }

    private double round(double value){
        return Math.round(value*100)/100.0;
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return false;
    }

    @Override
    public boolean showRecipeTextInGUI() {
        return false;
    }

    @Override
    public boolean supportsPowerPanel() {
        return false;
    }

    @Override
    public boolean isFlipChangeAllowed() {
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setByte("renderMode", renderMode);
        aNBT.setDouble("targetRealityPhase", targetRealityPhase);
        aNBT.setDouble("machineRealityPhase", machineRealityPhase);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        renderMode = aNBT.getByte("renderMode");
        targetRealityPhase = aNBT.getDouble("targetRealityPhase");
        machineRealityPhase = aNBT.getDouble("machineRealityPhase");
    }

    @Override
    public void encodeRenderData(ByteBuf buffer) {
        buffer.writeByte(renderMode);
        buffer.writeDouble(targetRealityPhase);
        buffer.writeDouble(machineRealityPhase);
    }

    @Override
    public void decodeRenderData(ByteArrayDataInput buffer) {
        renderMode = buffer.readByte();
        targetRealityPhase = buffer.readDouble();
        machineRealityPhase = buffer.readDouble();
    }

    public boolean renderSiphon() {
        return (renderMode >> 0b1) == 1;
    }

    public boolean renderCore() {
        return (renderMode & 0b1) == 1;
    }
}
