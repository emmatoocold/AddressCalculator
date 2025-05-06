package me.emmatoocold.addressCalculator;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.Objects;

import static me.emmatoocold.addressCalculator.addressCalculator.*;
import static org.bukkit.ChatColor.BOLD;

public class addressCalculatorCore {

    public static String formatString(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        text = text.replaceAll("_", " ");
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static boolean checkString(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean addressCalculator(ProtectedRegion region, double playerX, double playerZ, String direction, Player player) {

        String string;

        switch (direction) {
            case "north":
                string = region.getFlag(addressOffsetX);
                if (string == null || string.isEmpty()) {
                    return false;
                } else if (checkString(string)) {
                    player.sendMessage(Component.text("your address is " +
                                    (Math.round(((Math.abs(playerX - Integer.parseInt(Objects.requireNonNull(region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the North side of an East / West road in " +
                            BOLD + region.getFlag(addressDisplayName), NamedTextColor.GRAY));
                    return true;
                } else
                    player.sendMessage(Component.text(Objects.requireNonNull(region.getFlag(addressOffsetX)))
                            .append(Component.text(" in "))
                            .color(NamedTextColor.RED)
                            .append(Component.text(Objects.requireNonNull(region.getFlag(addressDisplayName)))
                                    .decorate(TextDecoration.ITALIC)
                                    .color(NamedTextColor.RED)));
                return false;
            case "east":
                string = region.getFlag(addressOffsetZ);
                if (string == null || string.isEmpty()) {
                    return false;
                } else if (checkString(string)) {
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(Integer.parseInt(Objects.requireNonNull(region.getFlag(addressOffsetZ))) - playerZ)) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of a North / South road in " +
                            BOLD + region.getFlag(addressDisplayName), NamedTextColor.GRAY));
                    return true;
                } else
                    player.sendMessage(Component.text(Objects.requireNonNull(region.getFlag(addressOffsetZ)))
                            .append(Component.text(" in "))
                            .color(NamedTextColor.RED)
                            .append(Component.text(Objects.requireNonNull(region.getFlag(addressDisplayName)))
                                    .decorate(TextDecoration.ITALIC)
                                    .color(NamedTextColor.RED)));
                return false;

            case "south":
                string = region.getFlag(addressOffsetX);
                if (string == null || string.isEmpty()) {
                    return false;
                } else if (checkString(string)) {
                    player.sendMessage(Component.text("your address is " +
                                    (Math.round(((Math.abs(playerX - Integer.parseInt(Objects.requireNonNull(region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the North side of an East / West road in " +
                            BOLD + region.getFlag(addressDisplayName), NamedTextColor.GRAY));
                    return true;
                } else
                    player.sendMessage(Component.text(Objects.requireNonNull(region.getFlag(addressOffsetX)))
                            .append(Component.text(" in "))
                            .color(NamedTextColor.RED)
                            .append(Component.text(Objects.requireNonNull(region.getFlag(addressDisplayName)))
                                    .decorate(TextDecoration.ITALIC)
                                    .color(NamedTextColor.RED)));
                return false;

            case "west":
                string = region.getFlag(addressOffsetZ);
                if (string == null || string.isEmpty()) {
                    return false;
                } else if (checkString(string)) {
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(Integer.parseInt(Objects.requireNonNull(region.getFlag(addressOffsetZ))) - playerZ)) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of a North / South road in " +
                            BOLD + region.getFlag(addressDisplayName), NamedTextColor.GRAY));
                    return true;
                } else
                    player.sendMessage(Component.text(Objects.requireNonNull(region.getFlag(addressOffsetZ)))
                            .append(Component.text(" in "))
                            .color(NamedTextColor.RED)
                            .append(Component.text(Objects.requireNonNull(region.getFlag(addressDisplayName)))
                                    .decorate(TextDecoration.ITALIC)
                                    .color(NamedTextColor.RED)));
                return false;

            default:
                player.sendMessage(Component.text("No or improper direction provided.").color(NamedTextColor.RED));
                return false;
        }
    }

    public static void addressMapFinder(ProtectedRegion region, Player player) {
        if(region.getFlag(addressMapUrl) != null) {
            player.sendMessage(Component
                    .text("Address map for " + BOLD + (region.getFlag(addressDisplayName)))
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .decorate(TextDecoration.UNDERLINED)
                    .hoverEvent(HoverEvent.showText(Component.text(Objects.requireNonNull(formatString(region.getFlag(addressMapUrl))))
                            .color(NamedTextColor.DARK_GRAY)))
                    .clickEvent(ClickEvent.openUrl(Objects.requireNonNull(region.getFlag(addressMapUrl)))));
        }
    }
}
