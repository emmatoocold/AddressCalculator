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

        Player player = (Player) sender;
        org.bukkit.@NotNull Location loc = player.getLocation();
        final List<String> validArguements = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], List.of("North","East","South","West"), validArguements);
            return validArguements;
        }

        if (args.length == 2)
            return List.of(String.valueOf(Math.round(loc.getX())));

        if (args.length == 3)
            return List.of(String.valueOf(Math.round(loc.getZ())));

        return new ArrayList<>();
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }

        org.bukkit.@NotNull Location loc = player.getLocation();

        double x;
        double y;
        double z;

        if (args.length == 3) {
            //code for 3 hard inputs
            x = Double.parseDouble(args[1]);
            y = loc.getY();
            z = Double.parseDouble(args[2]);
        } else if (args.length == 1) {
            //code for no hard inputs
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();
        } else {
            player.sendMessage(Component.text("1 or 3 arguments needed to run this command.", NamedTextColor.RED));
            return false;
        }

        World world = player.getWorld();
        var adaptedWorld = BukkitAdapter.adapt(world);
        Location worldEditLoc = new Location(adaptedWorld, x, y, z );
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(worldEditLoc);
        String direction = args[0].toLowerCase();
        boolean parameterSwitch = false;

        for (ProtectedRegion region : set) {
            if(addressCalculator(region, x, z, direction, player)) {parameterSwitch = true;}
        }
        if (!parameterSwitch) {
            player.sendMessage(Component.text("No region with address parameters found.").color(NamedTextColor.RED));
        }

        for (ProtectedRegion region : set) {
            addressMapFinder(region, player);
        }
        return true;
    }
}