package gregtech.common.tileentities.machines.multi.realitysiphon;

import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;
import static gregtech.api.util.GTUtility.filterValidMTEs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;

import gregtech.api.interfaces.ITexture;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatch;

public abstract class MTERealitySiphonModuleBase<T extends MTERealitySiphonModuleBase<T>>
    extends MTEExtendedPowerMultiBlockBase<T> implements ISurvivalConstructable {

    protected static final ITexture CASING_TEXTURE = casingTexturePages[16][96 + 11]; // casing 13 have an offset of 96
    protected static final String STRUCTURE_PIECE_MAIN = "main";

    protected int casingAmount;
    private MTERealityFabricSiphon siphon;

    private boolean linked = false;

    public MTERealitySiphonModuleBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTERealitySiphonModuleBase(String aName) {
        super(aName);
    }

    public void setTargetSiphon(MTERealityFabricSiphon siphon) {
        this.siphon = siphon;
        this.linked = siphon != null;
    }

    protected void onCasingAdded() {
        casingAmount++;
    }

    public Optional<MTERealityFabricSiphon> updateAndGetTargetSiphon() {
        var siphon = Optional.ofNullable(this.siphon)
            .filter(MetaTileEntity::isValid);
        if (!siphon.isPresent()) {
            this.siphon = null;
            this.linked = false;
        }
        return siphon;
    }

    public boolean isLinked() {
        return linked;
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return false;
    }

    @Override
    public boolean supportsPowerPanel() {
        return false;
    }

    @Override
    public boolean drainEnergyInput(long aEU) {
        if (siphon == null) return false;
        return siphon.drainEnergyInput(aEU);
    }

    @Override
    public long getMaxInputEu() {
        if (siphon == null) return 0;
        return siphon.getMaxInputEu();
    }

    @Override
    public long getMaxInputAmps() {
        if (siphon == null) return 0;
        return siphon.getMaxInputAmps();
    }

    @Override
    public long getEUVar() {
        if (siphon == null) return 0;
        return siphon.getEUVar();
    }

    @Override
    public void setEUVar(long aEnergy) {
        if (siphon == null) return;
        siphon.setEUVar(aEnergy);
    }

    @Override
    protected void setProcessingLogicPower(ProcessingLogic logic) {
        if (siphon == null) {
            super.setProcessingLogicPower(logic);
        } else {
            boolean useSingleAmp = !siphon.isDebugEnergyPresent() && siphon.mEnergyHatches.size() == 1
                && siphon.getExoticEnergyHatches()
                    .isEmpty();
            logic.setAvailableVoltage(siphon.getAverageInputVoltage());
            logic.setAvailableAmperage(useSingleAmp ? 1 : siphon.getMaxInputAmps());
        }
        logic.setAmperageOC(false);
    }

    public List<MTEHatch> getExoticAndNormalEnergyHatchList() {
        List<MTEHatch> tHatches = new ArrayList<>();
        if (siphon != null) {
            tHatches.addAll(filterValidMTEs(siphon.getExoticEnergyHatches()));
            tHatches.addAll(filterValidMTEs(siphon.mEnergyHatches));
        }
        return tHatches;
    }
}
