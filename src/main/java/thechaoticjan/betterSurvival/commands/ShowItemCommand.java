package thechaoticjan.betterSurvival.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thechaoticjan.betterSurvival.listeners.PlayerInteractionListeners;

import java.util.List;

public class ShowItemCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings)
    {
        if(commandSender instanceof Player player)
        {
            if(player.getInventory().getItemInMainHand().getType() == Material.AIR)
            {
                player.sendActionBar(Component.text("§cDu hast kein Item in der Hand, welches du zeigen könntest"));
            }
            else
            {
                ItemStack stack = player.getInventory().getItemInMainHand();
                Bukkit.broadcast(Component.text("§eɪᴛᴇᴍ §8| ").append(MiniMessage.miniMessage().deserialize(PlayerInteractionListeners.processName(player)).append(Component.text(" zeigt folgendes Item: ")).append(stack.displayName().hoverEvent(HoverEvent.showItem(stack.asHoverEvent().value())))));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings)
    {
        return List.of("");
    }
}
