package thechaoticjan.betterSurvival;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static thechaoticjan.betterSurvival.listeners.PlayerInteractionListeners.processName;

public class ScoreboardManager implements CommandExecutor, Listener {

    private final Map<UUID, String> ranks = new HashMap<>();
    private final Set<UUID> afkPlayers = new HashSet<>();

    private final Scoreboard scoreboard;

    private final File file;
    private final FileConfiguration config;

    public ScoreboardManager(JavaPlugin plugin)
    {
        file = new File(plugin.getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        // Load ranks from YAML
        if (config.isConfigurationSection("ranks")) {
            for (String uuid : config.getConfigurationSection("ranks").getKeys(false)) {
                ranks.put(UUID.fromString(uuid), config.getString("ranks." + uuid));
            }
        }

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void saveRanks() {
        for (Map.Entry<UUID, String> entry : ranks.entrySet()) {
            config.set("ranks." + entry.getKey().toString(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupHeaderFooter()
    {
        MiniMessage mm = MiniMessage.miniMessage();
        Component header = mm.deserialize("<gradient:white:light_purple>Survival Blumenwiese</gradient>").append(Component.newline());
        Component footer = Component.newline().append(mm.deserialize("<yellow>Gönnt euch").append(Component.newline()).append(mm.deserialize("<green>Und seid lieb zu einander!</green>")));

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendPlayerListHeaderAndFooter(header, footer);
        }
    }

    // Command executor for /rank and /afk
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (args.length < 1) {
                p.sendMessage(ChatColor.RED + "Usage: /rank <name>");
                return true;
            }

            String rankName = args[0].replace("&", "§").replace("&l", "");
            String prefix = rankName + "§8 |" + ChatColor.RESET;

            ranks.put(p.getUniqueId(), prefix);
            updatePlayer(p);
            p.sendMessage(ChatColor.GREEN + "Dein neuer Rang: " + prefix);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("afk")) {
            if (afkPlayers.contains(p.getUniqueId())) {
                afkPlayers.remove(p.getUniqueId());
                p.sendMessage(ChatColor.GREEN + "Du bist jetzt nicht mehr AFK.");
            } else {
                afkPlayers.add(p.getUniqueId());
                p.sendMessage(ChatColor.YELLOW + "Du bist jetzt AFK.");
            }
            updatePlayer(p);
            return true;
        }

        return false;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        // If they don’t have a stored rank yet, set a default
        ranks.putIfAbsent(player.getUniqueId(), "");

        // Ensure AFK state defaults to false (nothing needed since it's in a Set)

        // Apply their prefix/suffix/team again
        updatePlayer(player);
    }

    /** Updates the scoreboard team for the player */
    public void updatePlayer(Player p)
    {
        // Use a unique team name per player (UUID substring)
        String teamName = "t_" + p.getUniqueId().toString().substring(0, 16);

        Team team = scoreboard.getTeam(teamName);
        if (team == null)
        {
            team = scoreboard.registerNewTeam(teamName);
        }

        // Remove the player from all other teams
        scoreboard.getTeams().forEach(t -> t.removeEntry(p.getName()));

        // Get prefix and suffix
        String prefix = ranks.getOrDefault(p.getUniqueId(), "" + ChatColor.RESET);
        String suffix = afkPlayers.contains(p.getUniqueId()) ? " §7§o(AFK)" : "";

        // Apply
        team.prefix(Component.text(prefix + " "));
        team.suffix(Component.text(suffix));
        team.addEntry(p.getName());

        p.playerListName(Component.text(prefix + " ").append(MiniMessage.miniMessage().deserialize(processName(p))).append(Component.text(suffix)));

        // Force client refresh by temporarily resetting the scoreboard
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); // empty scoreboard
        p.setScoreboard(scoreboard); // assign our scoreboard

        // Optional: debug log
        Bukkit.getLogger().info("Updated team=" + team.getName()
                + " prefix=" + team.getPrefix()
                + " suffix=" + team.getSuffix()
                + " entries=" + team.getEntries());
    }
}
