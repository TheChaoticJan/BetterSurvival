package thechaoticjan.betterSurvival.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class InventorySeeCommand implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player player && strings.length == 1)
        {
            String name = strings[0];
            Player toSee = Bukkit.getPlayer(name);

            if(toSee == null)
            {
                player.sendMessage("§cDer Spieler ist leider nicht online");
                return true;
            }

            Inventory inventory = Bukkit.createInventory(player, 45, "§eInventar von §6" + toSee.getName());

            for(int i = 0; i < toSee.getInventory().getSize(); i++)
            {
                ItemStack stack = toSee.getInventory().getItem(i);
                if(stack == null){stack = new ItemStack(Material.AIR);}

                inventory.setItem(i ,stack);
            }

            player.openInventory(inventory);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(strings.length != 1)
        {
            return Collections.singletonList("");
        }
        return null;
    }
}
