package gregtech.common.gui.modularui.multiblock;

import static net.minecraft.util.StatCollector.translateToLocal;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.layout.Flow;
import com.cleanroommc.modularui.widgets.textfield.TextFieldWidget;

import gregtech.api.modularui2.GTGuiTextures;
import gregtech.common.gui.modularui.multiblock.base.MTEMultiBlockBaseGui;
import gregtech.common.tileentities.machines.multi.MTEAdvCircuitAssemblyLine;

public class MTEAdvancedCircuitAssemblyLineGui extends MTEMultiBlockBaseGui<MTEAdvCircuitAssemblyLine> {

    public MTEAdvancedCircuitAssemblyLineGui(MTEAdvCircuitAssemblyLine multiblock) {
        super(multiblock);
    }

    @Override
    protected void registerSyncValues(PanelSyncManager syncManager) {
        super.registerSyncValues(syncManager);
        IntSyncValue parallelSyncer = new IntSyncValue(multiblock::getSetParallel, multiblock::setSetParallel);
        IntSyncValue durationSyncer = new IntSyncValue(multiblock::getSetDuration, multiblock::setSetDuration);
        syncManager.syncValue("maximumParallels", parallelSyncer);
        syncManager.syncValue("maximumDuration", durationSyncer);
    }

    @Override
    protected Flow createButtonColumn(ModularPanel panel, PanelSyncManager syncManager) {
        return super.createButtonColumn(panel, syncManager).child(createAcalConfigButton(syncManager, panel));
    }

    protected IWidget createAcalConfigButton(PanelSyncManager syncManager, ModularPanel parent) {
        IPanelHandler acalSelectPanel = syncManager
            .panel("acalSelectPanel", (p_syncManager, syncHandler) -> openAcalConfigPanel(syncManager, parent), true);

        return new ButtonWidget<>().size(18)
            .overlay(GTGuiTextures.OVERLAY_BUTTON_BATCH_MODE_ON)
            .tooltip(t -> t.addLine(translateToLocal("GT5U.tpm.parallelwindow")))
            .onMousePressed(mouseButton -> {
                if (!acalSelectPanel.isPanelOpen()) {
                    acalSelectPanel.openPanel();
                } else {
                    acalSelectPanel.closePanel();
                }
                return true;
            });
    }

    private static final int WIDTH = 120;
    private static final int HEIGHT = 100;
    private static final int PADDING_SIDES = 4;

    private Flow buildSelect(String fieldName, IntSyncValue syncer, int paddingTop) {
        Flow holdingColumn = Flow.column()
            .size(WIDTH, HEIGHT / 2)
            .paddingTop(paddingTop);
        holdingColumn.child(
            IKey.lang(fieldName)
                .asWidget()
                .marginBottom(4));
        holdingColumn.child(
            new TextFieldWidget().setFormatAsInteger(true)
                .setNumbers(1, Integer.MAX_VALUE)
                .setTextAlignment(Alignment.CENTER)
                .setDefaultNumber(50)
                .value(syncer)
                .size(WIDTH - PADDING_SIDES * 2, 18)
                .align(Alignment.Center));
        return holdingColumn;

    }

    private ModularPanel openAcalConfigPanel(PanelSyncManager syncManager, ModularPanel parent) {
        ModularPanel returnPanel = new ModularPanel("AcalConfigSelectPanel").size(WIDTH, HEIGHT)
            .relative(parent)
            .leftRel(1)
            .topRel(0.8f);

        IntSyncValue parallelSyncer = syncManager.findSyncHandler("maximumParallels", IntSyncValue.class);
        Flow parallelSelect = buildSelect("GTPP.CC.parallel", parallelSyncer, 6);
        returnPanel.child(parallelSelect);

        IntSyncValue durationSyncer = syncManager.findSyncHandler("maximumDuration", IntSyncValue.class);
        Flow durationSelect = buildSelect("GTPP.CC.duration", durationSyncer, 62);
        returnPanel.child(durationSelect);

        return returnPanel;
    }
}
