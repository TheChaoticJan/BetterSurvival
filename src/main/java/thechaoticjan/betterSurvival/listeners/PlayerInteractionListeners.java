package thechaoticjan.betterSurvival.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import thechaoticjan.betterSurvival.ScoreboardManager;
import thechaoticjan.betterSurvival.commands.ConfigCommand;

public class PlayerInteractionListeners implements Listener
{
    @EventHandler
    private void playerJoin(PlayerJoinEvent event)
    {
        event.joinMessage(Component.text("§7‹§a+§7› ").append(MiniMessage.miniMessage().deserialize(processName(event.getPlayer()))));

        event.getPlayer().playerListName(MiniMessage.miniMessage().deserialize(processName(event.getPlayer())));
    }

    @EventHandler
    private void playerLeave(PlayerQuitEvent event)
    {
        event.quitMessage(Component.text("§7‹§c-§7› ").append(MiniMessage.miniMessage().deserialize(processName(event.getPlayer()))));
    }

    @EventHandler
    private void onMessage(AsyncPlayerChatEvent event)
    {
        Component message = Component.text("§f" + event.getMessage().replace("&", "§"));

        message = MiniMessage.miniMessage().deserialize(processName(event.getPlayer())).append(MiniMessage.miniMessage().deserialize(" <dark_gray>▸ ")).append(message);
        event.setCancelled(true);
        Bukkit.broadcast(message);
    }

    @EventHandler
    private void onNameSign(SignChangeEvent event)
    {
        int i = 0;
        for(String s : event.getLines())
        {
            event.setLine(i, s.replace("&", "§"));
            i++;
        }
    }

    public static String processName(Player player)
    {
        String name = "";
        int colorMap = 0;
        String playerName = player.getName();

        if(player.getPersistentDataContainer().has(ConfigCommand.COLOR_KEY))
        {
            PersistentDataContainer container = player.getPersistentDataContainer();
            colorMap = container.get(ConfigCommand.COLOR_KEY, PersistentDataType.INTEGER);
        }

        switch (colorMap)
        {
            case 1  -> name = "<gray>" + playerName + "</gray>";
            case 2  -> name = "<dark_gray>" + playerName + "</dark_gray>";
            case 3  -> name = "<black>" + playerName + "</black>";
            case 4  -> name = "<dark_blue>" + playerName + "</dark_blue>";
            case 5  -> name = "<yellow>" + playerName + "</yellow>";
            case 6  -> name = "<gold>" + playerName + "</gold>";
            case 7  -> name = "<red>" + playerName + "</red>";
            case 8  -> name = "<dark_red>" + playerName + "</dark_red>";
            case 9  -> name = "<dark_purple>" + playerName + "</dark_purple>";
            case 10 -> name = "<light_purple>" + playerName + "</light_purple>";
            case 11 -> name = "<aqua>" + playerName + "</aqua>";
            case 12 -> name = "<dark_aqua>" + playerName + "</dark_aqua>";
            case 13 -> name = "<blue>" + playerName + "</blue>";
            case 14 -> name = "<dark_green>" + playerName + "</dark_green>";
            case 15 -> name = "<green>" + playerName + "</green>";
            case 16 ->
            {
                name = "<rainbow>" + playerName + "</rainbow>";
            }
            default -> name = "<white>" + playerName + "<white>";
         }

        return name;
    }
}
