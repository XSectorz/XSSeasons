package net.xsapi.panat.xsseasons.core;

public interface SeasonsInterface {

    Seasons seasons = null;
    int days = 0;
    String realName = "";
    String season_str = "";

    public Seasons getSeasons();
    public void setSeasons(Seasons seasons);
    public int getDays();
    public String getSeason_str();
    public String getSeasonRealName();
    public void Events();


}
