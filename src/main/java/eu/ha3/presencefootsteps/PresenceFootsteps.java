package eu.ha3.presencefootsteps;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import eu.ha3.mc.quick.update.UpdateChecker;
import eu.ha3.mc.quick.update.UpdaterConfig;
import eu.ha3.presencefootsteps.sound.SoundEngine;
import eu.ha3.presencefootsteps.util.Edge;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;

public class PresenceFootsteps implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger("PFSolver");

    private static final String MODID = "presencefootsteps";
    private static final KeyMapping.Category KEY_BINDING_CATEGORY = KeyMapping.Category.register(id("category"));
    private static final String UPDATER_ENDPOINT = "https://raw.githubusercontent.com/Sollace/Presence-Footsteps/master/version/latest.json";

    public static final Component MOD_NAME = Component.translatable("mod.presencefootsteps.name");

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MODID, name);
    }

    private static PresenceFootsteps instance;

    public static PresenceFootsteps getInstance() {
        return instance;
    }

    private final Path pfFolder = FabricLoader.getInstance().getConfigDir().resolve("presencefootsteps");
    private final PFConfig config = new PFConfig(pfFolder.resolve("userconfig.json"));
    private final SoundEngine engine = new SoundEngine(config);
    private final PFDebugHud debugHud = new PFDebugHud(engine);

    private final UpdaterConfig updaterConfig = new UpdaterConfig(pfFolder.resolve("updater.json"));
    private final UpdateChecker updater = new UpdateChecker(updaterConfig, MODID, UPDATER_ENDPOINT, (newVersion, _) -> {
        showSystemToast(
                Component.translatable("pf.update.title"),
                Component.translatable("pf.update.text", newVersion.version().getFriendlyString(), newVersion.minecraft().getFriendlyString())
        );
    });

    private final KeyMapping optionsKeyBinding = new KeyMapping("key.presencefootsteps.settings", InputConstants.Type.KEYSYM, InputConstants.KEY_F10, KEY_BINDING_CATEGORY);
    private final KeyMapping toggleKeyBinding = new KeyMapping("key.presencefootsteps.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, KEY_BINDING_CATEGORY);
    private final Edge toggler = new Edge(z -> {
        if (z) {
            config.setDisabled(!config.getDisabled());
            saveAndReloadConfig();
        }
    });

    private final AtomicBoolean configChanged = new AtomicBoolean();
    private boolean prevEnabled = config.getEnabled();


    public PresenceFootsteps() {
        instance = this;
    }

    public PFDebugHud getDebugHud() {
        return debugHud;
    }

    public SoundEngine getEngine() {
        return engine;
    }

    public PFConfig getConfig() {
        return config;
    }

    public KeyMapping getOptionsKeyBinding() {
        return optionsKeyBinding;
    }

    public UpdateChecker getUpdateChecker() {
        return updater;
    }

    @Override
    public void onInitializeClient() {
        updaterConfig.load();
        config.load();

        KeyMappingHelper.registerKeyMapping(optionsKeyBinding);
        KeyMappingHelper.registerKeyMapping(toggleKeyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(SoundEngine.ID, engine);
        DebugScreenEntries.register(PFDebugHud.ID, debugHud);
    }

    private void onTick(Minecraft client) {
        Optional.ofNullable(client.player).filter(e -> !e.isRemoved()).ifPresent(cameraEntity -> {
            if (client.screen == null) {
                if (optionsKeyBinding.isDown()) {
                    client.setScreen(new PFOptionsScreen().build(client.screen));
                }
                toggler.accept(toggleKeyBinding.isDown());
            }

            engine.onFrame(client, cameraEntity);

            if (!FabricLoader.getInstance().isModLoaded("modmenu")) {
                updater.attempt();
            }
        });
    }

    void onEnabledStateChange(boolean enabled) {
        engine.reload();
        showSystemToast(
                MOD_NAME,
                Component.translatable("key.presencefootsteps.toggle." + (enabled ? "enabled" : "disabled")).withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.GRAY)
        );
    }

    public void showSystemToast(Component title, Component body) {
        Minecraft client = Minecraft.getInstance();
        client.getToastManager().addToast(SystemToast.multiline(client, SystemToast.SystemToastId.PACK_LOAD_FAILURE, title, body));
    }

    public void saveAndReloadConfig() {
        config.save();
        boolean enabled = config.getEnabled();
        if (prevEnabled != enabled) {
            onEnabledStateChange(enabled);
            prevEnabled = enabled;
        }
        engine.reload();
    }
}
