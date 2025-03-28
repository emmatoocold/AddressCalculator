package me.emmatoocold.AddressCalculator;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class AddressCalculator extends JavaPlugin {

    // declare your flag as a field accessible to other parts of your code (so you can use this to check it)
// note: if you want to use a different type of flag, make sure you change StateFlag here and below to that type
    public static StringFlag addressMapUrl;
    public static DoubleFlag addressOffsetX;
    public static DoubleFlag addressOffsetZ;
    public static DoubleFlag addressDensity;

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
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressOffsetX"
            DoubleFlag flag = new DoubleFlag("addressOffsetX");
            registry.register(flag);
            addressOffsetX = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressOffsetX");
            if (existing instanceof DoubleFlag) {
                addressOffsetX = (DoubleFlag) existing;
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressOffsetZ"
            DoubleFlag flag = new DoubleFlag("addressOffsetZ");
            registry.register(flag);
            addressOffsetZ = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressOffsetZ");
            if (existing instanceof DoubleFlag) {
                addressOffsetZ = (DoubleFlag) existing;
            } else {
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
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        try {
            // create a flag with the name "addressDensity"
            DoubleFlag flag = new DoubleFlag("district_");
            registry.register(flag);
            addressDensity = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("addressDensity");
            if (existing instanceof DoubleFlag) {
                addressDensity = (DoubleFlag) existing;
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }

        /*
        try {
            // create a flag with the name "isDistrict"
            BooleanFlag flag = new BooleanFlag("isDistrict");
            registry.register(flag);
            isDistrict = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get("isDistrict");
            if (existing instanceof BooleanFlag) {
                isDistrict = (BooleanFlag) existing;
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
            }
        }
         */

        System.out.println("Registered 'addressDensity'");

    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("AddressCalculator Started");
        getCommand("getaddress").setExecutor(new AddressCalculatorCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
