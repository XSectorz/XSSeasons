package net.xsapi.panat.xsseasons.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.xsapi.panat.xsseasons.core.SeasonsHandler;
import net.xsapi.panat.xsseasons.core.XSSeasons;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;

public class xsapi_seasons extends PlaceholderExpansion {

    private final XSSeasons plugin;

    public xsapi_seasons(XSSeasons plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "PanatXsectorZ";
    }

    @Override
    public String getIdentifier() {
        return "XSseasons";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        DecimalFormat df = new DecimalFormat("00");

        if(params.equalsIgnoreCase("current_seasons")) {
            return SeasonsHandler.getDataSeasonInterface().getSeason_str();
        }
        if(params.equalsIgnoreCase("current_seasons_realName")) {
            return SeasonsHandler.getDataSeasonInterface().getSeasonRealName();
        }
        if(params.equalsIgnoreCase("year")) {
            return String.valueOf(SeasonsHandler.getYear());
        }
        if(params.equalsIgnoreCase("day")) {
            return String.valueOf(SeasonsHandler.getDay());
        }
        if(params.equalsIgnoreCase("hours")) {
            return String.valueOf(df.format(SeasonsHandler.getHour()));
        }
        if(params.equalsIgnoreCase("minutes")) {
            return String.valueOf(df.format(SeasonsHandler.getMinutes()));
        }

        return null;
    }

}
