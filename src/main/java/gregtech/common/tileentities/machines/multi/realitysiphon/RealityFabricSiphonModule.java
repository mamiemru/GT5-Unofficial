package gregtech.common.tileentities.machines.multi.realitysiphon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.util.IGTHatchAdder;

public enum RealityFabricSiphonModule implements IHatchElement<MTERealityFabricSiphon> {

    SIPHON_MODULE(MTERealityFabricSiphon::addModuleToMachineList, MTERealitySiphonModuleBase.class);

    private final List<Class<? extends IMetaTileEntity>> mteClasses;
    private final IGTHatchAdder<MTERealityFabricSiphon> adder;

    @SafeVarargs
    RealityFabricSiphonModule(IGTHatchAdder<MTERealityFabricSiphon> adder,
        Class<? extends IMetaTileEntity>... mteClasses) {
        this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
        this.adder = adder;
    }

    @Override
    public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
        return mteClasses;
    }

    public IGTHatchAdder<? super MTERealityFabricSiphon> adder() {
        return adder;
    }

    @Override
    public long count(MTERealityFabricSiphon mteRealityFabricSiphon) {
        return mteRealityFabricSiphon.getNumberOfModules();
    }
}
