package gregtech.common.blocks;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;

public class BlockCasings14 extends BlockCasingsAbstract {

    public BlockCasings14() {
        super(ItemCasings.class, "gt.blockcasings14", MaterialCasings.INSTANCE, 16);
        register(0, ItemList.SALCasing);
        register(1, ItemList.SALConcretBase);
        register(2, ItemList.SALSupportStructure);
        register(3, ItemList.SALInternalStructure);
        register(4, ItemList.SALMechanicCasing);
        register(5, ItemList.SALMotor);
    }

    @Override
    public int getTextureIndex(int aMeta) {
        return (16 << 7) | (aMeta + 96);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int ordinalSide, int aMeta) {
        return switch (aMeta) {
            case 0 -> Textures.BlockIcons.SAL_CASING.getIcon();
            case 1 -> Textures.BlockIcons.SAL_CONCRETE_BASE.getIcon();
            case 2 -> (ordinalSide == 0 || ordinalSide == 1)? Textures.BlockIcons.SAL_STRUCTURE_TOP.getIcon(): Textures.BlockIcons.SAL_SUPPORT_STRUCTURE.getIcon();
            case 3 -> Textures.BlockIcons.SAL_INTERNAL_STRUCTURE.getIcon();
            case 4 -> Textures.BlockIcons.SAL_MECHANIC_CASING.getIcon();
            case 5 -> (ordinalSide == 0 || ordinalSide == 1)? Textures.BlockIcons.SAL_MOTOR_TOP.getIcon(): Textures.BlockIcons.SAL_MOTOR.getIcon();
            default -> Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
        };
    }
}

