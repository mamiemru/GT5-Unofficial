package gregtech.api.recipe.maps;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.StatCollector;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizons.modularui.api.drawable.FallbackableUITexture;
import com.gtnewhorizons.modularui.api.drawable.UITexture;
import com.gtnewhorizons.modularui.api.math.Pos2d;
import com.gtnewhorizons.modularui.api.math.Size;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.common.widget.ProgressBar;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.recipe.BasicUIPropertiesBuilder;
import gregtech.api.recipe.NEIRecipePropertiesBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.MethodsReturnNonnullByDefault;
import gregtech.common.gui.modularui.UIHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CausalityRecipeFrontend extends RecipeMapFrontend {

    private static final int OVERLAY_HEIGHT = 89;

    private static final int OFFSET_X = 10;
    private static final int ITEM_OFFSET_Y = 10;
    private static final int FLUID_OFFSET_Y = 67;

    public CausalityRecipeFrontend(BasicUIPropertiesBuilder uiPropertiesBuilder,
        NEIRecipePropertiesBuilder neiPropertiesBuilder) {
        super(
            uiPropertiesBuilder.progressBarTexture(new FallbackableUITexture(GTUITextures.PROGRESSBAR_CAUSALITY)),
            neiPropertiesBuilder.recipeBackgroundSize(new Size(170, OVERLAY_HEIGHT))
                .neiSpecialInfoFormatter(recipeInfo -> {
                    double data = recipeInfo.recipe.getMetadataOrDefault(GTRecipeConstants.CAUSALITY_STREAM, 0.0);
                    return Collections.singletonList(
                        StatCollector.translateToLocalFormatted("GT5U.nei.causality_assembler.causality_stream", data));
                }));
    }

    @Override
    public List<Pos2d> getItemInputPositions(int itemInputCount) {
        return UIHelper.getGridPositions(12, OFFSET_X, ITEM_OFFSET_Y, 4, 3);
    }

    @Override
    public List<Pos2d> getFluidInputPositions(int fluidInputCount) {
        return UIHelper.getGridPositions(4, OFFSET_X, FLUID_OFFSET_Y, 4);
    }

    @Override
    public List<Pos2d> getItemOutputPositions(int itemOutputCount) {
        return ImmutableList.of(new Pos2d(145, 28));
    }

    @Override
    public void addProgressBar(ModularWindow.Builder builder, Supplier<Float> progressSupplier, Pos2d windowOffset) {
        assert uiProperties.progressBarTexture != null;
        UITexture texture = uiProperties.progressBarTexture.get();
        builder.widget(
            new ProgressBar().setTexture(texture, 170)
                .setDirection(uiProperties.progressBarDirection)
                .setProgress(progressSupplier)
                .setSynced(false, false)
                .setPos(new Pos2d(3, 3).add(windowOffset))
                .setSize(new Size(170, OVERLAY_HEIGHT)));
    }
}
