package gregtech.api.recipe.check;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.NotNull;

public class ResultInsufficientCausality implements CheckRecipeResult {

    private double required;

    ResultInsufficientCausality(double required) {
        this.required = required;
    }

    @Override
    @Nonnull
    public @NotNull String getID() {
        return "insufficient_causality";
    }

    @Override
    public boolean wasSuccessful() {
        return false;
    }

    @Override
    @Nonnull
    public @NotNull String getDisplayString() {
        return Objects
            .requireNonNull(StatCollector.translateToLocalFormatted("GT5U.gui.text.insufficient_causality", required));
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound tag) {
        tag.setDouble("required", required);
        return tag;
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound tag) {
        required = tag.getDouble("required");
    }

    @Override
    @Nonnull
    public @NotNull CheckRecipeResult newInstance() {
        return new ResultInsufficientCausality(0);
    }

    @Override
    public void encode(@Nonnull PacketBuffer buffer) {
        buffer.writeDouble(required);
    }

    @Override
    public void decode(@Nonnull PacketBuffer buffer) {
        required = buffer.readDouble();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultInsufficientCausality that = (ResultInsufficientCausality) o;
        return required == that.required;
    }
}
