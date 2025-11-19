package eu.ha3.presencefootsteps;

import java.nio.file.Path;
import java.util.Set;

import eu.ha3.presencefootsteps.config.EntitySelector;
import eu.ha3.presencefootsteps.config.JsonFile;
import eu.ha3.presencefootsteps.sound.SoundEngine;
import eu.ha3.presencefootsteps.sound.generator.Locomotion;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class PFConfig extends JsonFile {

    private boolean disabled = false;

    private float volume = 0.7F;
    public float runningVolumeIncrease = 0.0F;

    public float clientPlayerVolume = 1.0F;
    public float otherPlayerVolume = 1.0F;
    public float hostileEntitiesVolume = 1.0F;
    public float passiveEntitiesVolume = 1.0F;

    public float wetSoundsVolume = 0.5F;
    public float foliageSoundsVolume = 1.0F;

    private int maxSteppingEntities = 50;

    private boolean multiplayer = true;
    private boolean global = true;
    private boolean footwear = true;
    private boolean visualiser = false;
    private boolean exclusive = false;

    private Locomotion stance = Locomotion.BIPED;

    private EntitySelector targetEntities = EntitySelector.ALL;

    public Set<Identifier> ignoredEntityTypes = Set.of(
                Identifier.ofVanilla("ghast"),
                Identifier.ofVanilla("happy_ghast"),
                Identifier.ofVanilla("phantom")
            );

    public PFConfig(Path file) {
        super(file);
    }

    public boolean isIgnoredForFootsteps(EntityType<?> type) {
        return this.ignoredEntityTypes.contains(Registries.ENTITY_TYPE.getId(type));
    }

    public void setLocomotion(Locomotion loco) {
        stance = loco;
    }

    public boolean isVisualiserRunning() {
        return visualiser;
    }

    public Locomotion getLocomotion() {
        return stance == null ? Locomotion.BIPED : stance;
    }

    public EntitySelector getEntitySelector() {
        return targetEntities == null ? EntitySelector.ALL : targetEntities;
    }

    public void setEntitySelector(EntitySelector entitySelector) {
        this.targetEntities = entitySelector == null ? EntitySelector.ALL : entitySelector;
    }

    public boolean getEnabledFootwear() {
        return footwear;
    }

    public boolean isExclusiveMode() {
        return exclusive;
    }

    public boolean getEnabledMP() {
        return multiplayer;
    }

    public int getMaxSteppingEntities() {
        return Math.max(1, maxSteppingEntities);
    }

    public boolean getDisabled() {
        return disabled;
    }

    public boolean getEnabled() {
        return !disabled && getGlobalVolume() > 0;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getRunningVolumeIncrease() {
        return MathHelper.clamp((int) (runningVolumeIncrease * 100F), -100, 100);
    }

    public int getGlobalVolume() {
        return MathHelper.clamp((int) (volume * 100F), 0, 100);
    }

    public void setGlobalVolume(int volume) {
        this.volume = volume * 0.01F;
    }

    public void setRunningVolumeIncrease(Integer volume) {
        this.runningVolumeIncrease = volume * 0.01F;
    }

    public void populateCrashReport(CrashReportSection section) {
        section.add("Disabled", getDisabled());
        section.add("Global Volume", volume);
        section.add("User's Selected Stance", getLocomotion());
        section.add("Target Selector", getEntitySelector());
        section.add("Enabled Global", global);
        section.add("Enabled Multiplayer", multiplayer);
    }

    public Integer getPlayerVolume() {
        return (int) (this.clientPlayerVolume * 100F);
    }

    public void setPlayerVolume(Integer integer) {
        this.clientPlayerVolume = integer * 0.01F;
    }

    public Integer getOtherPlayerVolume() {
        return (int) (this.otherPlayerVolume * 100F);
    }

    public void setOtherPlayerVolume(Integer integer) {
        this.otherPlayerVolume = integer * 0.01F;
    }

    public Integer getHostileEntitiesVolume() {
        return (int) (this.hostileEntitiesVolume * 100F);
    }

    public void setHostileEntitiesVolume(Integer integer) {
        this.hostileEntitiesVolume = integer * 0.01F;
    }

    public Integer getPassiveEntitiesVolume() {
        return (int) (this.passiveEntitiesVolume * 100F);
    }

    public void setPassiveEntitiesVolume(Integer integer) {
        this.passiveEntitiesVolume = integer * 0.01F;
    }

    public Integer getWetVolume() {
        return (int) (this.wetSoundsVolume * 100F);
    }

    public void setWetVolume(Integer integer) {
        this.wetSoundsVolume = integer * 0.01F;
    }

    public Integer getFoliageVolume() {
        return (int) (this.foliageSoundsVolume * 100F);
    }

    public void setFoliageVolume(Integer integer) {
        this.foliageSoundsVolume = integer * 0.01F;
    }

    public @NotNull Boolean getMultiplayer() {
        return this.multiplayer;
    }

    public void setMultiplayer(@NotNull Boolean value) {
        this.multiplayer = value;
    }

    public void setEnabledFootwear(@NotNull Boolean value) {
        this.footwear = value;
    }

    public void setExclusiveMode(Boolean value) {
        this.exclusive = value;
    }
}
