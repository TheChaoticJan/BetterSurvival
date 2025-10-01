package thechaoticjan.betterSurvival.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.List;

public class RenameCommand implements CommandExecutor, TabCompleter
{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(commandSender instanceof Player player)
        {
            if(strings.length < 1)
            {
                player.sendMessage("§cBitte benutze §e/rename <Name>");
                return true;
            }

            ItemStack toRename = player.getItemInHand();

            if(toRename.getType() == Material.AIR)
            {
                player.sendMessage("§cDu hältst kein Item in der Hand, was du umebenennen könntest!");
                return true;
            }

            ItemMeta meta = toRename.getItemMeta();

            StringBuilder name = new StringBuilder();
            for(String string : strings)
            {
                name.append(string.replace("&", "§"));
                name.append(" ");
            }

            name.deleteCharAt(name.length() - 1);

            meta.setDisplayName(name.toString());
            toRename.setItemMeta(meta);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy("§aErfolgreich das Item umbenannt!"));

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of("");
    }
}
