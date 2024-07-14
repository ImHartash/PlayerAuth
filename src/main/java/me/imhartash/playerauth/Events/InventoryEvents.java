package me.imhartash.playerauth.Events;

import me.imhartash.playerauth.PlayerAuth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryEvents implements Listener {
    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (PlayerAuth.auth_players.contains(player)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DropItem(PlayerDropItemEvent event) {
        if (PlayerAuth.auth_players.contains(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void BlockBreaks(BlockBreakEvent event) {
        if (PlayerAuth.auth_players.contains(event.getPlayer())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void PlayerDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (PlayerAuth.auth_players.contains(player)) return;
            event.setCancelled(true);
        }
    }
}
