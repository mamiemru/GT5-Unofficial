package gregtech.common.tileentities.machines.multi.realitysiphon;

import static gregtech.api.enums.Textures.BlockIcons.casingTexturePages;

import java.util.Optional;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizons.modularui.common.widget.DynamicPositionedColumn;
import com.gtnewhorizons.modularui.common.widget.FakeSyncWidget;
import com.gtnewhorizons.modularui.common.widget.SlotWidget;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;

public abstract class MTERealitySiphonModuleBase<T extends MTERealitySiphonModuleBase<T>> extends MTEEnhancedMultiBlockBase<T> implements ISurvivalConstructable {
    protected static final ITexture CASING_TEXTURE = casingTexturePages[16][96 + 11]; // casing 13 have an offset of 96
    protected static final String STRUCTURE_PIECE_MAIN = "main";

    protected int casingAmount;
    protected MTERealityFabricSiphon siphon;

    private boolean linked = false;

    public MTERealitySiphonModuleBase(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public MTERealitySiphonModuleBase(String aName) {
        super(aName);
    }

    public void setTargetSiphon(MTERealityFabricSiphon siphon){
        this.siphon = siphon;
        this.linked = siphon != null;
    }

    protected void onCasingAdded() {
        casingAmount++;
    }

    @Nullable
    public MTERealityFabricSiphon getTargetSiphon(){
        return (MTERealityFabricSiphon) Optional.ofNullable(this.siphon)
            .map(MTERealityFabricSiphon::getBaseMetaTileEntity)
            .map(bMte -> {
                var tile = bMte.getTileEntity(bMte.getXCoord(), bMte.getYCoord(), bMte.getZCoord());
                return tile instanceof IGregTechTileEntity ? (IGregTechTileEntity) tile : null;
            })
            .map(IGregTechTileEntity::getMetaTileEntity)
            .filter(mTile -> mTile instanceof MTERealityFabricSiphon)
            .orElse(null);
    }

    @Override
    protected boolean useMui2() {
        return false;
    }

    @Override
    protected void drawTexts(DynamicPositionedColumn screenElements, SlotWidget inventorySlot) {
        super.drawTexts(screenElements, inventorySlot);
        screenElements
            .widget(
                new TextWidget().setStringSupplier(
                        () -> StatCollector.translateToLocal("GT5U.gui.text.no_target_siphon"))
                    .setDefaultColor(EnumChatFormatting.DARK_RED)
                    .setEnabled(widget -> mMachine && !linked))
            .widget(new FakeSyncWidget.BooleanSyncer(() -> linked, linked -> this.linked = linked));
    }

    @Override
    public boolean getDefaultHasMaintenanceChecks() {
        return false;
    }

    @Override
    public boolean supportsPowerPanel() {
        return false;
    }
}
