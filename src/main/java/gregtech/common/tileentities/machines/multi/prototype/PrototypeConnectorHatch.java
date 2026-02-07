package gregtech.common.tileentities.machines.multi.prototype;

import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.implementations.MTEHatch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

public abstract class PrototypeConnectorHatch extends MTEHatch {

    private int prototypeCoordX = 0;
    private int prototypeCoordY = 0;
    private int prototypeCoordZ = 0;
    private MTEPrototype prototypeControler = null;

    public PrototypeConnectorHatch(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription, aTextures);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("eprototypeCoordX", this.prototypeCoordX);
        aNBT.setInteger("eprototypeCoordY", this.prototypeCoordY);
        aNBT.setInteger("eprototypeCoordZ", this.prototypeCoordZ);

    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        if (aNBT.hasKey("prototypeCoordX") && aNBT.hasKey("prototypeCoordY") && aNBT.hasKey("prototypeCoordZ")) {
            this.prototypeCoordX = aNBT.getInteger("eprototypeCoordX");
            this.prototypeCoordY = aNBT.getInteger("eprototypeCoordY");
            this.prototypeCoordZ = aNBT.getInteger("eprototypeCoordZ");
        }

    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            EnumChatFormatting.RED +
            translateToLocalFormatted("charge", this.getCharge()),
            EnumChatFormatting.AQUA +
            translateToLocalFormatted("prototypeCoordX", this.prototypeCoordX),
            translateToLocalFormatted("prototypeCoordY", this.prototypeCoordY),
            translateToLocalFormatted("prototypeCoordZ", this.prototypeCoordZ)
        };
    }

    public int getCharge() {

    }


}
