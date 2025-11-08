package eu.ha3.presencefootsteps.sound.generator;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import eu.ha3.presencefootsteps.sound.SoundEngine;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public enum Locomotion {
    BIPED((entity, engine) -> new TerrestrialStepSoundGenerator(entity, engine, new Modifier<>())),
    QUADRUPED((entity, engine) -> new TerrestrialStepSoundGenerator(entity, engine, new QuadrupedModifier())),
    FLYING((entity, engine) -> new WingedStepSoundGenerator(entity, engine, new QuadrupedModifier())),
    FLYING_BIPED((entity, engine) -> new WingedStepSoundGenerator(entity, engine, new Modifier<>()));

    private static final Map<String, Locomotion> registry = new Object2ObjectOpenHashMap<>();

    static {
        for (Locomotion i : values()) {
            registry.put(i.name(), i);
            registry.put(String.valueOf(i.ordinal()), i);
        }
    }

    private final BiFunction<LivingEntity, SoundEngine, Optional<StepSoundGenerator>> constructor;

    private final String translationKey = "menu.pf.stance." + name().toLowerCase(Locale.ROOT);

    Locomotion() {
        constructor = (entity, engine) -> Optional.empty();
    }

    Locomotion(BiFunction<LivingEntity, SoundEngine, StepSoundGenerator> gen) {
        constructor = (entity, engine) -> Optional.of(gen.apply(entity, engine));
    }

    public Optional<StepSoundGenerator> supplyGenerator(LivingEntity entity, SoundEngine engine) {
        return constructor.apply(entity, engine);
    }

    public Text getOptionName() {
        return Text.translatable(translationKey);
    }

    public Text getOptionTooltip() {
        return Text.translatable(translationKey + ".tooltip");
    }

    public static Locomotion byName(String name) {
        return registry.getOrDefault(name, BIPED);
    }
}
