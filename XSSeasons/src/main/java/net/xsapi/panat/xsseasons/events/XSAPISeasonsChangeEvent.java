package net.xsapi.panat.xsseasons.events;

import net.xsapi.panat.xsseasons.core.SeasonsInterface;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class XSAPISeasonsChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private SeasonsInterface seasons;

    public XSAPISeasonsChangeEvent(SeasonsInterface seasons) {
        this.seasons = seasons;
    }

    public SeasonsInterface getSeasons() {
        return seasons;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
