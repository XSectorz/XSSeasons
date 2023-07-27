package net.xsapi.panat.xsseasons.tasks;

import net.xsapi.panat.xsseasons.core.SeasonsHandler;
import net.xsapi.panat.xsseasons.core.XSSeasons;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class updateWorldTime extends BukkitRunnable {
    public static SeasonsHandler seasonHandler = new SeasonsHandler();

    @Override
    public void run() {
        seasonHandler.syncWorldTime();
        /*
        for (String world_name : SeasonsHandler.getWorldSync()) {

            if(Bukkit.getWorld(world_name) == null) {
                continue;
            }

            World world = Bukkit.getWorld(world_name);

            if(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)) {
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
            }

            long currentTime = world.getTime();

            double tickMult = XSSeasons.getMulti();
            long tick = XSSeasons.getTickCooldowns();

            if (tickMult > 0.0D && tickMult < 1.0D) { //slow down
                if (tick > (1.0D / tickMult)) {
                    XSSeasons.setTickCooldowns(0);
                    world.setTime(currentTime + 1);
                    //Bukkit.getLogger().info("Update time SLOW: " + world.getTime() + " COOLDOWN: " + (1.0D / tickMult));
                }
            }
            else if (tickMult >= 1.0D) { //speed up or keep normal
                world.setTime(currentTime + (long) tickMult);
                //Bukkit.getLogger().info("Update time SPEED : " + world.getTime());
            }

            //FIX TIMER
            if(SeasonsHandler.getHour() == 0 && SeasonsHandler.getMinutes() == 0) {
                if(world.getTime() <= 18000) {
                    world.setTime(18000);
                }
            } else if(SeasonsHandler.getHour() == 6 && SeasonsHandler.getMinutes() == 0) {
                if(world.getTime() >= 23000) {
                    world.setTime(0);
                }
            } else if(SeasonsHandler.getHour() == 12 && SeasonsHandler.getMinutes() == 0) {
                if(world.getTime() <= 6000) {
                    world.setTime(6000);
                }
            } else if(SeasonsHandler.getHour() == 18 && SeasonsHandler.getMinutes() == 0) {
                if(world.getTime() <= 12000) {
                    world.setTime(12000);
                }
            }

            XSSeasons.setTickCooldowns(XSSeasons.getTickCooldowns()+1);
        }*/
    }

}
