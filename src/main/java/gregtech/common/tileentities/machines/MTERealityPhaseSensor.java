package gregtech.common.tileentities.machines;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.IGTHatchAdder;
import gregtech.common.gui.modularui.hatch.MTEHatchRealityPhaseSensorGui;
import gregtech.common.tileentities.machines.multi.realitysiphon.MTERealityFabricSiphon;

public class MTERealityPhaseSensor extends MTEHatch {

    private static final IIconContainer TEXTURE_FRONT = Textures.BlockIcons.OVERLAY_HATCH_HEAT_SENSOR;
    private static final IIconContainer TEXTURE_FRONT_GLOW = Textures.BlockIcons.OVERLAY_HATCH_HEAT_SENSOR_GLOW;

    private double threshold = 0;
    private boolean inverted = false;
    private boolean isOn = false;

    public MTERealityPhaseSensor(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "Read Reality Fabric Siphon current phase.");
    }

    public MTERealityPhaseSensor(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return false;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public boolean allowGeneralRedstoneOutput() {
        return true;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection Side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, ForgeDirection side,
        ItemStack aStack) {
        return false;
    }

    @Override
    public void initDefaultModes(NBTTagCompound aNBT) {
        getBaseMetaTileEntity().setActive(true);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, ForgeDirection side,
        float aX, float aY, float aZ) {
        openGui(aPlayer);
        return true;
    }

    @Override
    public String[] getDescription() {
        return new String[] { "Optional Hatch for reading Reality Fabric Siphon current phase.",
            "Right click to open the GUI and change settings." };
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        threshold = aNBT.getDouble("mThreshold");
        inverted = aNBT.getBoolean("mInverted");
        isOn = aNBT.getBoolean("isOn");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setDouble("mThreshold", threshold);
        aNBT.setBoolean("mInverted", inverted);
        aNBT.setBoolean("isOn", isOn);
    }

    /**
     * Updates redstone output based on the heat of the machine.
     */
    public void updateRedstoneOutput(double phase) {
        isOn = (phase > threshold) ^ inverted;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        ForgeDirection facing = getBaseMetaTileEntity().getFrontFacing();
        if (aBaseMetaTileEntity.isServerSide()) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                aBaseMetaTileEntity
                    .setStrongOutputRedstoneSignal(direction, isOn && direction == facing ? (byte) 15 : 0);
            }
        }
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTERealityPhaseSensor(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(TEXTURE_FRONT), TextureFactory.builder()
            .addIcon(TEXTURE_FRONT_GLOW)
            .glow()
            .build() };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, TextureFactory.of(TEXTURE_FRONT) };
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings uiSettings) {
        return new MTEHatchRealityPhaseSensorGui(this).build(data, syncManager, uiSettings);
    }

    @Override
    protected boolean useMui2() {
        return true;
    }

    public enum RealityPhaseSensorHatchElement implements IHatchElement<MTERealityFabricSiphon> {

        RealityPhaseSensor;

        private final IGTHatchAdder<MTERealityFabricSiphon> adder = MTERealityFabricSiphon::addSensorHatchToMachineList;
        private final List<Class<? extends IMetaTileEntity>> mteClasses = Collections
            .unmodifiableList(Arrays.asList(MTERealityPhaseSensor.class));

        @Override
        public long count(MTERealityFabricSiphon siphon) {
            return siphon.getSensorHatchesNum();
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGTHatchAdder<? super MTERealityFabricSiphon> adder() {
            return adder;
        }

    }

}
