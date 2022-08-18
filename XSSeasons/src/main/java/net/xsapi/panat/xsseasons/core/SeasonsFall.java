package net.xsapi.panat.xsseasons.core;

import net.xsapi.panat.xsseasons.configurations.config;

public class SeasonsFall implements SeasonsInterface {
    private Seasons seasons = Seasons.Fall;
    private String season_realName = "Fall";
    private int days = config.customConfig.getInt("Seasons." + this.season_realName + ".day_period");
    private String season_str = config.customConfig.getString("Seasons." + this.season_realName + ".display");

    @Override
    public Seasons getSeasons() {
        return seasons;
    }

    @Override
    public void setSeasons(Seasons seasons) {
        this.seasons = seasons;
    }

    @Override
    public int getDays() {
        return this.days;
    }

    @Override
    public String getSeason_str() {
        return season_str;
    }

    @Override
    public String getSeasonRealName() {
        return season_realName;
    }

    @Override
    public void Events() {

    }
}
