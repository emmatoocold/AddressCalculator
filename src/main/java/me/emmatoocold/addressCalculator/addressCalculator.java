package me.emmatoocold.addressCalculator;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class addressCalculator extends JavaPlugin {

    // declare your flag as a field accessible to other parts of your code (so you can use this to check it)
// note: if you want to use a different type of flag, make sure you change StateFlag here and below to that type
    public static StringFlag addressMapUrl;
    public static StringFlag addressOffsetX;
    public static StringFlag addressOffsetZ;
    public static DoubleFlag addressDensity;
    public static StringFlag addressDisplayName;
    Logger log = Bukkit.getLogger();

    //public static BooleanFlag isDistrict;

    public void onLoad() {
        // ... do your own plugin things, etc

        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        try {
            // create a flag with the name "addressMapUrl"
            StringFlag flag = new StringFlag("addressMapUrl");
            registry.register(flag);
            addressMapUrl = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressMapUrl");
            if (existing instanceof StringFlag) {
                addressMapUrl = (StringFlag) existing;
                log.info("flag 'addressMapUrl' already exists!");
            } else {
                log.info("flag 'addressMapUrl' failed to has a conflict with an unlike variable!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressOffsetX"
            StringFlag flag = new StringFlag("addressOffsetX");
            registry.register(flag);
            addressOffsetX = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressOffsetX");
            if (existing instanceof StringFlag) {
                addressOffsetX = (StringFlag) existing;
                log.info("flag 'addressOffsetX' already exists!");
            } else {
                log.info("flag 'addressOffsetX' failed to has a conflict with an unlike variable!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressOffsetZ"
            StringFlag flag = new StringFlag("addressOffsetZ");
            registry.register(flag);
            addressOffsetZ = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressOffsetZ");
            if (existing instanceof StringFlag) {
                addressOffsetZ = (StringFlag) existing;
                log.info("flag 'addressOffsetZ' already exists!");
            } else {
                log.info("flag 'addressOffsetZ' failed to has a conflict with an unlike variable!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressDensity"
            DoubleFlag flag = new DoubleFlag("addressDensity");
            registry.register(flag);
            addressDensity = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressDensity");
            if (existing instanceof DoubleFlag) {
                addressDensity = (DoubleFlag) existing;
                log.info("flag 'addressDensity' already exists!");
            } else {
                log.info("flag 'addressDensity' failed to has a conflict with an unlike variable!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressDistrictName"
            StringFlag flag = new StringFlag("addressDisplayName");
            registry.register(flag);
            addressDisplayName = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressDisplayName");
            if (existing instanceof StringFlag) {
                addressDisplayName = (StringFlag) existing;
                log.info("flag 'addressDisplayName' already exists!");
            } else {
                log.info("flag 'addressDisplayName' failed to has a conflict with an unlike variable!");
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        Objects.requireNonNull(getCommand("getaddress")).setExecutor(new addressCalculatorCommandService());
        Objects.requireNonNull(getCommand("getaddressmap")).setExecutor(new addressCalculatorCommandService());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}