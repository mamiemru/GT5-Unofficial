package gregtech.common.gui.modularui.multiblock;

import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ListWidget;

import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;
import gregtech.common.tileentities.machines.multi.spaceassemblyline.MTESpaceAssemblyline;

public class MTESpaceAssemblyLineGUI extends MTEMultiBlockBaseGui<MTESpaceAssemblyline> {

    private final IntSyncValue mMaxProgresstimeSync = new IntSyncValue(multiblock::getMaxProgresstime);

    public MTESpaceAssemblyLineGUI(MTESpaceAssemblyline multiblock) {
        super(multiblock);
    }

    @Override
    protected void registerSyncValues(PanelSyncManager syncManager) {
        super.registerSyncValues(syncManager);
        syncManager.syncValue("mMaxProgresstime", mMaxProgresstimeSync);
    }

    @Override
    protected ListWidget<IWidget, ?> createTerminalTextWidget(PanelSyncManager syncManager, ModularPanel parent) {
        return super.createTerminalTextWidget(syncManager, parent);
    }
}
