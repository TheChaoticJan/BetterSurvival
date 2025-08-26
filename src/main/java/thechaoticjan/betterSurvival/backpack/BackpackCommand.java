package thechaoticjan.betterSurvival.backpack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import thechaoticjan.betterSurvival.Main;

import java.sql.SQLException;

public class BackpackCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {

        if(commandSender instanceof Player player)
        {

            Main main = Main.getInstance();

            try
            {
                Backpack backpack = main.getDatabase().loadBackpack();

                Inventory inventory = Bukkit.createInventory(player, Backpack.getSize(), Backpack.getName());


                for(int i = 0; i < backpack.getItems().length; i++)
                {
                    ItemStack stack = backpack.getItems()[i];
                    if(stack == null)
                    {
                        stack = new ItemStack(Material.AIR);
                    }
                    inventory.addItem(stack);
                }

                player.openInventory(inventory);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
