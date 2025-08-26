package thechaoticjan.betterSurvival.backpack;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class Backpack
{
    @Getter
    private static final String name = "§6ʙᴀᴄᴋᴘᴀᴄᴋ";

    @Getter
    private static final int size = 45;

    @Getter @Setter
    private static ItemStack [] items;

    public Backpack(ItemStack [] stacks)
    {
        Backpack.items = stacks;
    }
}
