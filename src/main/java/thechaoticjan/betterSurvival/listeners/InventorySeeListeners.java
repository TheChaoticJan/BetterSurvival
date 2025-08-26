package thechaoticjan.betterSurvival.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventorySeeListeners implements Listener
{
    @EventHandler
    private void onInventoryClick(InventoryClickEvent event)
    {
            if(event.getView().getTitle().contains("§eInventar von"))
            {
                event.setCancelled(true);
            }
    }
}
