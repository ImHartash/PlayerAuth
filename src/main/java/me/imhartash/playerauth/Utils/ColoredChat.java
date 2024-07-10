package me.imhartash.playerauth.Utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColoredChat {

    private final Pattern HEX_PATTERN = Pattern.compile("&#([a-f0-9]{6})");
    private final char COLOR_CHAR = '&';

    private String hex_convert(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {

            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x" +
                    COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1) +
                    COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3) +
                    COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5));

        }

        return matcher.appendTail(buffer).toString();
    }

    public String convert(String message) {
        String correct_message = hex_convert(message);
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, correct_message);
    }

}
