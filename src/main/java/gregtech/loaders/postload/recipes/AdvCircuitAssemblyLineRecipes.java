package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.advCircuitAssemblylineRecipes;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gtnhintergalactic.recipe.IGRecipeMaps.MODULE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import tectech.thing.CustomItemList;

public class AdvCircuitAssemblyLineRecipes implements Runnable {

    @Override
    public void run() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.CircuitAssemblerUIV.get(8L),
                ItemList.Circuit_OpticalMainframe.get(16L),
                ItemList.Robot_Arm_UIV.get(8L),
                ItemList.Robot_Arm_UEV.get(8L),
                ItemList.Conveyor_Module_UIV.get(16L),
                ItemList.Conveyor_Module_UEV.get(16L))
            .fluidInputs(
                Materials.Kevlar.getMolten(4 * INGOTS),
                Materials.TranscendentMetal.getMolten(16 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(32 * INGOTS))
            .itemOutputs(ItemList.AdvCircuitAssemblyLine.get(1L))
            .duration(150 * SECONDS)
            .eut(TierEU.UEV)
            .addTo(spaceAssemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Optically_Perfected_CPU.get(2L),
                ItemList.Circuit_Wafer_QuantumCPU.get(64L),
                ItemList.Circuit_Wafer_Ram.get(64L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SpaceTime, 4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TranscendentMetal, 8L))
            .fluidInputs(Materials.Kevlar.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Exotic_Super_CPU.get(1L))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(spaceAssemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(2L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(16L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 16L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(14 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(8000L))
            .itemOutputs(ItemList.Circuit_ExoticProcessor.get(1L))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_ExoticProcessor.get(1L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Optically_Compatible_Memory.get(4L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Parts_InductorXSMD.get(64L),
                ItemList.Circuit_Parts_DiodeXSMD.get(64L),
                ItemList.Circuit_Parts_ResistorXSMD.get(64L),
                ItemList.Circuit_Parts_TransistorXSMD.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(32L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TranscendentMetal, 16L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(14 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(8000L))
            .itemOutputs(ItemList.Circuit_ExoticAssembly.get(1L))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_ExoticAssembly.get(2L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Optically_Compatible_Memory.get(8L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Parts_InductorXSMD.get(64L),
                ItemList.Circuit_Parts_DiodeXSMD.get(64L),
                ItemList.Circuit_Parts_ResistorXSMD.get(64L),
                ItemList.Circuit_Parts_TransistorXSMD.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(48L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.SpaceTime, 16L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(32 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(16000L),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_ExoticComputer.get(1L))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_ExoticComputer.get(2L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Optically_Compatible_Memory.get(16L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Parts_InductorXSMD.get(64L),
                ItemList.Circuit_Parts_DiodeXSMD.get(64L),
                ItemList.Circuit_Parts_ResistorXSMD.get(64L),
                ItemList.Circuit_Parts_TransistorXSMD.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(64L),
                CustomItemList.DATApipe.get(16L),
                GGMaterial.shirabon.get(OrePrefixes.bolt, 8 * INGOTS))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(32 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(16000L),
                Materials.SixPhasedCopper.getMolten(64 * INGOTS),
                Materials.QuarkGluonPlasma.getFluid(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_ExoticMainframe.get(1L))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Optically_Perfected_CPU.get(8L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(2L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Wafer_Bioware.get(16L),
                CustomItemList.DATApipe.get(32L),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SpaceTime, 16L),
                ItemList.StableAdhesive.get(48))
            .fluidInputs(
                Materials.DimensionallyShiftedSuperfluid.getFluid(8000L),
                Materials.Kevlar.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Cosmic_Super_Ram.get(1L))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .metadata(MODULE_TIER, 2)
            .addTo(spaceAssemblerRecipes);
    }
}
