package thechaoticjan.betterSurvival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class PingCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if(commandSender instanceof Player player)
        {
            int ping = player.getPing();

            if(ping > 100)
            {
                player.sendMessage("Dein aktueller Ping beträgt: §c" + ping);
            }

            if(ping > 25)
            {
                player.sendMessage("Dein aktueller Ping beträgt: §e" + ping);
            }

            player.sendMessage("Dein aktueller Ping beträgt: §a" + ping);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings)
    {
        return List.of("");
    }
}
