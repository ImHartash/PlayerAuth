package me.imhartash.playerauth.Commands;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player send_player = (Player) commandSender;
        if (PlayerAuth.auth_players.contains(send_player)) return true;

        if (DataBase.get_data("id", send_player.getName()) == null) return true;

        String user_password = args[0];
        String db_password = Encryptor.decryptText(
                DataBase.get_data("player_password", send_player.getName())
        );

        if (!(db_password.equals(user_password))) {
            send_player.kickPlayer(ChatColor.RED + "Wrong Password...");
            return true;
        }

        send_player.sendMessage("Вы успешно авторизовались!!!");

        return true;
    }

}
