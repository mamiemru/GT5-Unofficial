package gregtech.common.tileentities.machines.multi.acal;

import java.util.ArrayList;

import net.minecraft.util.StatCollector;

import com.gtnewhorizon.structurelib.util.Vec3Impl;

import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;

public abstract class MTEACALupgradeBase<T extends MTEEnhancedMultiBlockBase<T>> extends MTEModuleBase<T> {

    protected MTEACALupgradeBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTEACALupgradeBase(String aName) {
        super(aName);
    }

    @Override
    public String[] getInfoData() {
        var ret = new ArrayList<String>();
        // if this unit has a controller, give the coordinates
        if (!controllerCoords.isEmpty()) {
            for (Vec3Impl controllerCoord : controllerCoords) {
                ret.add(
                    StatCollector.translateToLocalFormatted(
                        "GT5U.infodata.advanced_circuit_assembly_line_upgrade_base.linked_at",
                        controllerCoord.get(0),
                        controllerCoord.get(0),
                        controllerCoord.get(0)));
            }
        } else ret.add(
            StatCollector.translateToLocal("GT5U.infodata.advanced_circuit_assembly_line_upgrade_base.not_linked"));
        return ret.toArray(new String[0]);
    }

}
