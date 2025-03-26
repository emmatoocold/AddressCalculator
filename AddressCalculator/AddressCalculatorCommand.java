package me.emmatoocold.AddressCalculator;

import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.emmatoocold.AddressCalculator.AddressCalculator.*;

public class AddressCalculatorCommand implements CommandExecutor, TabCompleter {

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

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }

        String side;
        Player player = (Player) sender;
        double x = 0;
        double z = 0;

        if (args[0].equalsIgnoreCase("north") || args[0].equalsIgnoreCase("n")) {
            side = "North";
        } else if (args[0].equalsIgnoreCase("east") || args[0].equalsIgnoreCase("e")) {
            side = "East";
        } else if (args[0].equalsIgnoreCase("south") || args[0].equalsIgnoreCase("s")) {
            side = "South";
        } else if (args[0].equalsIgnoreCase("west") || args[0].equalsIgnoreCase("w")) {
            side = "West";
        } else {
            return false;
        }

        if (args.length == 3) {
            //code for 3 hard inputs
            x = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
        } else if (args.length == 1) {
            //code for no hard inputs
            org.bukkit.@NotNull Location loc = player.getLocation();
            x = loc.getX();
            z = loc.getZ();
        } else if (args.length == 2) {
            player.sendMessage(Component.text("1 or 3 arguments needed to run this command!", NamedTextColor.RED));
            return true; }

        Location loc = new com.sk89q.worldedit.util.Location((Extent) player.getWorld(), x, 64, z );
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(loc);

        for (ProtectedRegion region : set) {

            switch (side) {

                case "East":
                    player.sendMessage(Component.text("your build was found to be on the " + side + " side of a North / South road in " + region, NamedTextColor.GRAY));
                    player.sendMessage(Component.text("your address is " + (((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * 0.62137) / 2)) * 2) - 1), NamedTextColor.GRAY, TextDecoration.BOLD));

                case "West":
                    player.sendMessage(Component.text("your build was found to be on the " + side + " side of a North / South road in " + region, NamedTextColor.GRAY));
                    player.sendMessage(Component.text("your address is " + ((Math.round(((Math.abs(x - region.getFlag(addressOffsetX))) * 0.62137) / 2)) * 2), NamedTextColor.GRAY, TextDecoration.BOLD));

                case "North":
                    player.sendMessage(Component.text("your build was found to be on the " + side + " side of an East / West road in " + region + " Greenfield", NamedTextColor.GRAY));
                    player.sendMessage(Component.text("your address is " + ((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * 0.62137) / 2)) * 2), NamedTextColor.GRAY, TextDecoration.BOLD));

                case "South":
                    player.sendMessage(Component.text("your build was found to be on the " + side + " side of an East / West road in " + region + " Greenfield", NamedTextColor.GRAY));
                    player.sendMessage(Component.text("your address is " + (((Math.round(((Math.abs(region.getFlag(addressOffsetZ) - z)) * 0.62137) / 2)) * 2) - 1), NamedTextColor.GRAY, TextDecoration.BOLD));
            }
        }

        for (ProtectedRegion region : set) {

            if (region.getFlag(addressMapUrl).isEmpty()) {
                player.sendMessage(Component.text("No address map found for region " + region.getId()));
            } else {
                player.sendMessage(Component.text("The address map for ") + region.getId() + " is " + region.getFlag(addressMapUrl));
            }

        }

        return true;
    }
}
