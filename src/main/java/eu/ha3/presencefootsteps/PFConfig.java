package eu.ha3.presencefootsteps;

import java.nio.file.Path;
import java.util.Set;
import eu.ha3.presencefootsteps.config.EntitySelector;
import eu.ha3.presencefootsteps.config.JsonFile;
import eu.ha3.presencefootsteps.sound.generator.Locomotion;
import net.minecraft.CrashReportCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

public class PFConfig extends JsonFile {

    private boolean disabled = false;

    private int volume = 70;
    private int runningVolumeIncrease = 0;

    private int clientPlayerVolume = 100;
    private int otherPlayerVolume = 100;
    private int hostileEntitiesVolume = 100;
    private int passiveEntitiesVolume = 100;

    private int wetSoundsVolume = 100;
    private int foliageSoundsVolume = 100;

    private int maxSteppingEntities = 50;

    private boolean multiplayer = true;
    private boolean global = true;
    private boolean footwear = true;
    private boolean visualiser = false;
    private boolean exclusive = false;

    private Locomotion stance = Locomotion.NONE;

    private EntitySelector targetEntities = EntitySelector.ALL;

    private Set<Identifier> ignoredEntityTypes = Set.of(
            Identifier.withDefaultNamespace("ghast"),
            Identifier.withDefaultNamespace("happy_ghast"),
            Identifier.withDefaultNamespace("phantom")
    );

    public PFConfig(Path file) {
        super(file);
    }

    public boolean isDisabled() {
        return disabled || getGlobalVolume() < 3;
    }

    public boolean getEnabled() {
        return !isDisabled();
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean value) {
        disabled = value;
    }

    public int getGlobalVolume() {
        return Math.clamp(volume, 0, 100);
    }

    public void setGlobalVolume(int value) {
        volume = Math.clamp(value, 0, 100);
    }

    public int getRunningVolumeIncrease() {
        return Math.clamp(runningVolumeIncrease, -100, 100);
    }

    public void setRunningVolumeIncrease(int value) {
        runningVolumeIncrease = Math.clamp(value, -100, 100);
    }

    public int getClientPlayerVolume() {
        return Math.clamp(clientPlayerVolume, 0, 100);
    }

    public void setClientPlayerVolume(int value) {
        clientPlayerVolume = Math.clamp(value, 0, 100);
    }

    public int getOtherPlayerVolume() {
        return Math.clamp(otherPlayerVolume, 0, 100);
    }

    public void setOtherPlayerVolume(int value) {
        otherPlayerVolume = Math.clamp(value, 0, 100);
    }

    public int getHostileEntitiesVolume() {
        return Math.clamp(hostileEntitiesVolume, 0, 100);
    }

    public void setHostileEntitiesVolume(int value) {
        hostileEntitiesVolume = Math.clamp(value, 0, 100);
    }

    public int getPassiveEntitiesVolume() {
        return Math.clamp(passiveEntitiesVolume, 0, 100);
    }

    public void setPassiveEntitiesVolume(int value) {
        passiveEntitiesVolume = Math.clamp(value, 0, 1000);
    }

    public int getWetSoundsVolume() {
        return Math.clamp(wetSoundsVolume, 0, 100);
    }

    public void setWetSoundsVolume(int value) {
        wetSoundsVolume = Math.clamp(value, 0, 100);
    }

    public int getFoliageSoundsVolume() {
        return Math.clamp(foliageSoundsVolume, 0, 100);
    }

    public void setFoliageSoundsVolume(int value) {
        foliageSoundsVolume = Math.clamp(value, 0, 100);
    }

    public int getMaxSteppingEntities() {
        return maxSteppingEntities;
    }

    public void setMaxSteppingEntities(int value) {
        maxSteppingEntities = value;
    }

    public boolean getEnabledMP() {
        return multiplayer;
    }

    public void setMultiplayer(boolean value) {
        multiplayer = value;
    }

    public boolean getGlobal() {
        return global;
    }

    public void setGlobal(boolean value) {
        global = value;
    }

    public boolean getEnabledFootwear() {
        return footwear;
    }

    public void setFootwear(boolean value) {
        footwear = value;
    }

    public boolean isVisualiserRunning() {
        return visualiser;
    }

    public void setVisualiser(boolean value) {
        visualiser = value;
    }

    public boolean isExclusiveMode() {
        return exclusive;
    }

    public void setExclusive(boolean value) {
        exclusive = value;
    }

    public Locomotion getLocomotion() {
        return stance == null ? Locomotion.NONE : stance;
    }

    public void setLocomotion(Locomotion value) {
        stance = value == null ? Locomotion.NONE : value;
    }

    public EntitySelector getEntitySelector() {
        return targetEntities == null ? EntitySelector.ALL : targetEntities;
    }

    public void setEntitySelector(EntitySelector value) {
        targetEntities = value == null ? EntitySelector.ALL : value;
    }

    public boolean isIgnoredForFootsteps(EntityType<?> type) {
        return this.ignoredEntityTypes.contains(EntityType.getKey(type));
    }

    public void populateCrashReport(CrashReportCategory section) {
        section.setDetail("Disabled", getDisabled());
        section.setDetail("Global Volume", volume);
        section.setDetail("User's Selected Stance", getLocomotion());
        section.setDetail("Target Selector", getEntitySelector());
        section.setDetail("Enabled Global", global);
        section.setDetail("Enabled Multiplayer", multiplayer);
    }
}