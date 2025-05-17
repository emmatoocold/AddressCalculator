package me.emmatoocold.addressCalculator;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class addressCalculator extends JavaPlugin {

    // declare your flag as a field accessible to other parts of your code (so you can use this to check it)
    // note: if you want to use a different type of flag, make sure you change StateFlag here and below to that type
    public static final StringFlag ADDRESS_MAP_URL = new StringFlag("address-map-url");
    public static final StringFlag ADDRESS_OFFSET_X = new StringFlag("address-offset-x");
    public static final StringFlag ADDRESS_OFFSET_Z = new StringFlag("address-offset-y");
    public static final DoubleFlag ADDRESS_DENSITY = new DoubleFlag("address-density");
    public static final StringFlag ADDRESS_DISPLAY_NAME = new StringFlag("address-display-name");

    //public static BooleanFlag isDistrict;

    public void onLoad() {
        tryRegisterFlag(ADDRESS_MAP_URL);
        tryRegisterFlag(ADDRESS_OFFSET_X);
        tryRegisterFlag(ADDRESS_OFFSET_Z);
        tryRegisterFlag(ADDRESS_DENSITY);
        tryRegisterFlag(ADDRESS_DISPLAY_NAME);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        var worldGuardPlugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuardPlugin == null) {
            getLogger().severe("WorldGuard not installed!");
            return;
        }

        Objects.requireNonNull(getCommand("getaddress")).setExecutor(new addressCalculatorCommandService());
        Objects.requireNonNull(getCommand("getaddressmap")).setExecutor(new addressCalculatorCommandService());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private static void tryRegisterFlag(Flag<?> flag) {
        var registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            registry.register(flag);
        } catch (IllegalArgumentException e) {
            // Flag already registered
        }
    }

}