package eu.ha3.presencefootsteps.mixins;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import eu.ha3.presencefootsteps.sound.SoundEngine;
import eu.ha3.presencefootsteps.sound.StepSoundSource;
import eu.ha3.presencefootsteps.sound.generator.StepSoundGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
abstract class MLivingEntity extends Entity implements StepSoundSource {
    MLivingEntity() {super(null, null);}
    @Unique
    private final StepSoundSource stepSoundSource = new StepSoundSource.Container((LivingEntity)(Object)this);
    @Override
    public Optional<StepSoundGenerator> presenceFootsteps$getStepGenerator(SoundEngine engine) {
        return stepSoundSource.presenceFootsteps$getStepGenerator(engine);
    }
    @Override
    public boolean presenceFootsteps$isStepBlocked() {
        return stepSoundSource.presenceFootsteps$isStepBlocked();
    }
}
