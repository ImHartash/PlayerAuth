package me.imhartash.playerauth.Commands;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player) || args.length != 2) return true;

        Player player = (Player) commandSender;

        if (PlayerAuth.auth_players.contains(player)) {
            return true;
        }

        if (DataBase.get_data("id", player.getName()) != null) {
            player.sendMessage("Похоже, что вы уже зарегестрированны....");
            return true;
        }

        String player_password = args[0];
        String player_password_verif = args[1];

        if (player_password.length() < 6 || player_password.length() > 16) {
            player.sendMessage("Длинна пароля должна быть в районе от 6 до 16 символов...");
            return true;
        }

        if (!player_password.equals(player_password_verif)) {
            player.sendMessage("Пароли должны совпадать!!");
            return true;
        }
        DataBase.register_player(player, Encryptor.encryptString(player_password));

        player.sendMessage("Вы успешно зарегестрировались!");

        return true;
    }
}
