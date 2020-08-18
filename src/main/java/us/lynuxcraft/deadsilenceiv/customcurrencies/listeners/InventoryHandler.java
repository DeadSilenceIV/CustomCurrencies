package us.lynuxcraft.deadsilenceiv.customcurrencies.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.gui.BalancesMenu;
import us.lynuxcraft.deadsilenceiv.customcurrencies.gui.BalancesPage;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.InventoryManager;
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.InteractiveInventory;

public class InventoryHandler implements Listener{
    private InventoryManager inventoryManager;
    public InventoryHandler() {
        inventoryManager = CustomCurrencies.getInstance().getInventoryManager();
    }


    @EventHandler
    public void onClickInventory(InventoryClickEvent e){
        Inventory inventory = e.getClickedInventory();
        Player p = (Player)e.getWhoClicked();
        ItemStack currentItem = e.getCurrentItem();
        Integer slot = e.getSlot();
        ClickType clickType = e.getClick();
        if(inventory != p.getInventory() && currentItem != null){
            BalancesMenu balancesMenu = inventoryManager.getBalancesMenuByViewver(p);
            if(balancesMenu != null){
                e.setCancelled(true);
                BalancesPage page = balancesMenu.getViewerPage(p);
                InteractiveInventory.InventoryAction action = page.getSlotAction(slot,clickType);
                if (action != null) {
                    action.execute(p);
                }
            }
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        Player p = (Player)e.getPlayer();
        BalancesMenu balancesMenu = inventoryManager.getBalancesMenuByViewver(p);
        if(balancesMenu != null){
            if(!balancesMenu.isViewerChangingPage(p)){
                inventoryManager.removeBalancesMenu(balancesMenu);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        BalancesMenu balancesMenu = inventoryManager.getBalancesMenuByViewver(p);
        if(balancesMenu != null){
            inventoryManager.removeBalancesMenu(balancesMenu);
        }
    }
}
