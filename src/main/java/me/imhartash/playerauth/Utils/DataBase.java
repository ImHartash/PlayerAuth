package me.imhartash.playerauth.Utils;

import org.bukkit.entity.Player;

import java.sql.*;

public class DataBase {

    private static final String DATABASE_URL = "jdbc:sqlite:./plugins/PlayerAuth/players.db";
    private static Connection conn = null;

    public static void open_connection() {
        try {

            conn = DriverManager.getConnection(DATABASE_URL);
            create_database();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close_connection() {
        try {

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void create_database() {

        try {

            String sql_query = "CREATE TABLE IF NOT EXISTS players_data(" +
                    "id INTEGER PRIMARY KEY," +
                    "player_name TEXT," +
                    "player_password TEXT," +
                    "player_uuid TEXT);";

            conn.createStatement().execute(sql_query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void set_data(String data_name, String data, String player_name) {

        if (conn == null) return;

        try {

            String sql_query = "UPDATE players_data" +
                    "SET %s = '%s' WHERE player_name = '%s';";
            sql_query = String.format(sql_query, data_name, data, player_name);

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql_query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String get_data(String data_table, String player_name) {

        if (conn == null) return null;

        try {

            String sql_query = "SELECT %s FROM players_data WHERE player_name = '%s';";
            sql_query = String.format(sql_query, data_table, player_name);
            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery(sql_query);

            if (result.next()) {

                statement.close();
                return result.getString(data_table);

            }
            else {

                statement.close();
                return null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void register_player(Player player, String encrypted_user_password) {

        if (conn == null) return;

        try {

            String player_name = Encryptor.encryptString(player.getName());
            String player_uuid = Encryptor.encryptString(player.getUniqueId().toString());

            String sql_query = "INSERT INTO players_data(player_name, player_password, player_uuid)" +
                    "VALUES('%s', '%s', '%s');";
            sql_query = String.format(sql_query, player_name, encrypted_user_password, player_uuid);

            Statement statement = conn.createStatement();
            statement.execute(sql_query);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}