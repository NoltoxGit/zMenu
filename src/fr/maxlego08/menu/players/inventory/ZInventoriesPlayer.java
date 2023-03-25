package fr.maxlego08.menu.players.inventory;

import fr.maxlego08.menu.api.players.inventory.InventoriesPlayer;
import fr.maxlego08.menu.api.players.inventory.InventoryPlayer;
import fr.maxlego08.menu.zcore.logger.Logger;
import fr.maxlego08.menu.zcore.utils.storage.Persist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ZInventoriesPlayer implements InventoriesPlayer {

    private static Map<UUID, ZInventoryPlayer> inventories = new HashMap<>();

    @Override
    public void storeInventory(Player player) {
        if (hasSavedInventory(player.getUniqueId())) {
            // Something is wrong
            Logger.info("The plugin tries to save an inventory while the player already has an inventory saved!");
            return;
        }

        ZInventoryPlayer inventoryPlayer = new ZInventoryPlayer();
        inventoryPlayer.storeInventory(player);
        inventories.put(player.getUniqueId(), inventoryPlayer);
    }

    @Override
    public void giveInventory(Player player) {
        Optional<InventoryPlayer> optional = this.getPlayerInventory(player.getUniqueId());
        if (!optional.isEmpty()) {
            InventoryPlayer inventoryPlayer = optional.get();
            inventoryPlayer.giveInventory(player);
            inventories.remove(player.getUniqueId());
        }
    }

    @Override
    public boolean hasSavedInventory(UUID uniqueId) {
        return inventories.containsKey(uniqueId);
    }

    @Override
    public Optional<InventoryPlayer> getPlayerInventory(UUID uniqueId) {
        return Optional.ofNullable(inventories.getOrDefault(uniqueId, null));
    }

    @Override
    public void save(Persist persist) {
        persist.save(this, "players-inventory");
    }

    @Override
    public void load(Persist persist) {
        persist.loadOrSaveDefault(this, ZInventoriesPlayer.class, "players-inventory");
    }


    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (hasSavedInventory(player.getUniqueId())) {
            this.giveInventory(player);
        }
    }
}