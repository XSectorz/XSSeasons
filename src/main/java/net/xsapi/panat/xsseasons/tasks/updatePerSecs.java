package net.xsapi.panat.xsseasons.tasks;

import net.xsapi.panat.xsseasons.configurations.config;
import net.xsapi.panat.xsseasons.core.SeasonsHandler;
import net.xsapi.panat.xsseasons.core.XSSeasons;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class updatePerSecs extends BukkitRunnable {

    @Override
    public void run() {
        if(!XSSeasons.getPlugin().isParent()) {
            if(config.customConfig.getBoolean("redis.enable")) {
                XSSeasons.redisGetKey();
            }
        } else {
            XSSeasons.setTempAutoSave(XSSeasons.getTempAutoSave()-1);
          //  Bukkit.broadcastMessage("TEMP: " + XSSeasons.getTempAutoSave());
            if(XSSeasons.getTempAutoSave() <= 0) {
                XSSeasons.setTempAutoSave(XSSeasons.getAutoSave());
            //    Bukkit.broadcastMessage("AUTO_SAVE: " + XSSeasons.getAutoSave());
                SeasonsHandler.saveDataSeason();
            }
        }
    }
}
