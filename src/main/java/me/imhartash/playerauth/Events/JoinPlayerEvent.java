package me.imhartash.playerauth.Events;

import me.imhartash.playerauth.Commands.LoginCommand;
import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.ColoredChat;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinPlayerEvent implements Listener {

    private JavaPlugin plugin;

    public JoinPlayerEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int attempts = plugin.getConfig().getInt("attempts_to_login");

        LoginCommand.players_attempts.put(event.getPlayer(), attempts);

        if (DataBase.get_data("id", Encryptor.encryptString(player.getName())) == null) {
            register_user(player);
        } else {
            login_user(player);
        }
    }

    @EventHandler
    public void onPlayerLeaved(PlayerQuitEvent event) {
        LoginCommand.players_attempts.remove(event.getPlayer());
        PlayerAuth.auth_players.remove(event.getPlayer());
    }

    private void register_user(Player player) {
        Bukkit.getScheduler().runTaskTimer(plugin, (task) -> {
            if (PlayerAuth.auth_players.contains(player))
                task.cancel();
            else
                player.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("register_message")));
        }, 0L, 100L);
    }

    private void login_user(Player player) {
        Bukkit.getScheduler().runTaskTimer(plugin, (task) -> {
            if (PlayerAuth.auth_players.contains(player))
                task.cancel();
            else
                player.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("login_message")));
        }, 0L, 100L);
    }
}