package thechaoticjan.betterSurvival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import thechaoticjan.betterSurvival.commands.ConfigCommand;

public class PlayerInteractionListeners implements Listener
{
    @EventHandler
    private void playerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("§7‹§a+§7› " + processName(event.getPlayer()));
    }

    @EventHandler
    private void playerLeave(PlayerQuitEvent event)
    {
        event.setQuitMessage("§7‹§c-§7› " + processName(event.getPlayer()));
    }

    @EventHandler
    private void onMessage(AsyncPlayerChatEvent event)
    {
        String message = event.getMessage().replace("&", "§");

        message = processName(event.getPlayer()) + " §8▸ §r" + message;
        event.setCancelled(true);
        Bukkit.broadcastMessage(message);
    }

    private String processName(Player player)
    {
        String name = "";
        int colorMap = 0;
        char [] rainbowColors = new char[]{'c', '6', 'e', 'a', 'b', '9', 'd'};
        String playerName = player.getName();

        if(player.getPersistentDataContainer().has(ConfigCommand.COLOR_KEY))
        {
            PersistentDataContainer container = player.getPersistentDataContainer();
            colorMap = container.get(ConfigCommand.COLOR_KEY, PersistentDataType.INTEGER);
        }

        switch (colorMap)
        {
            case 1  -> name = "§7" + playerName;
            case 2  -> name = "§8" + playerName;
            case 3  -> name = "§0" + playerName;
            case 4  -> name = "§1" + playerName;
            case 5  -> name = "§e" + playerName;
            case 6  -> name = "§6" + playerName;
            case 7  -> name = "§c" + playerName;
            case 8  -> name = "§4" + playerName;
            case 9  -> name = "§5" + playerName;
            case 10 -> name = "§d" + playerName;
            case 11 -> name = "§b" + playerName;
            case 12 -> name = "§3" + playerName;
            case 13 -> name = "§9" + playerName;
            case 14 -> name = "§2" + playerName;
            case 15 -> name = "§a" + playerName;
            case 16 ->
            {
                int i = 0;
                StringBuilder nameBuilder = new StringBuilder(name);
                for(char c : playerName.toCharArray()){
                    nameBuilder.append("§").append(rainbowColors[i]).append(c);
                    i += 1;
                    if(i >= 7)
                    {
                        i = 0;
                    }
                }
                name = nameBuilder.toString();
            }
            default -> name = "§f" + playerName;
         }

        return name;
    }
}
