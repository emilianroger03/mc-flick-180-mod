package com.emilian.flick180;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Flick180Client implements ClientModInitializer {
    private static KeyBinding flick180Key;
    private static KeyBinding peekKey;

    private static final long PEEK_DURATION_MS = 25;
    private static boolean isPeeking = false;
    private static long peekStartTime = 0;

    @Override
    public void onInitializeClient() {
        flick180Key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flick180.flick",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KeyBinding.Category.MISC
        ));

        peekKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flick180.peek",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (flick180Key.wasPressed()) {
                client.player.setYaw(client.player.getYaw() + 180f);
            }

            while (peekKey.wasPressed()) {
                client.player.setYaw(client.player.getYaw() + 180f);
                isPeeking = true;
                peekStartTime = System.currentTimeMillis();
            }

            if (isPeeking && System.currentTimeMillis() - peekStartTime >= PEEK_DURATION_MS) {
                client.player.setYaw(client.player.getYaw() + 180f);
                isPeeking = false;
            }
        });
    }
}
