package fr.maxlego08.menu.zcore.utils.meta;

import fr.maxlego08.menu.api.utils.MetaUpdater;
import fr.maxlego08.menu.zcore.utils.ZUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ClassicMeta extends ZUtils implements MetaUpdater {
    @Override
    public void updateDisplayName(ItemMeta itemMeta, String text, Player player) {
        itemMeta.setDisplayName(color(papi(text, player)));
    }

    @Override
    public void updateDisplayName(ItemMeta itemMeta, String text, OfflinePlayer offlineplayer) {
        itemMeta.setDisplayName(color(papi(text, offlineplayer)));
    }

    @Override
    public void updateLore(ItemMeta itemMeta, List<String> lore, Player player) {
        itemMeta.setLore(color((papi(lore, player))));
    }

    @Override
    public void updateLore(ItemMeta itemMeta, List<String> lore, OfflinePlayer offlineplayer) {
        itemMeta.setLore(color(papi(lore, offlineplayer)));

    }

    @Override
    public Inventory createInventory(String inventoryName, int size, InventoryHolder inventoryHolder) {
        return Bukkit.createInventory(inventoryHolder, size, color(inventoryName));
    }

    @Override
    public void sendTitle(Player player, String title, String subtitle, long start, long duration, long end) {
        player.sendTitle(color(papi(title, player)), color(papi(subtitle, player)), (int) start, (int) duration, (int) end);
    }

    @Override
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    @Override
    public void openBook(Player player, String title, String author, List<String> lines) {
        player.sendMessage("§cYou cant open a book with your minecraft version !");
    }
}
