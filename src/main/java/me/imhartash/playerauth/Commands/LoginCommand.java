package me.imhartash.playerauth.Commands;

import me.imhartash.playerauth.PlayerAuth;
import me.imhartash.playerauth.Utils.ColoredChat;
import me.imhartash.playerauth.Utils.DataBase;
import me.imhartash.playerauth.Utils.Encryptor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

public class LoginCommand implements CommandExecutor {

    public static HashMap<Player, Integer> players_attempts = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player send_player = (Player) commandSender;
        if (PlayerAuth.auth_players.contains(send_player)) return true;

        if (DataBase.get_data("id", Encryptor.encryptString(send_player.getName())) == null) return true;

        String user_password = args[0];
        String db_password = Encryptor.decryptText(
                DataBase.get_data("player_password", Encryptor.encryptString(send_player.getName()))
        );

        if (!(db_password.equals(user_password))) {
            int current_attempts = players_attempts.get(send_player) - 1;

            if (current_attempts <= 0) {
                send_player.kickPlayer(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("wrong_password_kick"))));
                return true;
            }

            players_attempts.put(send_player, current_attempts);

            String message = ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("attempts_more")).replace(
                    "%attempts%", String.valueOf(current_attempts)
            ));

            send_player.sendMessage(message);
            return true;
        }

        send_player.sendMessage(ColoredChat.convert(Objects.requireNonNull(PlayerAuth.messagesConfig.getString("success_logged_in"))));
        PlayerAuth.auth_players.add(send_player);

        return true;
    }

}
