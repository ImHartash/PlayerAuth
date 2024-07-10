package me.imhartash.playerauth;

import me.imhartash.playerauth.Commands.LoginCommand;
import me.imhartash.playerauth.Commands.RegisterCommand;
import me.imhartash.playerauth.Events.JoinPlayerEvent;
import me.imhartash.playerauth.Events.MovePlayerEvent;
import me.imhartash.playerauth.Utils.DataBase;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public final class PlayerAuth extends JavaPlugin {

    public static List<Player> auth_players = new ArrayList<>();
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        DataBase.open_connection();
        this.saveDefaultConfig();
        this.getLogger().info("Plugin is Enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinPlayerEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new MovePlayerEvent(), this);

        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DataBase.close_connection();
        this.getLogger().info("Plugin is Disabled!");
    }
}
