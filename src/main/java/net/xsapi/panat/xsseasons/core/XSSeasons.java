package net.xsapi.panat.xsseasons.core;

import net.xsapi.panat.xsseasons.configurations.config;
import net.xsapi.panat.xsseasons.configurations.loadConfig;
import net.xsapi.panat.xsseasons.placeholders.xsapi_seasons;
import net.xsapi.panat.xsseasons.tasks.updateInterval;
import net.xsapi.panat.xsseasons.tasks.updatePerSecs;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.ArrayList;

public final class XSSeasons extends JavaPlugin {

    private static XSSeasons plugin;
    private static ArrayList<SeasonsInterface> seasonsList = new ArrayList<>();
    private static long tickCooldowns = 0;
    private static double multi = 0;

    public static XSSeasons getPlugin() {
        return XSSeasons.plugin;
    }
    public static ArrayList<SeasonsInterface> getSeasonsList() {
        return XSSeasons.seasonsList;
    }

    public static long getTickCooldowns() {
        return XSSeasons.tickCooldowns;
    }

    public static double getMulti() {
        return XSSeasons.multi;
    }

    public static void setMulti(double multi) {
        XSSeasons.multi = multi;
    }

    public static void setTickCooldowns(long tickCooldowns) {
        XSSeasons.tickCooldowns = tickCooldowns;
    }

    public static Jedis jedis;
    public static int AutoSave;
    public static int tempAutoSave;

    private boolean usingMySQL = false;
    private String JDBC_URL;
    private String USER;
    private String PASS;
    private String DB_TABLE;
    private String TABLE = "XSSEASON_Data";

    private static boolean usingRedius = false;

    private boolean isParent = false;
    private boolean isCrossServer = false;

    public static boolean isUsingRedius() {
        return usingRedius;
    }

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("§x§f§f§a§c§2§f******************************");
        Bukkit.getConsoleSender().sendMessage("§x§f§f§a§c§2§f   XSAPI Seasons v1.0     ");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§x§f§f§a§c§2§f  Minecraft seasons for farming!");
        Bukkit.getConsoleSender().sendMessage("§r");
        Bukkit.getConsoleSender().sendMessage("§x§f§f§a§c§2§f******************************");

        XSSeasons.plugin = this;

        new loadConfig();

        seasonsList.add(new SeasonsSummer());
        seasonsList.add(new SeasonsFall());
        seasonsList.add(new SeasonsSpring());
        seasonsList.add(new SeasonsWinter());

        SeasonsHandler seasonHandler = new SeasonsHandler();
        seasonHandler.seasonSorted();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage("§x§f§f§c§e§2§2[XSAPI SEASONS] PlaceholderAPI: §x§5§d§f§f§6§3Hook");
            new xsapi_seasons(XSSeasons.getPlugin()).register();
        } else {
            Bukkit.getConsoleSender().sendMessage("§x§f§f§c§e§2§2[XSAPI SEASONS] PlaceholderAPI: §x§f§f§5§8§5§8Not Hook");
        }
        setUPDefault();

        if(usingMySQL) {
            sqlConnection();
        }
        if(config.customConfig.getBoolean("redis.enable")) {
            redisConnection();
        }
        seasonHandler.loadDataSeason();

        seasonHandler.syncWorldTime();

        setMulti((10.0D / (720 / 60.0D)));

        BukkitTask task_1 = (new updateInterval()).runTaskTimer((Plugin) plugin, 200L, 200L);
        BukkitTask task_2 = (new updatePerSecs()).runTaskTimer((Plugin) plugin, 20L, 20L);

        //BukkitTask task_2 = (new updateWorldTime()).runTaskTimer((Plugin) plugin,1L,1L);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("§c[XSAPI SEASONS] Plugin Disabled !");

        SeasonsHandler.saveDataSeason();
    }

    public static void setAutoSave(int autoSave) {
        AutoSave = autoSave;
    }

    public static void setTempAutoSave(int temp) {
        tempAutoSave = temp;
    }

    public static int getTempAutoSave() {
        return tempAutoSave;
    }

    public static int getAutoSave() {
        return AutoSave;
    }

    public boolean isParent() {
        return isParent;
    }

    public boolean isCrossServer() {
        return isCrossServer;
    }

    public boolean getUsingMySQL() {
        return usingMySQL;
    }

    public String getDB_TABLE() {
        return DB_TABLE;
    }

    public String getJDBC_URL() {
        return JDBC_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASS;
    }

    public String getTABLE() {
        return TABLE;
    }

    public void setUPDefault() {
        this.isParent = config.customConfig.getBoolean("cross-server.parent_mode");
        this.isCrossServer = config.customConfig.getBoolean("cross-server.enable");
        this.usingMySQL = config.customConfig.getBoolean("database.enable");
        setAutoSave(config.customConfig.getInt("auto_save"));
        setTempAutoSave(config.customConfig.getInt("auto_save"));
    }

    public void sqlConnection() {
        String host = config.customConfig.getString("database.host");
        DB_TABLE =  config.customConfig.getString("database.dbTable");
        JDBC_URL = "jdbc:mysql://" + host +  "/" + DB_TABLE;
        USER = config.customConfig.getString("database.user");
        PASS = config.customConfig.getString("database.password");

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL,USER,PASS);

            Statement statement = connection.createStatement();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + getTABLE() + " ("
                    + "season VARCHAR(64) DEFAULT 'Summer', "
                    + "year INT DEFAULT 1, "
                    + "yearidx INT DEFAULT 0, "
                    + "day INT DEFAULT 1, "
                    + "hours INT DEFAULT 0, "
                    + "minutes INT DEFAULT 0"
                    + ")";

            statement.executeUpdate(createTableQuery);

            Statement statementInsert = connection.createStatement();

            String insertQuery = "INSERT INTO " + getTABLE() + " (season, year, yearidx, day, hours, minutes) "
                    + "VALUES ('Summer', 1, 0, 1, 0, 0)";

            statementInsert.executeUpdate(insertQuery);
            statementInsert.close();
            statement.close();

            connection.close();

            Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Database : §x§6§0§F§F§0§0Connected");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Database : §x§C§3§0§C§2§ANot Connected");
            e.printStackTrace();
        }
    }

    private void redisConnection() {
        String redisHost = config.customConfig.getString("redis.host");
        int redisPort = config.customConfig.getInt("redis.port");
        String password = config.customConfig.getString("redis.password");

        try {
            jedis = new Jedis(redisHost, redisPort);
            if(!password.isEmpty()) {
                jedis.auth(password);
            }
            jedis.close();
            usingRedius = true;
            Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Redis Server : §x§6§0§F§F§0§0Connected");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Redis Server : §x§C§3§0§C§2§ANot Connected");
            e.printStackTrace();
        }
    }

    public static void redisUpdateKey() {
        try {
            jedis.set("season", SeasonsHandler.getDataSeasonInterface().getSeasonRealName());
            jedis.set("year", String.valueOf(SeasonsHandler.getYear()));
            jedis.set("yearidx", String.valueOf(SeasonsHandler.getIndexYearCounter()));
            jedis.set("day", String.valueOf(SeasonsHandler.getDay()));
            jedis.set("hours", String.valueOf(SeasonsHandler.getHour()));
            jedis.set("minutes", String.valueOf(SeasonsHandler.getMinutes()));

       //    Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Redis Server : §x§6§0§F§F§0§0Updated Key!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void redisGetKey() {
        String season = jedis.get("season");
        int year = Integer.parseInt(jedis.get("year"));
        int yearidx = Integer.parseInt(jedis.get("yearidx"));
        int day = Integer.parseInt(jedis.get("day"));
        int hours = Integer.parseInt(jedis.get("hours"));
        int minutes = Integer.parseInt(jedis.get("minutes"));

        SeasonsHandler.setDataSeasonInterface(SeasonsHandler.getSeasonByRealName(season));
        SeasonsHandler.setYear(year);
        SeasonsHandler.setIndexYearCounter(yearidx);
        SeasonsHandler.setDay(day);
        SeasonsHandler.setHour(hours);
        SeasonsHandler.setMinutes(minutes);

      //  Bukkit.getConsoleSender().sendMessage("§x§E§7§F§F§0§0[XSAPI SEASONS] Redis Server : §x§6§0§F§F§0§0Get Key!");
    }

}
