package me.imhartash.playerauth.Commands.Console;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.ColoredChat;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("no_permission")));
            return true;
        }

        switch (strings[0]) {
            case "changepassword":
                String playerName = strings[1];
                String newPassword = strings[2];

                if (DataBase.get_data("id", Encryptor.encryptString(playerName)) == null) {
                    commandSender.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("player_not_registered")));
                    return true;
                }

                DataBase.set_data("player_password", Encryptor.encryptString(newPassword), Encryptor.encryptString(playerName));
                commandSender.sendMessage(ChatColor.GREEN + "Success!");
                return true;
            case "reload":
                PlayerAuth.plugin.reloadConfig();
                commandSender.sendMessage(ColoredChat.convert(PlayerAuth.messagesConfig.getString("success_reloaded")));
                return true;
            case "registered":
                commandSender.sendMessage(ColoredChat.convert("&dAuth players:"));
                for (Player player : PlayerAuth.auth_players) {
                    commandSender.sendMessage(player.getName());
                }
                return true;
        }

        return false;
    }
}
