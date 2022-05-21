package me.saif.bugreportplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public final class BugReportPlugin extends JavaPlugin implements Listener {

    private Inventory inventory;

    @Override
    public void onEnable() {
        inventory = Bukkit.createInventory(null, 9, "Title");
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onOpenEchest(InventoryOpenEvent event) {
        if (event.getInventory().equals(event.getPlayer().getEnderChest())) {
            event.setCancelled(true);
            event.getPlayer().openInventory(this.inventory);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onCloseInventory(InventoryCloseEvent event) {
        List<String> names = event.getViewers().stream().map(HumanEntity::getName).collect(Collectors.toList());

        this.getLogger().info("Number of viewers: " + names.size());
        this.getLogger().info(names.toString());
    }
}
