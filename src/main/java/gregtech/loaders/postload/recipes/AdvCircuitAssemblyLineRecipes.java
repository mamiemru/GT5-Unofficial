package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.ModIDs.NEW_HORIZONS_CORE_MOD;
import static gregtech.api.enums.Mods.SuperSolarPanels;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.recipe.RecipeMaps.advCircuitAssemblylineRecipes;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.recipe.RecipeMaps.nanoForgeRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.NANO_FORGE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.MODULE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;

import net.minecraft.item.ItemStack;

import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import tectech.thing.CustomItemList;

public class AdvCircuitAssemblyLineRecipes implements Runnable {

    @Override
    public void run() {
        registerAcal();
        registerPCBTranscendentFrontierSystem();
        registerDiscountComponent();
        registerEndgameCircuitParts();
        registerAnyCircuitRecipes();
        registerExoticLineRecipe();
        // registerCosmicLineRecipe();
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

    private void registerPCBTranscendentFrontierSystem() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.CircuitAssemblerUMV.get(8L),
                ItemList.Circuit_ExoticMainframe.get(16L),
                ItemList.Robot_Arm_UMV.get(8L),
                ItemList.Field_Generator_UMV.get(64L))
            .fluidInputs(
                Materials.Copper.getPlasma(64 * INGOTS),
                Materials.Space.getMolten(48 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(32 * INGOTS))
            .itemOutputs(ItemList.PCBTFS.get(1L))
            .duration(300 * SECONDS)
            .eut(TierEU.UMV)
            .addTo(spaceAssemblerRecipes);
    }

    private void registerDiscountComponent() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.RadoxPolymer, 4), // here one item
                MaterialsAlloy.OCTIRON.getFoil(2),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.SuperconductorUMVBase, 1),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(GGMaterial.shirabon.getMolten(1))
            .itemOutputs(ItemList.Circuit_Parts_CapacitorISMD.get(64))
            .duration(1)
            .eut(TierEU.UIV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Kevlar, 4),
                MaterialsAlloy.BLACK_TITANIUM.getFoil(2),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.SuperconductorUMVBase, 1),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(GGMaterial.shirabon.getMolten(1))
            .itemOutputs(ItemList.Circuit_Parts_TransistorISMD.get(64))
            .duration(1)
            .eut(TierEU.UIV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.BOTMIUM.getFoil(4),
                MaterialsAlloy.ABYSSAL.getFoil(2),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.SuperconductorUMVBase, 1),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(GGMaterial.shirabon.getMolten(1))
            .itemOutputs(ItemList.Circuit_Parts_DiodeISMD.get(64))
            .duration(1)
            .eut(TierEU.UIV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.ProtoHalkonite, 4),
                MaterialsAlloy.QUANTUM.getFoil(2),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.SuperconductorUMVBase, 1),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(GGMaterial.shirabon.getMolten(1))
            .itemOutputs(ItemList.Circuit_Parts_ResistorISMD.get(64))
            .duration(1)
            .eut(TierEU.UIV)
            .addTo(assemblerRecipes);
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Ichorium, 4),
                MaterialsAlloy.ABYSSAL.getFoil(2),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.SuperconductorUMVBase, 1),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(GGMaterial.shirabon.getMolten(1))
            .itemOutputs(ItemList.Circuit_Parts_InductorISMD.get(64))
            .duration(1)
            .eut(TierEU.UIV)
            .addTo(assemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_CapacitorISMD.get(12),
                ItemList.Circuit_Parts_TransistorISMD.get(12),
                ItemList.Circuit_Parts_InductorISMD.get(12),
                ItemList.Circuit_Parts_ResistorISMD.get(12),
                ItemList.Circuit_Parts_DiodeISMD.get(12),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 1),
                GTUtility.getIntegratedCircuit(9))
            .fluidInputs(Materials.PhononMedium.getFluid(4))
            .itemOutputs(ItemList.Circuit_Parts_UniversalISMD.get(64))
            .duration(1)
            .eut(TierEU.UMV)
            .addTo(assemblerRecipes);

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
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(spaceAssemblerRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Optically_Perfected_CPU.get(2L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(8L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(64L),
                ItemList.Circuit_Wafer_Bioware.get(16L),
                CustomItemList.DATApipe.get(32L),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SpaceTime, 2L),
                ItemList.StableAdhesive.get(48))
            .fluidInputs(
                Materials.DimensionallyShiftedSuperfluid.getFluid(250L),
                Materials.Kevlar.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Cosmic_Super_Ram.get(1L))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .metadata(MODULE_TIER, 2)
            .addTo(spaceAssemblerRecipes);
    }

    private ItemStack[] computeOutputForAnyCircuits(String aItem, int amount) {
        if (amount <= 0) {
            return new ItemStack[0];
        }
        int fullStacks = amount / 64;
        int remainder = amount % 64;
        int totalStacks = remainder > 0 ? fullStacks + 1 : fullStacks;
        ItemStack[] result = new ItemStack[totalStacks];

        int index = 0;
        for (int i = 0; i < fullStacks; i++) {
            result[index++] = getModItem(NEW_HORIZONS_CORE_MOD, aItem, 64, 0);
        }

        if (remainder > 0) {
            result[index] = getModItem(NEW_HORIZONS_CORE_MOD, aItem, remainder, 0);
        }

        return result;
    }

    private void registerAnyCircuitRecipes() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_Simple_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitULV", 64 * 8))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(200 * SECONDS)
            .eut(TierEU.RECIPE_ULV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitLV", 64 * 6))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(250 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitMV", 64 * 4))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(300 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC2.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitHV", 182))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(350 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Wafer_SoC2.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitEV", 128))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(400 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Chip_CrystalSoC.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Yttrium, 2L))
            .itemOutputs(computeOutputForAnyCircuits("item.CircuitIV", 92))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(450 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Yttrium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.CosmicNeutronium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitLuV", 64, 0))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(500 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_Parts_Chip_Bioware.get(1L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitZPM", 46, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(600 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Optically_Perfected_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.EnrichedHolmium, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitUV", 32, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(700 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Cosmic_Super_Ram.get(1L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitUHV", 28, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(900 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Cosmic_Super_Ram.get(2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.SuperconductorUMVBase, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "item.CircuitUEV", 20, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(8 * INGOTS))
            .duration(1200 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

    }

    private void registerExoticLineRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Optically_Compatible_Memory.get(2L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(16L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 16L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(2 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(500L))
            .itemOutputs(ItemList.Circuit_ExoticProcessor.get(1L))
            .duration(150 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_ExoticProcessor.get(1L),
                ItemList.Optically_Compatible_Memory.get(4L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Parts_InductorISMD.get(12L),
                ItemList.Circuit_Parts_ResistorISMD.get(12L),
                ItemList.Circuit_Parts_TransistorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(32L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TranscendentMetal, 16L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(4 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(1000L))
            .itemOutputs(ItemList.Circuit_ExoticAssembly.get(1L))
            .duration(200 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_ExoticAssembly.get(2L),
                ItemList.Optically_Compatible_Memory.get(8L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Parts_DiodeISMD.get(12L),
                ItemList.Circuit_Parts_ResistorISMD.get(12L),
                ItemList.Circuit_Parts_TransistorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(48L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.SpaceTime, 2L))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(8 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(2000L),
                GGMaterial.shirabon.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_ExoticComputer.get(1L))
            .duration(300 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_ExoticComputer.get(2L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Optically_Compatible_Memory.get(16L),
                ItemList.Circuit_Parts_UniversalISMD.get(32L),
                ItemList.Circuit_Wafer_QPIC.get(64L),
                GGMaterial.shirabon.get(OrePrefixes.bolt, 8 * INGOTS))
            .fluidInputs(
                GGMaterial.metastableOganesson.getMolten(16 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(4000L),
                Materials.SixPhasedCopper.getMolten(8 * INGOTS),
                Materials.QuarkGluonPlasma.getFluid(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_ExoticMainframe.get(1L))
            .duration(400 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

    }

    private void registerCosmicLineRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Cosmic_Super_Ram.get(2L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(16L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.SpaceTime, 2L))
            .fluidInputs(
                Materials.Samarium.getPlasma(2 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(500L))
            .itemOutputs(ItemList.Circuit_CosmicProcessor.get(1L))
            .duration(500 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Circuit_CosmicProcessor.get(1L),
                ItemList.Cosmic_Super_Ram.get(4L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Parts_InductorISMD.get(12L),
                ItemList.Circuit_Parts_ResistorISMD.get(12L),
                ItemList.Circuit_Parts_TransistorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(32L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Ichorium, 16L))
            .fluidInputs(
                Materials.Samarium.getPlasma(4 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(1000L))
            .itemOutputs(ItemList.Circuit_CosmicAssembly.get(1L))
            .duration(7500 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Circuit_CosmicAssembly.get(2L),
                ItemList.Cosmic_Super_Ram.get(8L),
                ItemList.Circuit_Parts_UniversalISMD.get(32L),
                ItemList.Circuit_Wafer_QPIC.get(48L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TengamPurified, 8L))
            .fluidInputs(
                Materials.SixPhasedCopper.getPlasma(8 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(2000L),
                Materials.QuarkGluonPlasma.getFluid(8 * INGOTS))
            .itemOutputs(ItemList.Circuit_CosmicComputer.get(1L))
            .duration(1100 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_CosmicComputer.get(2L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Cosmic_Super_Ram.get(16L),
                ItemList.Circuit_Parts_UniversalISMD.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(64L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Flerovium, 8L))
            .fluidInputs(
                Materials.Infinity.getPlasma(32 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(4000L),
                Materials.SixPhasedCopper.getPlasma(8 * INGOTS),
                Materials.MagMatter.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_CosmicMainframe.get(1L))
            .duration(1500 * SECONDS)
            .eut(TierEU.RECIPE_UXV)
            .metadata(MODULE_TIER, 2)
            .addTo(advCircuitAssemblylineRecipes);

    }
}
