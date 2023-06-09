package net.xsapi.panat.xsseasons.core;

import net.xsapi.panat.xsseasons.configurations.loadConfig;
import net.xsapi.panat.xsseasons.placeholders.xsapi_seasons;
import net.xsapi.panat.xsseasons.tasks.updateInterval;
import net.xsapi.panat.xsseasons.tasks.updateWorldTime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public final class XSSeasons extends JavaPlugin {

    private static XSSeasons plugin;
    private static ArrayList<SeasonsInterface> seasonsList = new ArrayList<>();
    private static long tickCooldowns = 0;
    private static double multi = 0;

    public static XSSeasons getPlugin() {
        return XSSeasons.plugin;
    }
    public static ArrayList<SeasonsInterface> getSeasonsList() {
        return XSSeasons.seasonsList;
    }

    public static long getTickCooldowns() {
        return XSSeasons.tickCooldowns;
    }

    public static double getMulti() {
        return XSSeasons.multi;
    }

    public static void setMulti(double multi) {
        XSSeasons.multi = multi;
    }

    public static void setTickCooldowns(long tickCooldowns) {
        XSSeasons.tickCooldowns = tickCooldowns;
    }

    @Override
    public void onEnable() {

        Bukkit.getLogger().info("§x§f§f§a§c§2§f******************************");
        Bukkit.getLogger().info("§x§f§f§a§c§2§f   XSAPI Seasons v1.0     ");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§x§f§f§a§c§2§f  Minecraft seasons for farming!");
        Bukkit.getLogger().info("§r");
        Bukkit.getLogger().info("§x§f§f§a§c§2§f******************************");

        XSSeasons.plugin = this;

        new loadConfig();

        seasonsList.add(new SeasonsSummer());
        seasonsList.add(new SeasonsFall());
        seasonsList.add(new SeasonsSpring());
        seasonsList.add(new SeasonsWinter());

        SeasonsHandler seasonHandler = new SeasonsHandler();
        seasonHandler.seasonSorted();

        seasonHandler.loadDataSeason();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getLogger().info("§x§f§f§c§e§2§2[XSAPI SEASONS] PlaceholderAPI: §x§5§d§f§f§6§3Hook");
            new xsapi_seasons(XSSeasons.getPlugin()).register();
        } else {
            Bukkit.getLogger().info("§x§f§f§c§e§2§2[XSAPI SEASONS] PlaceholderAPI: §x§f§f§5§8§5§8Not Hook");
        }

        seasonHandler.syncWorldTime();

        setMulti((10.0D / (720 / 60.0D)));

        BukkitTask task_1 = (new updateInterval()).runTaskTimer((Plugin) plugin, 200L, 200L);
        //BukkitTask task_2 = (new updateWorldTime()).runTaskTimer((Plugin) plugin,1L,1L);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("§c[XSAPI SEASONS] Plugin Disabled !");

        SeasonsHandler.saveDataSeason();
    }
}
