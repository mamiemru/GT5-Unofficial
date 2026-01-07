package gregtech.common.tileentities.machines.multi.realitysiphon;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.GTValues.V;
import static gregtech.api.enums.HatchElement.Energy;
import static gregtech.api.enums.HatchElement.ExoticEnergy;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_REALITY_FABRIC_SIPHON_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.enums.VoltageIndex.UIV;
import static net.minecraft.tileentity.TileEntity.INFINITE_EXTENT_AABB;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.jetbrains.annotations.NotNull;

import com.google.common.io.ByteArrayDataInput;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;

import gregtech.api.GregTechAPI;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.SoundResource;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTUtility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.blocks.BlockCasingsAbstract;
import gregtech.common.render.IMTERenderer;
import gregtech.common.render.RealityFabricSiphonRenderer;
import gregtech.common.tileentities.machines.MTERealityPhaseSensor;
import io.netty.buffer.ByteBuf;

public class MTERealityFabricSiphon extends MTEExtendedPowerMultiBlockBase<MTERealityFabricSiphon>
    implements ISurvivalConstructable, IMTERenderer {

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<MTERealityFabricSiphon> STRUCTURE_DEFINITION = StructureDefinition
        .<MTERealityFabricSiphon>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][] {
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 AAA                 ", "                A   A                ",
                        "                A   A                ", "                A   A                ",
                        "                 AAA                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                ABBBA                ", "                B   B                ",
                        "                B   B                ", "                B   B                ",
                        "                ABBBA                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E   E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E   E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E   E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E A E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E D E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E D E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E D E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BEEEB                ", "                E   E                ",
                        "                E D E                ", "                E   E                ",
                        "                BEEEB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                BBBBB                ", "                B   B                ",
                        "                B D B                ", "                B   B                ",
                        "                BBBBB                ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                  B                  ", "                 BDB                 ",
                        "                BDDDB                ", "                 BDB                 ",
                        "                  B                  ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                 BBB                 ",
                        "                BBDBB                ", "               BBD DBB               ",
                        "               BD   DB               ", "               BBD DBB               ",
                        "                BBDBB                ", "                 BBB                 ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BBB                 ", "                BBDBB                ",
                        "               BBD DBB               ", "              BBD   DBB              ",
                        "              BD     DB              ", "              BBD   DBB              ",
                        "               BBD DBB               ", "                BBDBB                ",
                        "                 BBB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 B B                 ", "                BDDDB                ",
                        "               BD   DB               ", "              BD     DB              ",
                        "               D     D               ", "              BD     DB              ",
                        "               BD   DB               ", "                BDDDB                ",
                        "                 B B                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "               B B B B               ", "              BBBDDDBBB              ",
                        "               BD   DB               ", "              BD     DB              ",
                        "               D     D               ", "              BD     DB              ",
                        "               BD   DB               ", "              BBBDDDBBB              ",
                        "               B B B B               ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "              B       B              ",
                        "             BBB B B BBB             ", "              BBBDDDBBB              ",
                        "               BD   DB               ", "              BD     DB              ",
                        "               D     D               ", "              BD     DB              ",
                        "               BD   DB               ", "              BBBDDDBBB              ",
                        "             BBB B B BBB             ", "              B       B              ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "             B         B             ", "            BBB       BBB            ",
                        "             B   B B   B             ", "                BDDDB                ",
                        "               BD   DB               ", "              BD     DB              ",
                        "               D     D               ", "              BD     DB              ",
                        "               BD   DB               ", "                BDDDB                ",
                        "             B   B B   B             ", "            BBB       BBB            ",
                        "             B         B             ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            BB         BB            ", "            B           B            ",
                        "                 BBB                 ", "                BDDDB                ",
                        "               BD   DB               ", "              BD     DB              ",
                        "              BD     DB              ", "              BD     DB              ",
                        "               BD   DB               ", "                BDDDB                ",
                        "                 BBB                 ", "            B           B            ",
                        "            BB         BB            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "            B           B            ",
                        "           BB           BB           ", "                                     ",
                        "                                     ", "                 BBB                 ",
                        "                BDDDB                ", "               BD   DB               ",
                        "               BD   DB               ", "               BD   DB               ",
                        "                BDDDB                ", "                 BBB                 ",
                        "                                     ", "                                     ",
                        "           BB           BB           ", "            B           B            ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "            B           B            ",
                        "           B             B           ", "                                     ",
                        "                                     ", "                                     ",
                        "                 DDD                 ", "                D   D                ",
                        "                D   D                ", "                D   D                ",
                        "                 DDD                 ", "                                     ",
                        "                                     ", "                                     ",
                        "           B             B           ", "            B           B            ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "            B           B            ",
                        "           B             B           ", "                                     ",
                        "                                     ", "                 BBB                 ",
                        "                BDDDB                ", "               BD   DB               ",
                        "               BD   DB               ", "               BD   DB               ",
                        "                BDDDB                ", "                 BBB                 ",
                        "                                     ", "                                     ",
                        "           B             B           ", "            B           B            ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           BB           BB           ",
                        "          BB             BB          ", "                                     ",
                        "                                     ", "                BAAAB                ",
                        "               B DDD B               ", "               ADBABDA               ",
                        "               ADAAADA               ", "               ADBABDA               ",
                        "               B DDD B               ", "                BAAAB                ",
                        "                                     ", "                                     ",
                        "          BB             BB          ", "           BB           BB           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "                ABBBA                ",
                        "               ABAAABA               ", "               BA   AB               ",
                        "               BA   AB               ", "               BA   AB               ",
                        "               ABAAABA               ", "                ABBBA                ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "                BB BB                ",
                        "               BBEEEBB               ", "               BE   EB               ",
                        "                E   E                ", "               BE   EB               ",
                        "               BBEEEBB               ", "                BB BB                ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                  B                  ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "               BB   BB               ",
                        "               BBEEEBB               ", "                E   E                ",
                        "    B           E   E           B    ", "                E   E                ",
                        "               BBEEEBB               ", "               BB   BB               ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                  B                  ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BCB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "               B     B               ",
                        "                BEEEB                ", "    B           E   E           B    ",
                        "    C           E   E           C    ", "    B           E   E           B    ",
                        "                BEEEB                ", "               B     B               ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BCB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BFB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "               B     B               ",
                        "                BEEEB                ", "    B           E   E           B    ",
                        "    F           E   E           F    ", "    B           E   E           B    ",
                        "                BEEEB                ", "               B     B               ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BFB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BCB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "            B           B            ", "           B             B           ",
                        "          B               B          ", "                                     ",
                        "                                     ", "               B     B               ",
                        "                BE~EB                ", "    B           E   E           B    ",
                        "    C           E   E           C    ", "    B           E   E           B    ",
                        "                BEEEB                ", "               B     B               ",
                        "                                     ", "                                     ",
                        "          B               B          ", "           B             B           ",
                        "            B           B            ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     ", "                                     ",
                        "                 BCB                 ", "                                     ",
                        "                                     ", "                                     ",
                        "                                     " },
                    { "                BBBBB                ", "               BBGGGBB               ",
                        "               BGGGGGB               ", "              BBGGGGGBB              ",
                        "              BGGBBBGGB              ", "             BBGGB BGGBB             ",
                        "           BBBGGGB BGGGBBB           ", "          BBGGGGBB BBGGGGBB          ",
                        "         BBGGGGGB   BGGGGGBB         ", "        BBGGGGGGB   BGGGGGGBB        ",
                        "       BBGGBBBGGB   BGGBBBGGBB       ", "      BBGGBBBGGGBBBBBGGGBBBGGBB      ",
                        "      BGGGBBGGGGGGGGGGGGGBBGGGB      ", "     BBGGGBGGGGGGGGGGGGGGGBGGGBB     ",
                        "   BBBGGGGGGGGBBGGGGGBBGGGGGGGGBBB   ", " BBBGGGGGGGGGGBBBGGGBBBGGGGGGGGGGBBB ",
                        "BBGGGGGBBBBBGGGBBBBBBBGGGBBBBBGGGGGBB", "BGGGBBBB   BGGGGBAAABGGGGB   BBBBGGGB",
                        "BGGGB      BGGGGBABABGGGGB      BGGGB", "BGGGBBBB   BGGGGBAAABGGGGB   BBBBGGGB",
                        "BBGGGGGBBBBBGGGBBBBBBBGGGBBBBBGGGGGBB", " BBBGGGGGGGGGGBBBGGGBBBGGGGGGGGGGBBB ",
                        "   BBBGGGGGGGGBBGGGGGBBGGGGGGGGBBB   ", "     BBGGGBGGGGGGGGGGGGGGGBGGGBB     ",
                        "      BGGGBBGGGGGGGGGGGGGBBGGGB      ", "      BBGGBBBGGGBBBBBGGGBBBGGBB      ",
                        "       BBGGBBBGGB   BGGBBBGGBB       ", "        BBGGGGGGB   BGGGGGGBB        ",
                        "         BBGGGGGB   BGGGGGBB         ", "          BBGGGGBB BBGGGGBB          ",
                        "           BBBGGGB BGGGBBB           ", "             BBGGB BGGBB             ",
                        "              BGGBBBGGB              ", "              BBGGGGGBB              ",
                        "               BGGGGGB               ", "               BBGGGBB               ",
                        "                BBBBB                " } }))
        .addElement('A', ofBlock(GregTechAPI.sBlockCasings1, 14))
        .addElement('B', ofBlock(GregTechAPI.sBlockCasings13, 10))
        .addElement('C', ofBlock(GregTechAPI.sBlockCasings13, 11))
        .addElement('D', ofBlock(GregTechAPI.sBlockCasings13, 12))
        .addElement('E', ofBlock(GregTechAPI.sBlockGlass1, 7))
        .addElement(
            'F',
            ofChain(
                HatchElementBuilder.<MTERealityFabricSiphon>builder()
                    .atLeast(RealityFabricSiphonModule.SIPHON_MODULE)
                    .casingIndex(((BlockCasingsAbstract) GregTechAPI.sBlockCasings13).getTextureIndex(11))
                    .hint(1)
                    .build(),
                onElementPass(MTERealityFabricSiphon::onCasingAdded, ofBlock(GregTechAPI.sBlockCasings13, 11))))
        .addElement(
            'G',
            ofChain(
                HatchElementBuilder.<MTERealityFabricSiphon>builder()
                    .atLeast(
                        Energy,
                        ExoticEnergy,
                        MTERealityPhaseSensor.RealityPhaseSensorHatchElement.RealityPhaseSensor)
                    .casingIndex(((BlockCasingsAbstract) GregTechAPI.sBlockCasings13).getTextureIndex(11))
                    .hint(2)
                    .build(),
                onElementPass(MTERealityFabricSiphon::onCasingAdded, ofBlock(GregTechAPI.sBlockCasings13, 11))))
        .build();

    private static final ITexture CASING_TEXTURE = casingTexturePages[16][96 + 10];
    private static final long BASE_ENERGY_CONSUMPTION = V[UIV] * 64;

    // Target reality phase to have 100% efficiency
    private static final double TARGET_REALITY_PHASE = 100;
    // max distance bellow of upper the value for the siphon to get causality
    private static final double MAX_REALITY_PHASE_OFFSET = 35;
    // Number of phase list every PROCESS_TIME
    private static final double PHASE_LOST_PER_CYCLE = 20;
    // Time before checking reality phase
    private static final int PROCESS_TIME = 5;
    // Required causality to create the Condensed Causality Crystal
    private static final double REQUIRED_CONDENSED_CAUSALITY = 4 * 60 * 60;

    private final List<MTERealitySiphonModuleBase<?>> moduleHatches = new ArrayList<>();
    private final List<MTERealityPhaseSensor> sensorHatches = new ArrayList<>();

    private int casingAmount;

    private boolean needToSyncToClient = false;
    private byte renderMode = 3;
    private double realityPhase = 0;
    private double condensedCausality = 0;
    private long siphonActiveTime = 0;

    private double causalityGeneratedPerSec = 0;
    private double consumedCausality = 0;
    private double lastConsumedCausality = 0;

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
            return moduleHatches.add(siphonModule);
        }
        return false;
    }

    protected void onCasingAdded() {
        casingAmount++;
    }

    public int getNumberOfModules() {
        return moduleHatches != null ? moduleHatches.size() : 0;
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
        casingAmount = 0;
        return checkPiece(STRUCTURE_PIECE_MAIN, 18, 28, 16) && casingAmount >= 380;
    }

    @Override
    public void clearHatches() {
        super.clearHatches();
        moduleHatches.clear();
        sensorHatches.clear();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTERealityFabricSiphon(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity baseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
        int colorIndex, boolean active, boolean redstoneLevel) {
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
            .addInfo(
                "The closer the tuned reality phase is to the Reality Phase Coefficient, the better the efficiency")
            .addInfo(
                "The tuned reality phase must be at least " + MAX_REALITY_PHASE_OFFSET
                    + " phase close to the coefficient to work")
            .addInfo("By doing so, a reality siphon is able to be created to collect a stream of reality fabric")
            .addInfo("Tuning the reality phase is done by an external module, up to 4 modules can be install")
            .addInfo("Is it safe ? That's not a question to ask...")
            .addSeparator()
            .beginStructureBlock(37, 30, 37, false)
            .addController("Front center")
            .addCasingInfoRange("Reality Attuned Casing", 380, 404, false)
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
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0D; // 256 block range
    }

    @Override
    protected SoundResource getActivitySoundLoop() {
        return SoundResource.TECTECH_MACHINES_FX_WHOOUM;
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {
        if (getBaseMetaTileEntity().isAllowedToWork()) {

            if (getMaxInputEu() < BASE_ENERGY_CONSUMPTION) {
                mEfficiencyIncrease = 0;
                mMaxProgresstime = 0;
                return CheckRecipeResultRegistry.insufficientPower(BASE_ENERGY_CONSUMPTION);
            }

            lEUt = -BASE_ENERGY_CONSUMPTION;
            causalityGeneratedPerSec = Math.floor(getBaseCausalityGenerated() * getCausalityMultiplicator() * 100.0)
                / 100.0;
            mEfficiencyIncrease = (int) (100_00.0 * getCausalityMultiplicator());
            mMaxProgresstime = PROCESS_TIME * 20;
            consumedCausality = 0;

            if (realityPhase > 0) realityPhase -= Math.min(realityPhase, PHASE_LOST_PER_CYCLE);

            return CheckRecipeResultRegistry.SUCCESSFUL;
        }

        mEfficiencyIncrease = 0;
        mMaxProgresstime = 0;
        return CheckRecipeResultRegistry.NO_RECIPE;
    }

    @Override
    public void onPreTick(IGregTechTileEntity baseMetaTileEntity, long tick) {
        super.onPreTick(baseMetaTileEntity, tick);
        if (baseMetaTileEntity.isServerSide() && baseMetaTileEntity.isActive()) {
            if (tick % 20 == 0) {
                consumedCausality = 0;
            }
            if (causalityGeneratedPerSec > 0) {
                siphonActiveTime++;
                if (siphonActiveTime > REQUIRED_CONDENSED_CAUSALITY)
                    siphonActiveTime = (long) REQUIRED_CONDENSED_CAUSALITY;
            } else siphonActiveTime = 0;
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity baseMetaTileEntity, long tick) {
        super.onPostTick(baseMetaTileEntity, tick);
        if (baseMetaTileEntity.isServerSide()) {
            if (needToSyncToClient) {
                needToSyncToClient = false;
                sendRenderDataToClient(this);
            }
            moduleHatches.forEach(module -> module.setTargetSiphon(this));
            sensorHatches.forEach(sensor -> sensor.updateRedstoneOutput(realityPhase));
            // Using last consumed to prevent seeing value jump from x to 0 then x every seconde
            if (baseMetaTileEntity.isActive() && tick % 20 == 2) {
                lastConsumedCausality = consumedCausality;
                condensedCausality += causalityGeneratedPerSec - consumedCausality;
                if (condensedCausality >= REQUIRED_CONDENSED_CAUSALITY) { // Around 4h when 100% efficiency
                    condensedCausality = REQUIRED_CONDENSED_CAUSALITY;
                    ItemStack ccc = ItemList.Condensed_Causality_Crystal.get(1);
                    if (getControllerSlot() == null) {
                        baseMetaTileEntity.setInventorySlotContents(getControllerSlotIndex(), ccc);
                        condensedCausality -= REQUIRED_CONDENSED_CAUSALITY;
                    } else if (getControllerSlot().isItemEqual(ccc)
                        && getControllerSlot().stackSize < ccc.getMaxStackSize()) {
                            getControllerSlot().stackSize++;
                            condensedCausality -= REQUIRED_CONDENSED_CAUSALITY;
                        }
                }
            }
        }
    }

    public double getBaseCausalityGenerated() {
        double phaseDifference = Math.abs(realityPhase - TARGET_REALITY_PHASE);
        return Math.max(0, 1 - phaseDifference / MAX_REALITY_PHASE_OFFSET);
    }

    public double getCausalityGeneratedPerSec() {
        return causalityGeneratedPerSec - consumedCausality;
    }

    /**
     * Get Causality multiplicator with bonus on accumulated causality and siphon active time
     */
    private double getCausalityMultiplicator() {
        return 1.0 + (condensedCausality / REQUIRED_CONDENSED_CAUSALITY)
            + (siphonActiveTime / REQUIRED_CONDENSED_CAUSALITY);
    }

    public void addRealityPhase(double phase) {
        realityPhase += phase;
        needToSyncToClient = true;
    }

    public boolean addSensorHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity != null && aTileEntity.getMetaTileEntity() instanceof MTERealityPhaseSensor sensor) {
            sensor.updateTexture(aBaseCasingIndex);
            return sensorHatches.add(sensor);
        }
        return false;
    }

    public int getSensorHatchesNum() {
        return sensorHatches.size();
    }

    /**
     * Consume generated causality, return false if not enough
     * 
     * @param causality Amount of causality to consume
     * @return true if success, false otherwise
     */
    public boolean consumeCausality(double causality) {
        if ((causalityGeneratedPerSec - consumedCausality - causality) < 0) return false;
        consumedCausality += causality;
        return true;
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
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

        screenElements.widget(
            TextWidget.dynamicString(
                () -> StatCollector.translateToLocalFormatted("GT5U.gui.text.attached_modules", getNumberOfModules()))
                .setDefaultColor(COLOR_TEXT_WHITE.get())
                .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()));

        screenElements
            .widget(
                new TextWidget()
                    .setStringSupplier(
                        () -> StatCollector
                            .translateToLocalFormatted("GT5U.gui.text.current_reality_phase", round(realityPhase)))
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()))
            .widget(
                new FakeSyncWidget.DoubleSyncer(() -> realityPhase, realityPhase -> this.realityPhase = realityPhase));

        screenElements
            .widget(
                new TextWidget().setStringSupplier(
                    () -> StatCollector
                        .translateToLocalFormatted("GT5U.gui.text.accumulated_causality", round(condensedCausality)))
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()))
            .widget(
                new FakeSyncWidget.DoubleSyncer(
                    () -> condensedCausality,
                    causality -> this.condensedCausality = causality));

        screenElements
            .widget(
                new TextWidget()
                    .setStringSupplier(
                        () -> StatCollector.translateToLocalFormatted(
                            "GT5U.gui.text.generating_causality",
                            round(causalityGeneratedPerSec)))
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()))
            .widget(
                new FakeSyncWidget.DoubleSyncer(
                    () -> causalityGeneratedPerSec,
                    causalityGenerated -> this.causalityGeneratedPerSec = causalityGenerated));

        screenElements
            .widget(
                new TextWidget()
                    .setStringSupplier(
                        () -> StatCollector
                            .translateToLocalFormatted("GT5U.gui.text.used_causality", lastConsumedCausality))
                    .setDefaultColor(COLOR_TEXT_WHITE.get())
                    .setEnabled(widget -> mMachine && getBaseMetaTileEntity().isActive()))
            .widget(
                new FakeSyncWidget.DoubleSyncer(
                    () -> lastConsumedCausality,
                    lastConsumedCausality -> this.lastConsumedCausality = lastConsumedCausality));
    }

    private double round(double value) {
        return Math.round(value * 100) / 100.0;
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
        aNBT.setDouble("condensedCausality", condensedCausality);
        aNBT.setDouble("consumedCausality", consumedCausality);
        aNBT.setDouble("realityPhase", realityPhase);
        aNBT.setDouble("siphonActiveTime", siphonActiveTime);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        renderMode = aNBT.getByte("renderMode");
        condensedCausality = aNBT.getDouble("condensedCausality");
        consumedCausality = aNBT.getDouble("consumedCausality");
        realityPhase = aNBT.getDouble("realityPhase");
        siphonActiveTime = aNBT.getLong("siphonActiveTime");
    }

    @Override
    public void encodeRenderData(ByteBuf buffer) {
        buffer.writeByte(renderMode);
        buffer.writeDouble(realityPhase);
    }

    @Override
    public void decodeRenderData(ByteArrayDataInput buffer) {
        renderMode = buffer.readByte();
        realityPhase = buffer.readDouble();
    }

    public boolean renderSiphon() {
        return (renderMode >> 0b1) == 1;
    }

    public boolean renderCore() {
        return (renderMode & 0b1) == 1;
    }

    @Override
    public ArrayList<String> getSpecialDebugInfo(IGregTechTileEntity baseMetaTileEntity, EntityPlayer player,
        int logLevel, ArrayList<String> list) {
        list.add("Attached Module(s): " + getNumberOfModules());
        list.add("Siphon active time: " + siphonActiveTime);
        list.add("Reality Phase: " + realityPhase);
        list.add("Accumulated Causality: " + condensedCausality);
        list.add("Generating Causality: " + causalityGeneratedPerSec + "/s");
        list.add("Used Causality: " + lastConsumedCausality + "/s");
        return list;
    }
}
