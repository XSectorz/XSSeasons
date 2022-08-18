package net.xsapi.panat.xsseasons.events;

import net.xsapi.panat.xsseasons.core.SeasonsInterface;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class XSAPIYearChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private int year;

    public XSAPIYearChangeEvent(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
