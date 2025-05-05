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

    public static boolean addressCalculator(ProtectedRegion region, double x, double z, String direction, Player player) {
        if (region.getFlag(addressOffsetZ) != null && region.getFlag(addressOffsetX) != null && region.getFlag(addressDensity) != null) {
            switch (direction) {
                case "north":
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the North side of an East / West road in " +
                            BOLD + formatString(region.getId()), NamedTextColor.GRAY));
                    return true;

                case "east":
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of a North / South road in " +
                            BOLD + formatString(region.getId()), NamedTextColor.GRAY));
                    return true;

                case "south":
                    player.sendMessage(Component.text("your address is " +
                                    (((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the South side of an East / West road in " +
                            BOLD + formatString(region.getId()), NamedTextColor.GRAY));
                    return true;

                case "west":
                    player.sendMessage(Component.text("your address is " +
                                    (((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of an North / South road in " +
                            BOLD + formatString(region.getId()), NamedTextColor.GRAY));
                    return true;

                default:
                    player.sendMessage(Component.text("No or improper direction provided.").color(NamedTextColor.RED));
                    return true;
            }
        }
        return false;
    };

    public static void addressMapFinder(ProtectedRegion region, Player player) {
        if(region.getFlag(addressMapUrl) != null) {
            player.sendMessage(Component
                    .text("Address map for " + BOLD + (region.getId()))
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .decorate(TextDecoration.UNDERLINED)
                    .hoverEvent(HoverEvent.showText(Component.text(Objects.requireNonNull(formatString(region.getFlag(addressMapUrl))))
                            .color(NamedTextColor.DARK_GRAY)))
                    .clickEvent(ClickEvent.openUrl(Objects.requireNonNull(region.getFlag(addressMapUrl)))));
        }
    }
}
