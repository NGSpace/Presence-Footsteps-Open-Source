package eu.ha3.presencefootsteps.sound;

import java.util.Optional;

import eu.ha3.presencefootsteps.PresenceFootsteps;
import eu.ha3.presencefootsteps.sound.generator.Locomotion;
import eu.ha3.presencefootsteps.sound.generator.StepSoundGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface StepSoundSource {
    Optional<StepSoundGenerator> presenceFootsteps$getStepGenerator(SoundEngine engine);

    boolean presenceFootsteps$isStepBlocked();

    final class Container implements StepSoundSource {
        private Locomotion locomotion;
        private @NotNull Optional<StepSoundGenerator> stepSoundGenerator = Optional.empty();

        private final LivingEntity entity;

        public Container(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public Optional<StepSoundGenerator> presenceFootsteps$getStepGenerator(SoundEngine engine) {
            Locomotion loco = engine.getIsolator().locomotions().lookup(entity);

            if (stepSoundGenerator.isEmpty() || loco != locomotion) {
                locomotion = loco;
                stepSoundGenerator = loco.supplyGenerator(entity, engine);
            }
            return stepSoundGenerator;
        }

        @Override
        public boolean presenceFootsteps$isStepBlocked() {
            SoundEngine engine = PresenceFootsteps.getInstance().getEngine();
            if (!engine.getConfig().isExclusiveMode() && !(entity instanceof PlayerEntity)) {
                return false;
            }
            return engine.isEnabledFor(entity) && presenceFootsteps$getStepGenerator(engine).isPresent();
        }
    }
}
