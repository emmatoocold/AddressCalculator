package me.emmatoocold.addressCalculator;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.emmatoocold.addressCalculator.addressCalculator.*;
import static org.bukkit.ChatColor.BOLD;

public class addressCalculatorCommandService implements CommandExecutor, TabCompleter {

    public static String capitalizeEveryLetterAfterSpace(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Arrays.stream(text.split("//s+"))
                .map(word -> word.substring(0,1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining());
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        org.bukkit.@NotNull Location loc = player.getLocation();

        if (args.length == 1)
            return Arrays.asList("North","East","South","West");

        if (args.length == 2)
            return List.of(String.valueOf(Math.round(loc.getX())));

        if (args.length == 3)
            return List.of(String.valueOf(Math.round(loc.getZ())));

        return new ArrayList<>();
    }
    @Override
    @Deprecated
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }

        double x = 0;
        double y = 0;
        double z = 0;

        if (args.length == 3) {
            //code for 3 hard inputs
            x = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
        } else if (args.length == 1) {
            //code for no hard inputs
            org.bukkit.@NotNull Location loc = player.getLocation();
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();
        } else if (args.length == 2) {
            player.sendMessage(Component.text("1 or 3 arguments needed to run this command!", NamedTextColor.RED));
            return true;
        } else if (args.length < 1) {
            player.sendMessage(Component.text("Not enough arguments to run this command!", NamedTextColor.RED));
            return true;
        } else if (args.length > 3) {
            player.sendMessage(Component.text("Too many arguments provided!", NamedTextColor.RED));
            return true; }

        World world = player.getWorld();
        var adaptedWorld = BukkitAdapter.adapt(world);
        Location loc = new Location(adaptedWorld, x, y, z );
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(loc);

        for (ProtectedRegion region : set) {
            if (region.getFlag(addressOffsetZ) != null && region.getFlag(addressOffsetX) != null && region.getFlag(addressDensity) != null) {

                if (args[0].equalsIgnoreCase("west")) {
                    player.sendMessage(Component.text("your address is " +
                                    (((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of an North / South road in " +
                            BOLD + capitalizeEveryLetterAfterSpace(region.getId()), NamedTextColor.GRAY));
                }
                else if (args[0].equalsIgnoreCase("east")) {
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the West side of a North / South road in " +
                            BOLD + capitalizeEveryLetterAfterSpace(region.getId()), NamedTextColor.GRAY));
                }
                else if (args[0].equalsIgnoreCase("north")) {
                    player.sendMessage(Component.text("your address is " +
                                    ((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the North side of an East / West road in " +
                            BOLD + capitalizeEveryLetterAfterSpace(region.getId()), NamedTextColor.GRAY));
                }
                else if (args[0].equalsIgnoreCase("south")) {
                    player.sendMessage(Component.text("your address is " +
                                    (((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * region.getFlag(addressDensity)) / 2)) * 2) - 1),
                            NamedTextColor.GRAY, TextDecoration.BOLD));
                    player.sendMessage(Component.text("On the South side of an East / West road in " +
                            BOLD + capitalizeEveryLetterAfterSpace(region.getId()), NamedTextColor.GRAY));
                }
            }
            if (region.getFlag(addressMapUrl) != null) {
                player.sendMessage(Component.text("The address map for " + capitalizeEveryLetterAfterSpace(region.getId()) + " is: " + region.getFlag(addressMapUrl), NamedTextColor.GRAY));
            }
        }
        return true;
    }
}