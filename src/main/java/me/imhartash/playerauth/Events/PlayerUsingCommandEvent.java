package me.imhartash.playerauth.Events;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.ColoredChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;

public class PlayerUsingCommandEvent implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (PlayerAuth.auth_players.contains(player)) return;

        String message = event.getMessage();

        if (!(message.startsWith("/login") || message.startsWith("/l") ||
        message.startsWith("/reg") || message.startsWith("/register"))) {
            event.setCancelled(true);
            player.sendMessage(
                    ColoredChat.convert(PlayerAuth.messagesConfig.getString("using_command_not_auth")));
        }
    }
}
