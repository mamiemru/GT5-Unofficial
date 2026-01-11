package gregtech.common.tileentities.machines;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

/*
 * An interface to use HeatSensor Hatch
 */
public interface IHeatProducer {

    int getHeatSensorHatchNum();

    boolean addHeatSensorHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex);

}
