package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.causalityRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipeConstants;
import gregtech.common.items.MetaGeneratedItem03;

public class CausalityAssemblerRecipes implements Runnable {

    @Override
    public void run() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UIV, 4),
                ItemList.Battery_Hull_LV.get(1),
                ItemList.Circuit_Silicon_Wafer.get(1),
                ItemList.Circuit_Silicon_Wafer2.get(1),
                ItemList.Circuit_Silicon_Wafer3.get(1),
                ItemList.Circuit_Silicon_Wafer4.get(1),
                ItemList.Circuit_Silicon_Wafer5.get(1),
                ItemList.Circuit_Silicon_Wafer6.get(1),
                ItemList.Circuit_Silicon_Wafer7.get(1),
                ItemList.AlloySmelterLuV.get(1),
                ItemList.Bottle_Hot_Sauce.get(1),
                ItemList.Bottle_Beer.get(1))
            .fluidInputs(
                Materials.PhononMedium.getFluid(1000),
                Materials.AceticAcid.getFluid(1000),
                Materials.NefariousGas.getFluid(1000),
                Materials.NitricAcid.getFluid(1000))
            .itemOutputs(new ItemStack(MetaGeneratedItem03.INSTANCE, 1, 32166))
            .duration(10 * SECONDS)
            .eut(33554432)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.8)
            .addTo(causalityRecipes);
    }

}
