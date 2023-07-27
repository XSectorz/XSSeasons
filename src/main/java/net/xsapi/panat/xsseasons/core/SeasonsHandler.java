package net.xsapi.panat.xsseasons.core;

import net.xsapi.panat.xsseasons.configurations.config;
import net.xsapi.panat.xsseasons.configurations.data;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class SeasonsHandler {

    private static ArrayList<String> seasons_sort = new ArrayList<>(config.customConfig.getStringList("Seasons_sort"));
    private static ArrayList<SeasonsInterface> seasonsData = new ArrayList<>();
    private static ArrayList<String> worldSync = new ArrayList<>(config.customConfig.getStringList("World_sync"));

    private static SeasonsInterface dataSeasonInterface;

    private static int hour;
    private static int minutes;
    private static int day;
    private static int year;
    private static int indexYearCounter;

    public static ArrayList<String> getWorldSync() {
        return SeasonsHandler.worldSync;
    }

    public static ArrayList<SeasonsInterface> getSeasonsData() {
        return SeasonsHandler.seasonsData;
    }

    public static void setDataSeasonInterface(SeasonsInterface dataSeasonInterface) {
        SeasonsHandler.dataSeasonInterface = dataSeasonInterface;
    }

    public static SeasonsInterface getDataSeasonInterface() {
        return SeasonsHandler.dataSeasonInterface;
    }

    public static void setIndexYearCounter(int indexYearCounter) {
        SeasonsHandler.indexYearCounter = indexYearCounter;
    }

    public static int getIndexYearCounter() {
        return SeasonsHandler.indexYearCounter;
    }

    public static int getMinutes() {
        return SeasonsHandler.minutes;
    }

    public static void setMinutes(int minutes) {
        SeasonsHandler.minutes = minutes;
    }

    public static void setDay(int day) {
        SeasonsHandler.day = day;
    }

    public static int getDay() {
        return SeasonsHandler.day;
    }

    public static void setHour(int hour) {
        SeasonsHandler.hour = hour;
    }

    public static int getHour() {
        return SeasonsHandler.hour;
    }

    public static void setYear(int year) {
        SeasonsHandler.year = year;
    }

    public static int getYear() {
        return SeasonsHandler.year;
    }


    public void syncWorldTime() {
        int hours = getHour();
        int minutes = getMinutes();

        for(String worldName : getWorldSync()) {
            if(Bukkit.getWorld(worldName) == null) {
                continue;
            }

            World world = Bukkit.getWorld(worldName);

            if(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)) {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
            }

            long time = 0L;
            time = time + (hours*1000L); //hours
            time = time + ((minutes/10)*(long) 166.67); //minutes
            time = time + 18000L;

            if(time >= 24000) {
                time = time - 24000;
            }


            //Bukkit.getLogger().info("SET TIME: " + worldName + " AT : " + time);
            //Bukkit.getLogger().info("SYSTEM TIME: " + hours + " : " + minutes);

            world.setTime(time);

            if(time == 1000) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(XSSeasons.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        world.setTime(1001);
                    }
                }, 20L);
            }

        }
    }

    public void loadDataSeason() {

        if(data.customConfig.get("data_seasons.season") == null) {

            data.customConfig.set("data_seasons.day",1);
            data.customConfig.set("data_seasons.year",1);
            data.customConfig.set("data_seasons.index_year_counter",0);
            data.customConfig.set("data_seasons.time.hours",0);
            data.customConfig.set("data_seasons.time.minutes",0);

            SeasonsHandler.dataSeasonInterface = seasonsData.get(0);
            SeasonsHandler.day = 1;
            SeasonsHandler.hour = 0;
            SeasonsHandler.minutes = 0;
            SeasonsHandler.year = 1;
            SeasonsHandler.indexYearCounter = 0;

            data.save();
            data.reload();
            Bukkit.getLogger().info("§x§f§f§a§c§2§floaded default data complete!");
        } else {
            SeasonsHandler.day = data.customConfig.getInt("data_seasons.day");
            SeasonsHandler.year = data.customConfig.getInt("data_seasons.year");
            SeasonsHandler.hour = data.customConfig.getInt("data_seasons.time.hours");
            SeasonsHandler.minutes = data.customConfig.getInt("data_seasons.time.minutes");
            SeasonsHandler.dataSeasonInterface = getSeasonByRealName(data.customConfig.getString("data_seasons.season"));
            SeasonsHandler.indexYearCounter = data.customConfig.getInt("data_seasons.index_year_counter");

            Bukkit.getLogger().info("§x§f§f§a§c§2§floaded data from file complete!");
        }

    }

    public static void saveDataSeason() {
        data.customConfig.set("data_seasons.season",getDataSeasonInterface().getSeasonRealName());
        data.customConfig.set("data_seasons.day",getDay());
        data.customConfig.set("data_seasons.year",getYear());
        data.customConfig.set("data_seasons.index_year_counter",getIndexYearCounter());
        data.customConfig.set("data_seasons.time.hours",getHour());
        data.customConfig.set("data_seasons.time.minutes",getMinutes());
        data.save();
    }

    public static void debug() {
        Bukkit.broadcastMessage("Year " + getYear() + " Season " + getDataSeasonInterface().getSeason_str() + " DAY " + getDay() + " TIME: " + getHour() + ":" + getMinutes());
    }

    public void seasonSorted() {
        for (String season_str : seasons_sort) {
            seasonsData.add(getSeasonByRealName(season_str));
        }
    }

    public SeasonsInterface getSeasonByRealName(String realName) {

        for (SeasonsInterface seasons : XSSeasons.getSeasonsList()) {
            if(seasons.getSeasonRealName().equalsIgnoreCase(realName)) {
                return seasons;
            }
        }

        return null;
    }

}
