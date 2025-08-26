package thechaoticjan.betterSurvival;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import thechaoticjan.betterSurvival.backpack.BackpackCommand;
import thechaoticjan.betterSurvival.backpack.BackpackListeners;
import thechaoticjan.betterSurvival.commands.InventorySeeCommand;
import thechaoticjan.betterSurvival.commands.PingCommand;
import thechaoticjan.betterSurvival.database.Database;
import thechaoticjan.betterSurvival.listeners.AnvilListener;
import thechaoticjan.betterSurvival.listeners.InventorySeeListeners;
import thechaoticjan.betterSurvival.listeners.JoinAndLeaveListeners;

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
        getServer().getPluginManager().registerEvents(new JoinAndLeaveListeners(), this);

        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("invsee").setExecutor(new InventorySeeCommand());
        getCommand("ping").setExecutor(new PingCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
