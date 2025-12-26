package gregtech.common.tileentities.machines;

import java.util.ArrayList;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

/*
 * An interface to use HeatSensor Hatch
 */
public interface IHeatProducer {

    ArrayList<MTEHeatSensor> sensorHatches = new ArrayList<>();

    default int getHeatSensorHatchNum() {
        return sensorHatches.size();
    }

    default boolean addHeatSensorHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity != null && aTileEntity.getMetaTileEntity() instanceof MTEHeatSensor sensor) {
            sensor.updateTexture(aBaseCasingIndex);
            return sensorHatches.add(sensor);
        }
        return false;
    }

}
