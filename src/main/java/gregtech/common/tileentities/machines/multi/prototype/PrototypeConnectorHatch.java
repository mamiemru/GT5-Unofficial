package gregtech.common.tileentities.machines.multi.prototype;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

public abstract class PrototypeConnectorHatch extends MTEHatch {

    private int prototypeCoordX = 0;
    private int prototypeCoordY = 0;
    private int prototypeCoordZ = 0;
    private MTEPrototype prototypeController = null;

    protected PrototypeConnectorHatch(int aID, String aName, String aNameRegional, int aTier, String descr) {
        super(aID, aName, aNameRegional, aTier, 0, descr);
    }

    protected PrototypeConnectorHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("eprototypeCoordX", prototypeCoordX);
        aNBT.setInteger("eprototypeCoordY", prototypeCoordY);
        aNBT.setInteger("eprototypeCoordZ", prototypeCoordZ);

    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (aNBT.hasKey("prototypeCoordX") && aNBT.hasKey("prototypeCoordY") && aNBT.hasKey("prototypeCoordZ")) {
            prototypeCoordX = aNBT.getInteger("eprototypeCoordX");
            prototypeCoordY = aNBT.getInteger("eprototypeCoordY");
            prototypeCoordZ = aNBT.getInteger("eprototypeCoordZ");
        }

    }

    public boolean connectToController(MTEPrototype te) {
        prototypeCoordX = te.getBaseMetaTileEntity().getXCoord();
        prototypeCoordY = te.getBaseMetaTileEntity().getYCoord();
        prototypeCoordZ = te.getBaseMetaTileEntity().getZCoord();
        prototypeController = te;
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            EnumChatFormatting.RED +
            translateToLocalFormatted("charge", getCharge()),
            EnumChatFormatting.AQUA +
            translateToLocalFormatted("prototypeCoordX", prototypeCoordX),
            translateToLocalFormatted("prototypeCoordY", prototypeCoordY),
            translateToLocalFormatted("prototypeCoordZ", prototypeCoordZ)
        };
    }

    public int getCharge() {
        if (prototypeController == null) {
            var tileEntity = getBaseMetaTileEntity().getWorld()
                .getTileEntity(prototypeCoordX, prototypeCoordY, prototypeCoordZ);
            if (tileEntity == null) return 0;
            if (!(tileEntity instanceof IGregTechTileEntity gtTileEntity)) return 0;
            var metaTileEntity = gtTileEntity.getMetaTileEntity();
            if (!(metaTileEntity instanceof MTEPrototype)) return 0;
            prototypeController = (MTEPrototype) metaTileEntity;
        }
        return prototypeController.getCharge();
    }
}
