package com.matthewperiut.aether.client;

import com.matthewperiut.aether.item.AetherItems;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.OptionalInt;

import static com.matthewperiut.aether.gen.dim.AetherDimensions.MOD_ID;

public class AetherKeybinds {
    public static KeyBinding keyGainLore;

    @EventListener
    public void registerKeyBindings(KeyBindingRegisterEvent event) {
        List<KeyBinding> list = event.keyBindings;
        list.add(keyGainLore = new KeyBinding("key.aether.gainlore", Keyboard.KEY_B));
    }

    @EventListener
    public void keyStateChanged(KeyStateChangedEvent event){
        if(event.environment == KeyStateChangedEvent.Environment.IN_GAME){
            if(Keyboard.getEventKeyState()){
                if (Keyboard.isKeyDown(keyGainLore.code)) {
                    Minecraft mc = ((Minecraft) FabricLoader.getInstance().getGameInstance());
                    @NotNull OptionalInt dimensionId = DimensionRegistry.INSTANCE.getLegacyId(MOD_ID.id("the_aether"));
                    ItemStack book = null;
                    if (dimensionId.isPresent()) {
                        if (mc.player.dimensionId == dimensionId.getAsInt()) {
                            book = new ItemStack(AetherItems.LoreBook, 1, 2);
                        } else if (mc.player.dimensionId == -1) {
                            book = new ItemStack(AetherItems.LoreBook, 1, 1);
                        } else if (mc.player.dimensionId == 0) {
                            book = new ItemStack(AetherItems.LoreBook, 1, 0);
                        }
                    }
                    if (book != null) mc.player.inventory.addStack(book);
                }
            }
        }
    }
}
