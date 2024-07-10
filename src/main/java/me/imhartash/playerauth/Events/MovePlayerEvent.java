package me.imhartash.playerauth.Events;

import me.imhartash.playerauth.PlayerAuth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovePlayerEvent implements Listener {
    @EventHandler
    public static void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!PlayerAuth.auth_players.contains(player)) {
            event.setCancelled(true);
        }
    }
}
