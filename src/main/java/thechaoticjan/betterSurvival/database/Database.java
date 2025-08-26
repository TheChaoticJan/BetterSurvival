package thechaoticjan.betterSurvival.database;

import org.bukkit.inventory.ItemStack;
import thechaoticjan.betterSurvival.Main;
import thechaoticjan.betterSurvival.helpers.ItemSerializer;
import thechaoticjan.betterSurvival.backpack.Backpack;

import java.sql.*;

public class Database
{
    private Connection connection;

    private Connection getConnection() throws SQLException
    {
        if(connection != null)
        {
            return connection;
        }

        String url = "jdbc:sqlite:database.db";

        this.connection = DriverManager.getConnection(url);
        Main.getInstance().getLogger().info("Database connection established.");

        return this.connection;
    }

    public void initializeDatabase() throws SQLException
    {
        Statement statement = getConnection().createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS bp_items(id INTEGER PRIMARY KEY AUTOINCREMENT, items TEXT)";
        statement.execute(sql);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(
                "WITH RECURSIVE slots(n) AS ( " +
                        "    SELECT 0 " +
                        "    UNION ALL " +
                        "    SELECT n + 1 FROM slots WHERE n < 44 " +
                        ") " +
                        "INSERT INTO bp_items (items) " +
                        "SELECT 'null' FROM slots " +
                        "WHERE NOT EXISTS (SELECT 1 FROM bp_items)"
        );
        stmt.close();
        statement.close();
    }

    public Backpack loadBackpack() throws SQLException
    {
        ItemStack [] items = new ItemStack[Backpack.getSize()];

        Statement statement = getConnection().createStatement();
        String sql = "SELECT * FROM bp_items;";

        ResultSet results = statement.executeQuery(sql);

        int i = 0;
        while(results.next())
        {
            items[i] = ItemSerializer.itemFromJson(results.getString("items"));
            i++;
        }

        statement.close();

        return new Backpack(items);
    }

    public synchronized void safeBackpack(Backpack backpack) throws SQLException
    {
        ItemStack [] items = backpack.getItems();
        int i = 1;

        for(ItemStack stack : items)
        {
            String json = ItemSerializer.itemToJson(stack);

            PreparedStatement statement = getConnection().prepareStatement
                    (
                    "UPDATE bp_items SET items = ? WHERE id = ?;"
                    );

            statement.setString(1, json);
            statement.setInt(2, i);
            statement.executeUpdate();

            i++;
        }

    }




}
