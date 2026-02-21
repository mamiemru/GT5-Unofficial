package gregtech.common.tileentities.machines.multi.prototype;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class PrototypeHatch extends PrototypeConnectorHatch {

    public PrototypeHatch(int id, String name, int tier, String nameRegional) {
        super(id, name, nameRegional, tier, nameRegional);
    }

    public PrototypeHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[0];
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[0];
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new PrototypeHatch(mName, mTier, mDescriptionArray, mTextures);
    }
}
