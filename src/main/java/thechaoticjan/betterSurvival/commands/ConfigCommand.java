package thechaoticjan.betterSurvival.commands;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thechaoticjan.betterSurvival.Main;
import thechaoticjan.betterSurvival.listeners.PlayerInteractionListeners;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ConfigCommand implements CommandExecutor, Listener, TabCompleter
{
    private static final String INV_TITLE = "§6Stelle deine Farbe ein";
    public static final NamespacedKey COLOR_KEY = new NamespacedKey(Main.getInstance(), "color");

    private static final NamespacedKey ITEM_COLOR_KEY = new NamespacedKey(Main.getInstance(), "item_color");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player player)
        {
            Inventory inventory = Bukkit.createInventory(player, 45, INV_TITLE);

            inventory.setItem(11, makeItem(Material.WHITE_DYE, "<white>Weiß", 0));
            inventory.setItem(12, makeItem(Material.LIGHT_GRAY_DYE, "<gray>Hellgrau", 1));
            inventory.setItem(13, makeItem(Material.GRAY_DYE, "<dark_gray>Dunkelgrau", 2));
            inventory.setItem(14, makeItem(Material.BLACK_DYE, "<#1f1f1f>Schwarz", 3));
            inventory.setItem(15, makeItem(Material.BLUE_DYE, "<dark_blue>Dunkelblau", 4));
            inventory.setItem(19, makeItem(Material.YELLOW_DYE, "<yellow>Gelb", 5));
            inventory.setItem(20, makeItem(Material.ORANGE_DYE, "<gold>Orange", 6));
            inventory.setItem(21, makeItem(Material.RED_DYE, "<red>Rot", 7));
            inventory.setItem(22, makeItem(Material.RED_DYE, "<dark_red>Dunkelrot", 8));
            inventory.setItem(23, makeItem(Material.PURPLE_DYE, "<dark_purple>Lila", 9));
            inventory.setItem(24, makeItem(Material.PINK_DYE, "<light_purple>Pink", 10));
            inventory.setItem(25, makeItem(Material.LIGHT_BLUE_DYE, "<aqua>Hellblau", 11));
            inventory.setItem(29, makeItem(Material.CYAN_DYE, "<dark_aqua>Cyan", 12));
            inventory.setItem(30, makeItem(Material.BLUE_DYE, "<blue>Blau", 13));
            inventory.setItem(31, makeItem(Material.GREEN_DYE, "<dark_green>Grün", 14));
            inventory.setItem(32, makeItem(Material.LIME_DYE, "<green>Hellgrün", 15));
            inventory.setItem(33, makeItem(Material.FIREWORK_STAR, "<rainbow>Regenbogen" , 16));

            player.openInventory(inventory);
        }

        return true;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getWhoClicked() instanceof Player player)
        {

            if (event.getView().getTitle().equalsIgnoreCase(INV_TITLE))
            {
                if (event.getClickedInventory().getSize() != 45)
                {
                    return;
                }

                if (event.getCurrentItem() == null)
                {
                    return;
                }

                PersistentDataContainer container = event.getWhoClicked().getPersistentDataContainer();
                PersistentDataContainer itemContainer = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getPersistentDataContainer();

                container.set(COLOR_KEY, PersistentDataType.INTEGER, Objects.requireNonNull(itemContainer.get(ITEM_COLOR_KEY, PersistentDataType.INTEGER)));
                event.setCancelled(true);
                event.getWhoClicked().sendActionBar(Component.text("§aErfolgreich eine neue Farbe ausgewählt"));
                player.playerListName(MiniMessage.miniMessage().deserialize(PlayerInteractionListeners.processName(player)));
                event.getWhoClicked().closeInventory();
            }
        }
    }



    private ItemStack makeItem(Material material, String name, int colorMapping)
    {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(MiniMessage.miniMessage().deserialize("<i:false>" + name));
        meta.getPersistentDataContainer().set(ITEM_COLOR_KEY, PersistentDataType.INTEGER, colorMapping);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of("");
    }
}
