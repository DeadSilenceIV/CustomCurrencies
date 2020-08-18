package us.lynuxcraft.deadsilenceiv.customcurrencies.utils.builders;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemBuilder{
    protected ItemStack stack;
    public ItemBuilder(Material material) {
        stack = new ItemStack(material);
    }

    public ItemBuilder setAmount(Integer amount){
        stack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDamage(Short damage){
        stack.setDurability(damage);
        return this;
    }


    public ItemBuilder setName(String name){
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',name));
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        if(!lore.isEmpty()) {
            for(int i = 0; i <= lore.size()-1; i++) {
                String line = lore.get(i);
                lore.set(i,ChatColor.translateAlternateColorCodes('&',line));
            }
        }
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, Integer level, boolean unsafe){
        if(unsafe){
            stack.addUnsafeEnchantment(enchantment,level);
        }else{
            stack.addEnchantment(enchantment,level);
        }
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment,Integer> enchantments,boolean unsafe){
        if(unsafe){
            stack.addUnsafeEnchantments(enchantments);
        }else{
            stack.addEnchantments(enchantments);
        }
        return this;
    }

    public ItemStack build() {
        return stack;
    }
}
