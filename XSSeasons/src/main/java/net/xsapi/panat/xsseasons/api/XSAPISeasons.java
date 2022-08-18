package net.xsapi.panat.xsseasons.api;

import net.xsapi.panat.xsseasons.core.SeasonsHandler;
import net.xsapi.panat.xsseasons.core.SeasonsInterface;
import net.xsapi.panat.xsseasons.core.XSSeasons;

public class XSAPISeasons {

    public XSSeasons getInstance() {
        return XSSeasons.getPlugin();
    }

    public int getYear() {
        return SeasonsHandler.getYear();
    }

    public int getDay() {
        return SeasonsHandler.getDay();
    }

    public int getHour() {
        return SeasonsHandler.getHour();
    }

    public int getMinutes() {
        return SeasonsHandler.getMinutes();
    }

    public SeasonsInterface getSeason() {
        return SeasonsHandler.getDataSeasonInterface();
    }

}
