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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.emmatoocold.addressCalculator.addressCalculatorCore.*;

public class addressCalculatorCommandService implements CommandExecutor, TabCompleter {

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("getaddress")) {
            Player player = (Player) sender;
            org.bukkit.@NotNull Location loc = player.getLocation();
            final List<String> validArguements = new ArrayList<>();

            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], List.of("North", "East", "South", "West"), validArguements);
                return validArguements;
            }

            if (args.length == 2)
                return List.of(String.valueOf(Math.round(loc.getX())));

            if (args.length == 3)
                return List.of(String.valueOf(Math.round(loc.getZ())));

        }

        if (command.getName().equalsIgnoreCase("getaddressmap")) {
            Player player = (Player) sender;
            org.bukkit.@NotNull Location loc = player.getLocation();

            if (args.length == 1)
                return List.of(String.valueOf(Math.round(loc.getX())));

            if (args.length == 2)
                return List.of(String.valueOf(Math.round(loc.getZ())));

        }
            return new ArrayList<>();

    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
        {
            if (command.getName().equalsIgnoreCase("getaddress")) {

                double playerX;
                double playerY;
                double playerZ;

                Player player = (Player) sender;
                org.bukkit.@NotNull Location loc = player.getLocation();

                if (args.length == 3) {
                    //code for 3 hard inputs
                    playerX = Double.parseDouble(args[1]);
                    playerY = loc.getY();
                    playerZ = Double.parseDouble(args[2]);
                } else if (args.length == 1) {
                    //code for no hard inputs
                    playerX = loc.getX();
                    playerY = loc.getY();
                    playerZ = loc.getZ();
                } else {
                    player.sendMessage(Component.text("usage: /getaddress <Side of road> <X coordinate> <z coordinate>")
                            .color(NamedTextColor.RED));
                    return false;
                }

                World world = player.getWorld();
                var adaptedWorld = BukkitAdapter.adapt(world);
                Location worldEditLoc = new Location(adaptedWorld, playerX, playerY, playerZ);
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();

                ApplicableRegionSet set = query.getApplicableRegions(worldEditLoc);
                String direction = args[0].toLowerCase();

                for (ProtectedRegion region : set) {
                    if (addressCalculator(region, playerX, playerZ, direction, player)) {
                        addressMapFinder(region, player);
                    }
                }

        /*
        boolean parameterSwitch = false;

        if (!parameterSwitch) {
            player.sendMessage(Component.text("No region with address parameters found.")
                    .color(NamedTextColor.RED));
        }
         */
            }

            if (command.getName().equalsIgnoreCase("getaddressmap")) {

                Player player = (Player) sender;
                org.bukkit.@NotNull Location loc = player.getLocation();

                double playerX;
                double playerY;
                double playerZ;


                if (args.length == 2) {
                    //code for 3 hard inputs
                    playerX = Double.parseDouble(args[0]);
                    playerY = loc.getY();
                    playerZ = Double.parseDouble(args[1]);
                } else if (args.length == 0) {
                    //code for no hard inputs
                    playerX = loc.getX();
                    playerY = loc.getY();
                    playerZ = loc.getZ();
                } else {
                    player.sendMessage(Component.text("usage: /getaddressmap <X coordinate> <z coordinate>")
                            .color(NamedTextColor.RED));
                    return false;
                }


                World world = player.getWorld();
                var adaptedWorld = BukkitAdapter.adapt(world);
                Location worldEditLoc = new Location(adaptedWorld, playerX, playerY, playerZ);
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();

                ApplicableRegionSet set = query.getApplicableRegions(worldEditLoc);

                for (ProtectedRegion region : set) {
                    addressMapFinder(region, player);
                }


            }

            return true;
    }
}