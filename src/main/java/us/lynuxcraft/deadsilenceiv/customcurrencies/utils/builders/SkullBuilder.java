package us.lynuxcraft.deadsilenceiv.customcurrencies.utils.builders;

import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullBuilder extends ItemBuilder{
    public SkullBuilder() {
        super(Material.SKULL_ITEM);
        setDamage((short)3);
    }
    public SkullBuilder setOwner(String owner){
        SkullMeta skullMeta = (SkullMeta)stack.getItemMeta();
        skullMeta.setOwner(owner);
        stack.setItemMeta(skullMeta);
        return this;
    }
}
