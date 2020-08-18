package us.lynuxcraft.deadsilenceiv.customcurrencies.managers;

import org.bukkit.entity.HumanEntity;
import us.lynuxcraft.deadsilenceiv.customcurrencies.gui.BalancesMenu;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager{
    private List<BalancesMenu> balancesMenus;
    public InventoryManager() {
        balancesMenus = new ArrayList<>();
    }

    public void addBalancesMenu(BalancesMenu balancesMenu){
        balancesMenus.add(balancesMenu);
    }

    public void removeBalancesMenu(BalancesMenu balancesMenu){
        balancesMenus.remove(balancesMenu);
    }
    public BalancesMenu getBalancesMenuByViewver(HumanEntity humanEntity){
        for(BalancesMenu balancesMenu : balancesMenus){
            if(balancesMenu.getAllPageViewers().contains(humanEntity))return balancesMenu;
        }
        return null;
    }
}
