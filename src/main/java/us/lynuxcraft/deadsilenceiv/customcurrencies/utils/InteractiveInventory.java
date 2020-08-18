package us.lynuxcraft.deadsilenceiv.customcurrencies.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public abstract class InteractiveInventory {
    protected Inventory inventory;
    protected ArrayList<InventoryAction> actions;
    protected InteractiveInventory(){
        actions = new ArrayList<>();
    }

    public static Integer calculateRows(int size){
        return size % 9 == 0 ? size : size + (9 - (size % 9));
    }
    public Inventory getInventory() {
        return inventory;
    }
    public static abstract class InventoryAction{
        @Getter private int slot;
        @Getter private ClickType clickType;
        public InventoryAction(int slot,ClickType type){
            this.slot = slot;
            this.clickType = type;
        }
        public abstract void execute(Player p);
    }

    protected void addAction(InventoryAction action){
        actions.add(action);
    }

    protected void removeAction(InventoryAction action){
        if(actions.contains(action)){
            actions.remove(action);
        }
    }

    public InventoryAction getSlotAction(int slot,ClickType type){
        for(InventoryAction action : getActions()){
            if(slot == action.getSlot() && action.getClickType() == type){
                return action;
            }
        }
        return null;
    }
    protected ArrayList<InventoryAction> getActions() {
        return actions;
    }
}
