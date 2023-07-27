package net.xsapi.panat.xsseasons.tasks;

import net.xsapi.panat.xsseasons.configurations.config;
import net.xsapi.panat.xsseasons.core.SeasonsHandler;
import net.xsapi.panat.xsseasons.core.XSSeasons;
import net.xsapi.panat.xsseasons.events.XSAPISeasonsChangeEvent;
import net.xsapi.panat.xsseasons.events.XSAPIYearChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class updateInterval extends BukkitRunnable {

    public static SeasonsHandler seasonHandler = new SeasonsHandler();

    @Override
    public void run() {

        if(!XSSeasons.getPlugin().isCrossServer()) {
            updateStatus();
        } else {
            if(XSSeasons.getPlugin().isParent()) {
                updateStatus();
                XSSeasons.redisUpdateKey();
            }
        }

        seasonHandler.syncWorldTime();
        //SeasonsHandler.debug();

    }

    public void updateStatus() {
        SeasonsHandler.setMinutes(SeasonsHandler.getMinutes()+10);

        if(SeasonsHandler.getMinutes() >= 60) {
            SeasonsHandler.setMinutes(0);
            SeasonsHandler.setHour(SeasonsHandler.getHour()+1);
        }

        if(SeasonsHandler.getHour() >= 24) { //new Day
            SeasonsHandler.setHour(0);
            SeasonsHandler.setDay(SeasonsHandler.getDay()+1);
            //Bukkit.broadcastMessage("NEW DAY COMING");
        }

        if(SeasonsHandler.getDay() > SeasonsHandler.getDataSeasonInterface().getDays()) { //New Seasons
            SeasonsHandler.setIndexYearCounter(SeasonsHandler.getIndexYearCounter()+1);
            int index = SeasonsHandler.getIndexYearCounter();

            if(index==4) {
                index = 0;
            }
            SeasonsHandler.setDataSeasonInterface(SeasonsHandler.getSeasonsData().get(index));
            SeasonsHandler.setDay(1);
            XSAPISeasonsChangeEvent event = new XSAPISeasonsChangeEvent(SeasonsHandler.getDataSeasonInterface());
            Bukkit.getServer().getPluginManager().callEvent(event);
            //Bukkit.broadcastMessage("NEW SEASONS COMING!");
        }


        if(SeasonsHandler.getIndexYearCounter() >= 4) { //new Year
            SeasonsHandler.setIndexYearCounter(0);
            SeasonsHandler.setYear(SeasonsHandler.getYear() + 1);
            XSAPIYearChangeEvent event = new XSAPIYearChangeEvent(SeasonsHandler.getYear());
            Bukkit.getServer().getPluginManager().callEvent(event);
            //Bukkit.broadcastMessage("NEW YEAR COMING");
        }
    }

}
