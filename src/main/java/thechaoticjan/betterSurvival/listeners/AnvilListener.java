package thechaoticjan.betterSurvival.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class AnvilListener implements Listener
{
    @EventHandler
    private void prepareAnvil(PrepareAnvilEvent event)
    {
        ItemStack result = event.getResult();
        if(result == null)
        {
            return;
        }

        ItemMeta meta = result.getItemMeta();
        if(meta == null)
        {
            return;
        }

        event.getInventory().setMaximumRepairCost(35);

        String name = meta.getDisplayName();
        name = name.replace("&", "§");
        meta.setDisplayName(name);
        result.setItemMeta(meta);

        event.setResult(result);
    }
}
