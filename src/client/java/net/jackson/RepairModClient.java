package net.jackson;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class RepairModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(ClientCommandManager.literal("repairitem")
					.executes(context -> {
						MinecraftClient client = MinecraftClient.getInstance();
						ClientPlayerEntity player = client.player;
						if (player != null) {
							if (client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE) {
								ItemStack heldItem = player.getMainHandStack();
								if (!heldItem.isEmpty()) {
									heldItem.setDamage(0);
									player.sendMessage(Text.literal("Item repaired!"), false);
								} else {
									player.sendMessage(Text.literal("No item in hand to repair!"), false);
								}
							} else {
								player.sendMessage(Text.literal("This command can only be used in Creative mode!"), false);
							}
						}
						return 1;
					}));
		});
	}
}