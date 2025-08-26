package thechaoticjan.betterSurvival.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinAndLeaveListeners implements Listener
{
    @EventHandler
    private void playerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage("<§a+§f> §d" + event.getPlayer().getName());
    }

    @EventHandler
    private void playerLeave(PlayerQuitEvent event)
    {
        event.setQuitMessage("<§c-§f> §d" + event.getPlayer().getName());
    }
}
