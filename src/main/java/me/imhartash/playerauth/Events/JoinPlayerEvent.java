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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoinPlayerEvent implements Listener {

    private final JavaPlugin plugin;

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
            if (!check_session(player)) {
                login_user(player);
            }
            else {
                PlayerAuth.auth_players.add(player);
                player.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("success_logged_in")));
            }
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
            if (PlayerAuth.auth_players.contains(player)) {
                if (PlayerAuth.plugin.getConfig().getBoolean("sessions.enabled")) {
                    player.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("session_saved")));
                    String playerAddress = Objects.requireNonNull(player.getAddress()).getHostName();
                    long currentTime = System.currentTimeMillis();

                    List<String> playerInfo = new ArrayList<>();
                    playerInfo.add(0, playerAddress);
                    playerInfo.add(1, String.valueOf(currentTime));

                    PlayerAuth.playerSessions.remove(player);
                    PlayerAuth.playerSessions.put(player.getName(), playerInfo);
                }

                task.cancel();
            }
            else
                player.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("login_message")));
        }, 0L, 100L);
    }

    private boolean check_session(Player player) {
        if (!PlayerAuth.playerSessions.containsKey(player.getName())) {
            return false;
        }

        String currentIP = Objects.requireNonNull(player.getAddress()).getHostName();
        String lastIP = PlayerAuth.playerSessions.get(player.getName()).get(0);

        long currentTime = System.currentTimeMillis();
        long lastTime = Long.parseLong(PlayerAuth.playerSessions.get(player.getName()).get(1));

        long sessionTime = 3600000L * PlayerAuth.plugin.getConfig().getInt("sessions.time");

        return currentIP.equals(lastIP) && currentTime - lastTime < sessionTime;
    }
}