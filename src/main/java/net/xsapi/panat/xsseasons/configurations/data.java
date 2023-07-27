package net.xsapi.panat.xsseasons.configurations;

import net.xsapi.panat.xsseasons.core.XSSeasons;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class data {

    public static File customConfigFile;

    public static FileConfiguration customConfig;

    public FileConfiguration getConfig() {
        return customConfig;
    }

    public void loadConfigu() {
        customConfigFile = new File(XSSeasons.getPlugin().getDataFolder(), "data.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            XSSeasons.getPlugin().saveResource("data.yml", false);
        }
        customConfig = (FileConfiguration) new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        customConfigFile = new File(XSSeasons.getPlugin().getDataFolder(), "data.yml");
        try {
            customConfig.options().copyDefaults(true);
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        config.customConfigFile = new File(XSSeasons.getPlugin().getDataFolder(), "data.yml");
        if (!config.customConfigFile.exists()) {
            config.customConfigFile.getParentFile().mkdirs();
            XSSeasons.getPlugin().saveResource("data.yml", false);
        } else {
            config.customConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(config.customConfigFile);
            try {
                config.customConfig.save(config.customConfigFile);
                XSSeasons.getPlugin().reloadConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
