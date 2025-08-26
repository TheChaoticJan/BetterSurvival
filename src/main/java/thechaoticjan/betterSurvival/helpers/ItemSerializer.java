package thechaoticjan.betterSurvival.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemSerializer
{
    private static final Gson gson = new Gson();
    private static final Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

    // Serialize one ItemStack -> JSON string
    public static String itemToJson(ItemStack item) {
        if (item == null)
        {
            item = new ItemStack(Material.AIR);// empty slot
        }
        return gson.toJson(item.serialize());
    }

    // Deserialize one JSON string -> ItemStack
    public static ItemStack itemFromJson(String json) {
        if (json == null) return null; // empty slot
        Map<String, Object> map = gson.fromJson(json, mapType);
        return ItemStack.deserialize(map);
    }
}
