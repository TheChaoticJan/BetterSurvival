package thechaoticjan.betterSurvival.backpack;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import thechaoticjan.betterSurvival.Main;

import java.sql.SQLException;

public class BackpackListeners implements Listener
{
    private void safeInventory(InventoryEvent event, Player notToChange)
    {
        if(event.getView().getTitle().equalsIgnoreCase(Backpack.getName()))
        {
            Inventory inventory = event.getInventory();
            Main main = Main.getInstance();

            ItemStack [] items = new ItemStack[Backpack.getSize()];
            for(int i = 0; i < inventory.getSize(); i++)
            {
                items[i] = inventory.getItem(i);
            }

            try
            {
                main.getDatabase().safeBackpack(new Backpack(items));
            }
            catch (SQLException e){e.printStackTrace();}

            Bukkit.getScheduler().runTaskAsynchronously(main, () ->
            {
                try
                {
                    main.getDatabase().safeBackpack(new Backpack(items));
                }
                catch (SQLException e) {e.printStackTrace();}
            });

            // Update other players on main thread
            Bukkit.getScheduler().runTask(main, () ->
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (player == notToChange) continue;

                    InventoryView view = player.getOpenInventory();
                    if (view.getTitle().equalsIgnoreCase(Backpack.getName()))
                    {
                        Inventory top = view.getTopInventory();
                        for (int i = 0; i < items.length; i++) {

                            top.setItem(i, items[i]);
                        }
                        player.updateInventory();
                    }
                }
            });
        }
    }

    @EventHandler
    private void playerCloseInventory(InventoryCloseEvent event)
    {
      safeInventory(event, (Player) event.getPlayer());
    }

    @EventHandler
    private void playerDragInventory(InventoryDragEvent event)
    {
        if(event.getWhoClicked() instanceof Player player)
        {
            safeInventory(event, player);
        }
    }

    @EventHandler
    private void playerClickInventory(InventoryClickEvent event)
    {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> safeInventory(event, player));
    }
}

