package gregtech.common.tileentities.machines.multi.acal;

import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;

public abstract class MTEModulableController<T extends MTEModulableController<T>> extends MTEEnhancedMultiBlockBase<T> {

    public MTEModulableController(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEModulableController(String aName) {
        super(aName);
    }

    abstract void registerLinkedUnit(MTEModuleBase<?> unit);

    abstract void unregisterLinkedUnit(MTEModuleBase<?> unit);

}
