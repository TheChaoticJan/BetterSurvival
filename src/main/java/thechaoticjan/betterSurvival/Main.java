package thechaoticjan.betterSurvival;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import thechaoticjan.betterSurvival.backpack.BackpackCommand;
import thechaoticjan.betterSurvival.backpack.BackpackListeners;
import thechaoticjan.betterSurvival.commands.*;
import thechaoticjan.betterSurvival.database.Database;
import thechaoticjan.betterSurvival.listeners.*;
import org.bukkit.event.Listener;

import java.sql.SQLException;
import java.util.*;

public final class Main extends JavaPlugin implements CommandExecutor, Listener
{
    @Getter @Setter
    private static Main instance;

    @Getter @Setter
    private Database database;

    private ScoreboardManager manager;

    @Override
    public void onEnable()
    {
        instance = this;

        database = new Database();
        try
        {
            database.initializeDatabase();
        }
        catch (SQLException e){e.printStackTrace();}


        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new BackpackListeners(), this);
        getServer().getPluginManager().registerEvents(new InventorySeeListeners(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListeners(), this);
        getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        getServer().getPluginManager().registerEvents(new ConfigCommand(), this);
        getServer().getPluginManager().registerEvents(this, this);

        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("invsee").setExecutor(new InventorySeeCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rename").setExecutor(new RenameCommand());
        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("ec").setExecutor(new EnderchestCommand());
        getCommand("showitem").setExecutor(new ShowItemCommand());

        manager = new ScoreboardManager(this);
        getCommand("rank").setExecutor(manager);
        getCommand("afk").setExecutor(manager);
        getServer().getPluginManager().registerEvents(manager, this);

        manager.setupHeaderFooter();

    }

    @Override
    public void onDisable()
    {
        manager.saveRanks();
    }
}
