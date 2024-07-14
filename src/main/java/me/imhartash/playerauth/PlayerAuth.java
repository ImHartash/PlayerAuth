package me.imhartash.playerauth;

import me.imhartash.playerauth.Commands.Console.PluginCommand;
import me.imhartash.playerauth.Commands.Console.PluginTabCompleter;
import me.imhartash.playerauth.Commands.LoginCommand;
import me.imhartash.playerauth.Commands.RegisterCommand;
import me.imhartash.playerauth.Events.InventoryEvents;
import me.imhartash.playerauth.Events.JoinPlayerEvent;
import me.imhartash.playerauth.Events.MovePlayerEvent;
import me.imhartash.playerauth.Events.PlayerUsingCommandEvent;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PlayerAuth extends JavaPlugin {

    public static List<Player> auth_players = new ArrayList<>();
    public static JavaPlugin plugin;
    public static FileConfiguration messagesConfig;

    public static HashMap<String, List<String>> playerSessions = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Encryptor.setup();

        File messagesFileConfig = new File(getDataFolder(), "messages.yml");
        messagesConfig = new YamlConfiguration();

        if (!messagesFileConfig.exists()) {
            messagesFileConfig.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        try {
            messagesConfig.load(messagesFileConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        DataBase.open_connection();
        this.saveDefaultConfig();
        this.getLogger().info("Plugin is Enabled!");

        this.getServer().getPluginManager().registerEvents(new JoinPlayerEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new MovePlayerEvent(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerUsingCommandEvent(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

        this.getCommand("login").setExecutor(new LoginCommand());
        this.getCommand("register").setExecutor(new RegisterCommand());

        this.getCommand("playerauth").setExecutor(new PluginCommand());
        this.getCommand("playerauth").setTabCompleter(new PluginTabCompleter());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DataBase.close_connection();
        this.getLogger().info("Plugin is Disabled!");
    }
}
