package thechaoticjan.betterSurvival;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import thechaoticjan.betterSurvival.backpack.BackpackCommand;
import thechaoticjan.betterSurvival.backpack.BackpackListeners;
import thechaoticjan.betterSurvival.commands.*;
import thechaoticjan.betterSurvival.database.Database;
import thechaoticjan.betterSurvival.listeners.*;

import java.sql.SQLException;

public final class Main extends JavaPlugin
{
    @Getter @Setter
    private static Main instance;

    @Getter @Setter
    private Database database;

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

        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("invsee").setExecutor(new InventorySeeCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rename").setExecutor(new RenameCommand());
        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("ec").setExecutor(new EnderchestCommand());
        getCommand("showitem").setExecutor(new ShowItemCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
