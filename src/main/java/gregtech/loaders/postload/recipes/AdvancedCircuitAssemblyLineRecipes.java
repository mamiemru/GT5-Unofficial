package gregtech.loaders.postload.recipes;

import static gregtech.api.enums.Mods.ModIDs.NEW_HORIZONS_CORE_MOD;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.SuperSolarPanels;
import static gregtech.api.recipe.RecipeMaps.advancedCircuitAssemblylineRecipes;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.causalityRecipes;
import static gregtech.api.recipe.RecipeMaps.formingPressRecipes;
import static gregtech.api.recipe.RecipeMaps.laserEngraverRecipes;
import static gregtech.api.recipe.RecipeMaps.neutroniumCompressorRecipes;
import static gregtech.api.recipe.RecipeMaps.pcbFactoryRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COMPRESSION_TIER;
import static gregtech.api.util.GTRecipeConstants.PCB_NANITE_MATERIAL;
import static gtPlusPlus.core.material.MaterialsElements.STANDALONE.ADVANCED_NITINOL;
import static gtPlusPlus.core.material.MaterialsElements.STANDALONE.HYPOGEN;
import static gtnhintergalactic.recipe.IGRecipeMaps.MODULE_TIER;
import static gtnhintergalactic.recipe.IGRecipeMaps.spaceAssemblerRecipes;

import net.minecraft.item.ItemStack;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.items.GGMaterial;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtnhintergalactic.recipe.IGRecipeMaps;
import tectech.thing.CustomItemList;

public class AdvancedCircuitAssemblyLineRecipes implements Runnable {

    @Override
    public void run() {
        registerAcal();
        registerComponents();
        registerShortcutRecipe();
        registerAnyCircuitRecipes();
        registerExoticLineRecipe();
        registerCosmicLineRecipe();
        registerTemporalRecipe();
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
            .itemOutputs(ItemList.AdvancedCircuitAssemblyLine.get(1L))
            .duration(150 * SECONDS)
            .eut(TierEU.UEV)
            .addTo(spaceAssemblerRecipes);
    }

    private void registerComponents() {

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                Materials.RadoxPolymer.getPlates(1),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.foil), 20, 10106),
                HYPOGEN.getFoil(20),
                ADVANCED_NITINOL.getFoil(20))
            .fluidInputs(
                Materials.SulfuricAcid.getFluid(707),
                Materials.IronIIIChloride.getFluid(17677),
                Materials.CosmicNeutronium.getMolten(6109))
            .itemOutputs(ItemList.Circuit_Board_Exotic.get(14))
            .duration(15 * SECONDS)
            .eut(TierEU.UHV)
            .metadata(PCB_NANITE_MATERIAL, Materials.Gold)
            .addTo(pcbFactoryRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                Materials.RadoxPolymer.getPlates(1),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.foil), 22, 10106),
                HYPOGEN.getFoil(22),
                ADVANCED_NITINOL.getFoil(22))
            .fluidInputs(
                Materials.SulfuricAcid.getFluid(707),
                Materials.IronIIIChloride.getFluid(17677),
                Materials.CosmicNeutronium.getMolten(6109))
            .itemOutputs(ItemList.Circuit_Board_Exotic.get(18))
            .duration(13 * SECONDS)
            .eut(TierEU.UEV)
            .metadata(PCB_NANITE_MATERIAL, Materials.TranscendentMetal)
            .addTo(pcbFactoryRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(3),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.Ichorium, 1),
                new ItemStack(WerkstoffLoader.items.get(OrePrefixes.foil), 27, 10106),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Void, 47),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Mellion, 13))
            .fluidInputs(
                Materials.SulfuricAcid.getFluid(866L),
                Materials.IronIIIChloride.getFluid(21650L),
                Materials.TranscendentMetal.getMolten(7482L))
            .itemOutputs(ItemList.Circuit_Board_Cosmic.get(12))
            .duration(11 * SECONDS)
            .eut(TierEU.UIV)
            .metadata(PCB_NANITE_MATERIAL, Materials.TranscendentMetal)
            .addTo(RecipeMaps.pcbFactoryRecipes);

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
                MaterialsAlloy.TITANSTEEL.getFoil(2),
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
            .itemInputs(ItemList.Condensed_Causality_Crystal.get(0), ItemList.Circuit_Silicon_Wafer7.get(16L))
            .fluidInputs(Materials.Protomatter.getFluid(INGOTS))
            .itemOutputs(ItemList.Circuit_Wafer_IPCRL.get(1L))
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .addTo(laserEngraverRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Circuit_Wafer_IPCRL.get(1))
            .fluidInputs(Materials.Void.getMolten(8 * INGOTS))
            .itemOutputs(ItemList.Circuit_Wafer_MIPCRL.get(4))
            .metadata(COMPRESSION_TIER, 2)
            .duration(7 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .addTo(neutroniumCompressorRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(ItemList.Circuit_Wafer_IPCRL.get(1))
            .fluidInputs(Materials.Void.getMolten(32 * INGOTS))
            .itemOutputs(ItemList.Circuit_Wafer_MIPCRL.get(32))
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.2)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_CosmicComputer.get(4),
                ItemList.Circuit_ExoticMainframe.get(1),
                ItemList.Circuit_Parts_UniversalISMD.get(64),
                ItemList.Circuit_Wafer_MIPCRL.get(32),
                ItemList.EnergisedTesseract.get(8),
                GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.Infinity, 16),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Ichorium, 8),
                GTOreDictUnificator.get(OrePrefixes.plateSuperdense, Materials.SpaceTime, 2))
            .fluidInputs(
                Materials.Void.getMolten(32 * INGOTS),
                Materials.QuarkGluonPlasma.getFluid(8 * INGOTS),
                Materials.WhiteDwarfMatter.getMolten(18),
                Materials.BlackDwarfMatter.getMolten(18))
            .itemOutputs(ItemList.Chronological_disruption_mainframe.get(1))
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.8)
            .duration(16 * SECONDS)
            .eut(TierEU.RECIPE_UXV)
            .addTo(causalityRecipes);
    }

    private void registerShortcutRecipe() {

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
            .itemInputs(
                gregtech.api.enums.ItemList.Circuit_Chip_Optical.get(1L),
                ItemList.Optical_Cpu_Containment_Housing.get(1L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.InfinityCatalyst, 4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.CosmicNeutronium, 4L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Draconium, 4L),
                CustomItemList.DATApipe.get(1L),
                GGMaterial.atomicSeparationCatalyst.get(OrePrefixes.screw, 4),
                GGMaterial.preciousMetalAlloy.get(OrePrefixes.screw, 4))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(288))
            .itemOutputs(ItemList.Optically_Perfected_CPU.get(1L))
            .metadata(IGRecipeMaps.MODULE_TIER, 1)
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.3)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Optical.get(4L),
                ItemList.Optical_Cpu_Containment_Housing.get(4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.SuperconductorUHVBase, 8L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TengamAttuned, 8L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.DraconiumAwakened, 8L),
                CustomItemList.DATApipe.get(4L),
                GGMaterial.preciousMetalAlloy.get(OrePrefixes.screw, 8),
                // Enriched Naquadah Alloy screw
                GGMaterial.enrichedNaquadahAlloy.get(OrePrefixes.screw, 8))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(576))
            .itemOutputs(ItemList.Optically_Perfected_CPU.get(4L))
            .metadata(IGRecipeMaps.MODULE_TIER, 2)
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.5)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Chip_Optical.get(16L),
                ItemList.Optical_Cpu_Containment_Housing.get(16L),
                MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getScrew(16),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.SpaceTime, 16L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 16L),
                CustomItemList.DATApipe.get(16L),
                // Enriched Naquadah Alloy screw
                GGMaterial.enrichedNaquadahAlloy.get(OrePrefixes.screw, 16),
                // Shirabon screw
                GGMaterial.shirabon.get(OrePrefixes.screw, 16))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(1152))
            .itemOutputs(ItemList.Optically_Perfected_CPU.get(16L))
            .metadata(IGRecipeMaps.MODULE_TIER, 2)
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.8)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                getModItem(OpenComputers.ID, "item", 1L, 39), // Memory tier 3.5
                ItemList.Circuit_Chip_Optical.get(1L),
                CustomItemList.DATApipe.get(4L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Infinity, 8L),
                getModItem(SuperSolarPanels.ID, "solarsplitter", 1L, 0)) // Solar Light Splitter
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(288))
            .itemOutputs(ItemList.Optically_Compatible_Memory.get(2))
            .metadata(IGRecipeMaps.MODULE_TIER, 1)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.2)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                getModItem(OpenComputers.ID, "item", 4L, 39), // Memory tier 3.5
                ItemList.Circuit_Chip_Optical.get(1L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Infinity, 16L),
                getModItem(SuperSolarPanels.ID, "solarsplitter", 4L, 0)) // Solar Light Splitter
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(576))
            .itemOutputs(ItemList.Optically_Compatible_Memory.get(8))
            .metadata(IGRecipeMaps.MODULE_TIER, 2)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.3)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                getModItem(OpenComputers.ID, "item", 16L, 39), // Memory tier 3.5
                ItemList.Circuit_Chip_Optical.get(1L),
                CustomItemList.DATApipe.get(64L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 4L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Infinity, 32L),
                getModItem(SuperSolarPanels.ID, "solarsplitter", 16L, 0)) // Solar Light Splitter
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(1152))
            .itemOutputs(ItemList.Optically_Compatible_Memory.get(32))
            .metadata(IGRecipeMaps.MODULE_TIER, 2)
            .duration(5 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.5)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Multifiberglass.get(1L),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.NaquadahAlloy, 64L),
                GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.LuV), 4L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(8L),
                ItemList.Circuit_Chip_HPIC.get(64L),
                ItemList.Circuit_Parts_DiodeASMD.get(8L),
                ItemList.Circuit_Parts_CapacitorASMD.get(8L),
                ItemList.Circuit_Parts_ResistorASMD.get(8L),
                ItemList.Circuit_Parts_TransistorASMD.get(8L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 64))
            .fluidInputs(Materials.SolderingAlloy.getMolten(720))
            .itemOutputs(ItemList.Energy_LapotronicOrb2.get(1))
            .metadata(IGRecipeMaps.MODULE_TIER, 1)
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.2)
            .addTo(causalityRecipes);

        // Alternate Energy Module Recipe
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Wetware_Extreme.get(1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Bedrockium, 64L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 4L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Chip_UHPIC.get(64L),
                ItemList.Circuit_Parts_DiodeXSMD.get(8L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(8L),
                ItemList.Circuit_Parts_ResistorXSMD.get(8L),
                ItemList.Circuit_Parts_TransistorXSMD.get(8L),
                GTOreDictUnificator.get("wireFineHypogen", 48))
            .fluidInputs(Materials.SolderingAlloy.getMolten(720))
            .itemOutputs(ItemList.Energy_Module.get(1))
            .metadata(IGRecipeMaps.MODULE_TIER, 1)
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.3)
            .addTo(causalityRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Bio_Ultra.get(1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.CosmicNeutronium, 64L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Parts_Crystal_Chip_Master.get(64L),
                ItemList.Circuit_Chip_NPIC.get(64L),
                ItemList.Circuit_Parts_DiodeXSMD.get(32L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(32L),
                ItemList.Circuit_Parts_ResistorXSMD.get(32L),
                ItemList.Circuit_Parts_TransistorXSMD.get(32L),
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SpaceTime, 12L))
            .fluidInputs(Materials.SolderingAlloy.getMolten(720))
            .itemOutputs(ItemList.Energy_Cluster.get(1))
            .metadata(IGRecipeMaps.MODULE_TIER, 1)
            .duration(50 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 0.4)
            .addTo(causalityRecipes);

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
            .eut(TierEU.RECIPE_UIV)
            .metadata(GTRecipeConstants.CAUSALITY_STREAM, 1.2)
            .addTo(causalityRecipes);
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
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Wafer_Simple_SoC.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitULV", 64 * 8))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(200 * SECONDS)
            .eut(TierEU.RECIPE_ULV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Wafer_SoC.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitLV", 64 * 6))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(250 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Wafer_SoC.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.AnnealedCopper, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitMV", 64 * 4))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(300 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Wafer_SoC2.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitHV", 182))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(350 * SECONDS)
            .eut(TierEU.RECIPE_HV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Wafer_SoC2.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitEV", 128))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(400 * SECONDS)
            .eut(TierEU.RECIPE_EV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Chip_CrystalSoC.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Yttrium, 2))
            .itemOutputs(computeOutputForAnyCircuits("CircuitIV", 92))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(450 * SECONDS)
            .eut(TierEU.RECIPE_IV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Yttrium, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.CosmicNeutronium, 2))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "CircuitLuV", 64, 0))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
            .duration(500 * SECONDS)
            .eut(TierEU.RECIPE_LuV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1),
                ItemList.Circuit_Parts_Chip_Bioware.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 2L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Infinity, 2L))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "CircuitZPM", 46, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(600 * SECONDS)
            .eut(TierEU.RECIPE_ZPM)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1),
                ItemList.Optically_Perfected_CPU.get(1),
                ItemList.Optically_Compatible_Memory.get(2),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Ichorium, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Void, 2))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "CircuitUV", 32, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(700 * SECONDS)
            .eut(TierEU.RECIPE_UV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Chronological_disruption_mainframe.get(4),
                ItemList.Exotic_Super_CPU.get(2),
                ItemList.Circuit_Wafer_MIPCRL.get(4),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Infinity, 2),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.ProtoHalkonite, 2))
            .itemOutputs(getModItem(NEW_HORIZONS_CORE_MOD, "CircuitUHV", 24, 0))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
            .duration(800 * SECONDS)
            .eut(TierEU.RECIPE_UHV)
            .addTo(advancedCircuitAssemblylineRecipes);

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
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Exotic.get(1L),
                ItemList.Circuit_ExoticProcessor.get(2L),
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
            .addTo(advancedCircuitAssemblylineRecipes);

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
            .addTo(advancedCircuitAssemblylineRecipes);

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
            .addTo(advancedCircuitAssemblylineRecipes);

    }

    private void registerCosmicLineRecipe() {
        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Exotic_Super_CPU.get(1L),
                ItemList.Circuit_Wafer_MIPCRL.get(1L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Wafer_QPIC.get(16L),
                CustomItemList.DATApipe.get(16L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.SpaceTime, 2L))
            .fluidInputs(
                Materials.Samarium.getPlasma(2 * INGOTS),
                Materials.Void.getMolten(10 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(500L))
            .itemOutputs(ItemList.Circuit_CosmicProcessor.get(1L))
            .duration(200 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Circuit_CosmicProcessor.get(1L),
                ItemList.Circuit_Wafer_MIPCRL.get(1L),
                ItemList.Circuit_Parts_CapacitorISMD.get(12L),
                ItemList.Circuit_Parts_InductorISMD.get(12L),
                ItemList.Circuit_Parts_ResistorISMD.get(12L),
                ItemList.Circuit_Parts_TransistorISMD.get(12L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Ichorium, 16L))
            .fluidInputs(
                Materials.Samarium.getPlasma(4 * INGOTS),
                Materials.Void.getMolten(20 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(1000L))
            .itemOutputs(ItemList.Circuit_CosmicAssembly.get(1L))
            .duration(250 * SECONDS)
            .eut(TierEU.RECIPE_UIV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Cosmic.get(1L),
                ItemList.Circuit_Wafer_MIPCRL.get(2L),
                ItemList.Circuit_CosmicAssembly.get(2L),
                ItemList.Circuit_Parts_UniversalISMD.get(32L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.TengamPurified, 8L))
            .fluidInputs(
                Materials.Void.getMolten(40 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(2000L),
                Materials.QuarkGluonPlasma.getFluid(8 * INGOTS))
            .itemOutputs(ItemList.Circuit_CosmicComputer.get(1L))
            .duration(400 * SECONDS)
            .eut(TierEU.RECIPE_UMV)
            .addTo(advancedCircuitAssemblylineRecipes);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_CosmicComputer.get(2L),
                ItemList.Exotic_Super_CPU.get(2L),
                ItemList.Circuit_Wafer_MIPCRL.get(2L),
                ItemList.Circuit_Parts_UniversalISMD.get(64L),
                GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Flerovium, 8L))
            .fluidInputs(
                Materials.Infinity.getPlasma(32 * INGOTS),
                Materials.DimensionallyShiftedSuperfluid.getFluid(4000L),
                Materials.Void.getMolten(80 * INGOTS),
                Materials.MagMatter.getMolten(4 * INGOTS))
            .itemOutputs(ItemList.Circuit_CosmicMainframe.get(1L))
            .duration(600 * SECONDS)
            .eut(TierEU.RECIPE_UXV)
            .addTo(advancedCircuitAssemblylineRecipes);
    }

    private void registerTemporalRecipe() {

    }
}
