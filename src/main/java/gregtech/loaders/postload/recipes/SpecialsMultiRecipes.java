package gregtech.loaders.postload.recipes;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

public class SpecialsMultiRecipes implements Runnable {

    @Override
    public void run() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                ItemList.BendingMachineUMV.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64),
                GregtechItemList.Industrial_PlatePress.get(64)
                )
            .circuit(1)
            .itemOutputs(ItemList.Machine_Multi_Special_DoctorMindBender.get(1L))
            .duration(100 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .addTo(assemblerRecipes);
    }
}
