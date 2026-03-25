package gregtech.common.items;

import gregtech.api.items.GTGenericItem;
import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static gtPlusPlus.core.lib.GTPPCore.RANDOM;

public class SpaceCarrierDrone extends GTGenericItem implements IElectricItem  {

    private static final int MAX_DURABILITY = 50;
    private static final int MAX_ENERGY = 1000;
    private static final int CHARGE = 10;

    private int durability;
    private int energy;

    public SpaceCarrierDrone(String aUnlocalized, String aEnglish, String aEnglishTooltip) {
        super(aUnlocalized, aEnglish, aEnglishTooltip);
        this.durability = MAX_DURABILITY;
        this.energy = MAX_ENERGY;
        this.setMaxStackSize(1);
    }

    public void use() {
        int drain = 5 + RANDOM.nextInt(16);
        this.energy = Math.max(0, this.energy - drain);
    }

    public void recharge() {
        if (this.energy < MAX_ENERGY) {
            this.energy = Math.min(MAX_ENERGY, this.energy + 10);
        }
    }


    public int getEnergy() { return energy; }
    public int getDurability() { return durability; }

    @Override
    public String toString() {
        return "CarrierDrone{durability=" + durability + ", energy=" + energy + "}";
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return MAX_ENERGY;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return CHARGE;
    }
}
