package me.imhartash.playerauth.Events;

import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinPlayerEvent implements Listener {

    private JavaPlugin plugin;

    public JoinPlayerEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (DataBase.get_data("id", Encryptor.encryptString(player.getName())) == null) {
            register_user(player);
        } else {
            login_user(player);
        }
    }


    private void register_user(Player player) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {

        }, 20, 100);
    }

    private void login_user(Player player) {
        player.sendMessage("Авторизуйся падль...");
    }
}