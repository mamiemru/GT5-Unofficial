package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.ModIDs.NEW_HORIZONS_CORE_MOD;
import static gregtech.api.enums.Mods.SuperSolarPanels;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.recipe.RecipeMaps.advCircuitAssemblylineRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.recipe.RecipeMaps.nanoForgeRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.NANO_FORGE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.MODULE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import tectech.thing.CustomItemList;

public class AdvCircuitAssemblyLineRecipes implements Runnable {

    @Override
    public void run() {
        registerAcal();
        registerDiscountComponent();
        registerEndgameCircuitParts();
        registerAnyCircuitRecipes();
        registerExoticLineRecipe();
    }

    private void registerAcal() {
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
    }

    private void registerDiscountComponent() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                MaterialsAlloy.BOTMIUM.getFoil(1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.NickelZincFerrite, 1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.NaquadahAlloy, 1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.VibrantAlloy, 1),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Bedrockium, 4))
            .itemOutputs(ItemList.Optical_Cpu_Containment_Housing.get(4))
            .duration(3 * SECONDS)
            .eut(TierEU.UIV)
            .addTo(formingPressRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(getModItem(UniversalSingularities.ID, "universal.general.singularity", 0, 14))
            .itemOutputs(getModItem(SuperSolarPanels.ID, "solarsplitter", 2048 / 32, 0))
            .fluidInputs(
                Materials.ReinforcedGlass.getMolten(18432 / 32 * INGOTS),
                Materials.Sunnarium.getMolten(18432 / 32 * INGOTS),
                Materials.Glowstone.getMolten(4608 / 32 * INGOTS))
            .metadata(NANO_FORGE_TIER, 3)
            .duration(100 * SECONDS)
            .eut(2_000_000_000)
            .addTo(nanoForgeRecipes);

    }

    private void registerEndgameCircuitParts() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Optically_Perfected_CPU.get(2L),
                ItemList.Circuit_Wafer_QuantumCPU.get(64L),
                ItemList.Circuit_Wafer_Ram.get(64L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SpaceTime, 2L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TranscendentMetal, 1L))
            .fluidInputs(Materials.Kevlar.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Exotic_Super_CPU.get(1L))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(spaceAssemblerRecipes);
git
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Optically_Perfected_CPU.get(2L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(8L),
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

    private void registerAnyCircuitRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_Simple_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitULV", 16, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(2 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_ULV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitLV", 16, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitMV", 8, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC2.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitHV", 8, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC2.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitEV", 8, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Chip_CrystalSoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Yttrium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitIV", 8, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Yttrium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.CosmicNeutronium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitLuV", 4, 0))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Parts_Chip_Bioware.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitZPM", 4, 0))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .duration(2 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Optically_Perfected_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.EnrichedHolmium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitUV", 4, 0))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS),
                Materials.QuarkGluonPlasma.getFluid(2 * INGOTS))
            .duration(8 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(advCircuitAssemblylineRecipes);
    }

    private void registerExoticLineRecipe() {
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
                GGMaterial.metastableOganesson.getMolten(6 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(8000L))
            .itemOutputs(ItemList.Circuit_ExoticProcessor.get(1L))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_ExoticProcessor.get(1L),
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
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_ExoticAssembly.get(2L),
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

    }
}
