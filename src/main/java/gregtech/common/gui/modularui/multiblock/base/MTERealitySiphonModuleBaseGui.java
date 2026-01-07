package gregtech.common.gui.modularui.multiblock.base;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ListWidget;

import gregtech.common.tileentities.machines.multi.realitysiphon.MTERealitySiphonModuleBase;

public class MTERealitySiphonModuleBaseGui<T extends MTERealitySiphonModuleBase<T>> extends MTEMultiBlockBaseGui<T> {

    private final BooleanSyncValue siphonLinkedSyncer = new BooleanSyncValue(multiblock::isLinked);

    public MTERealitySiphonModuleBaseGui(T multiblock) {
        super(multiblock);
    }

    @Override
    protected ListWidget<IWidget, ?> createTerminalTextWidget(PanelSyncManager syncManager, ModularPanel parent) {
        return super.createTerminalTextWidget(syncManager, parent).childIf(
            () -> !siphonLinkedSyncer.getBoolValue(),
            IKey.lang("GT5U.gui.text.no_target_siphon")
                .asWidget()
                .setEnabledIf((useless) -> !siphonLinkedSyncer.getBoolValue()));
    }

    @Override
    protected void registerSyncValues(PanelSyncManager syncManager) {
        super.registerSyncValues(syncManager);
        syncManager.syncValue("siphonLinked", siphonLinkedSyncer);
    }
}
