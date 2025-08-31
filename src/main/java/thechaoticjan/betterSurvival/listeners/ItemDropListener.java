package thechaoticjan.betterSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ItemDropListener implements Listener
{
    @EventHandler
    private void onItemDrop(PlayerDropItemEvent event)
    {
        ItemStack stack = event.getItemDrop().getItemStack();

        if(Objects.requireNonNull(stack.getItemMeta()).hasDisplayName())
        {
            event.getItemDrop().setCustomName(stack.getItemMeta().getDisplayName());
            event.getItemDrop().setCustomNameVisible(true);
        }
    }
}
