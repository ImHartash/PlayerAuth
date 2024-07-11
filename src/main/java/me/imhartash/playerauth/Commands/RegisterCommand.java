package me.imhartash.playerauth.Commands;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.ColoredChat;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player) || args.length != 2) return true;

        Player player = (Player) commandSender;

        if (PlayerAuth.auth_players.contains(player)) {
            return true;
        }

        if (DataBase.get_data("id", Encryptor.encryptString(player.getName())) != null) {
            player.sendMessage(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("player_registered"))));
            return true;
        }

        String player_password = args[0];
        String player_password_verif = args[1];

        if (player_password.length() < 6 || player_password.length() > 16) {
            player.sendMessage(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("wrong_register_length"))));
            return true;
        }

        if (!player_password.equals(player_password_verif)) {
            player.sendMessage(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("wrong_passwords"))));
            return true;
        }
        DataBase.register_player(player, Encryptor.encryptString(player_password));

        player.sendMessage(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("success_registered"))));
        PlayerAuth.auth_players.add(player);

        return true;
    }
}
