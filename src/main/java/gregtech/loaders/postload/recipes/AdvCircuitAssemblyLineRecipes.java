package gregtech.loaders.postload.recipes;

import static gregtech.api.recipe.RecipeMaps.advCircuitAssemblylineRecipes;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gtnhintergalactic.recipe.IGRecipeMaps.MODULE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
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

        RecipeMaps.circuitAssemblerRecipes.getAllRecipes()
            .forEach(
                e -> GTValues.RA.stdBuilder()
                    .itemInputs(e.mInputs)
                    .fluidInputs(e.mFluidInputs)
                    .itemOutputs(e.mOutputs)
                    .duration(e.mDuration)
                    .eut(e.mEUt)
                    .addTo(advCircuitAssemblylineRecipes));

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
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 2),
                ItemList.Circuit_Wetwaresupercomputer.get(2L),
                ItemList.Circuit_Parts_InductorXSMD.get(4L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(4L),
                ItemList.Circuit_Parts_ResistorXSMD.get(4L),
                ItemList.Circuit_Parts_TransistorXSMD.get(4L),
                ItemList.Circuit_Parts_DiodeXSMD.get(4L),
                ItemList.Circuit_Chip_Ram.get(48L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUV, 32L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
            .itemOutputs(ItemList.Circuit_Wetwaremainframe.get(1L))
            .fluidInputs(
                MaterialsAlloy.INDALLOY_140.getFluidStack(20 * INGOTS),
                GTModHandler.getIC2Coolant(10_000),
                Materials.Radon.getGas(2_500))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Bio_Ultra.get(2L),
                ItemList.Circuit_Biowarecomputer.get(2L),
                ItemList.Circuit_Parts_TransistorXSMD.get(4L),
                ItemList.Circuit_Parts_ResistorXSMD.get(4L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(4L),
                ItemList.Circuit_Parts_DiodeXSMD.get(4L),
                ItemList.Circuit_Chip_NOR.get(32L),
                ItemList.Circuit_Chip_Ram.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 32L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
            .itemOutputs(ItemList.Circuit_Biowaresupercomputer.get(1L))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                Materials.BioMediumSterilized.getFluid(10 * INGOTS),
                Materials.SuperCoolant.getFluid(10_000))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 4L),
                ItemList.Circuit_Biowaresupercomputer.get(2L),
                ItemList.Circuit_Parts_InductorXSMD.get(6L),
                ItemList.Circuit_Parts_TransistorXSMD.get(6L),
                ItemList.Circuit_Parts_ResistorXSMD.get(6L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(6L),
                ItemList.Circuit_Parts_DiodeXSMD.get(6L),
                ItemList.Circuit_Chip_Ram.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 32L),
                GTOreDictUnificator.get(OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
            .itemOutputs(ItemList.Circuit_Biomainframe.get(1L))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                Materials.BioMediumSterilized.getFluid(20 * INGOTS),
                Materials.SuperCoolant.getFluid(20_000L))
            .duration(15 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Optical.get(1L),
                ItemList.Circuit_OpticalProcessor.get(2L),
                ItemList.Circuit_Parts_InductorXSMD.get(16L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(20L),
                ItemList.Circuit_Parts_ResistorXSMD.get(20L),
                ItemList.Circuit_Chip_NOR.get(32L),
                ItemList.Circuit_Chip_Ram.get(64L),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.wireFine), 24, 10101),
                GTOreDictUnificator.get(OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L))
            .itemOutputs(ItemList.Circuit_OpticalAssembly.get(1L))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                Materials.Radon.getPlasma(10 * INGOTS),
                Materials.SuperCoolant.getFluid(10_000L),
                new FluidStack(FluidRegistry.getFluid("oganesson"), 500))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Optical.get(2L),
                ItemList.Circuit_OpticalAssembly.get(2L),
                ItemList.Circuit_Parts_TransistorXSMD.get(24L),
                ItemList.Circuit_Parts_ResistorXSMD.get(24L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(24L),
                ItemList.Circuit_Parts_DiodeXSMD.get(24L),
                ItemList.Circuit_Chip_NOR.get(64L),
                ItemList.Circuit_Chip_SoC2.get(32L),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.wireFine), 32, 10101),
                GTOreDictUnificator.get(OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
            .itemOutputs(ItemList.Circuit_OpticalComputer.get(1L))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                Materials.Radon.getPlasma(20 * INGOTS),
                Materials.SuperCoolant.getFluid(20_000),
                new FluidStack(FluidRegistry.getFluid("oganesson"), 1_000))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 8),
                ItemList.Circuit_OpticalComputer.get(2L),
                ItemList.Circuit_Parts_InductorXSMD.get(32L),
                ItemList.Circuit_Parts_TransistorXSMD.get(32L),
                ItemList.Circuit_Parts_ResistorXSMD.get(32L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(32L),
                ItemList.Circuit_Parts_DiodeXSMD.get(32L),
                ItemList.Circuit_Chip_SoC2.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 32L),
                GTOreDictUnificator.get(OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L),
                GTOreDictUnificator.get(OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
            .itemOutputs(ItemList.Circuit_OpticalMainframe.get(1L))
            .fluidInputs(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(40 * INGOTS),
                Materials.Radon.getPlasma(40 * INGOTS),
                Materials.SuperCoolant.getFluid(40_000),
                new FluidStack(FluidRegistry.getFluid("oganesson"), 2_000))
            .duration(25 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(advCircuitAssemblylineRecipes);

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
