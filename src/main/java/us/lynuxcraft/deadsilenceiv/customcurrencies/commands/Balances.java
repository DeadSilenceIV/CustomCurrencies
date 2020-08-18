package us.lynuxcraft.deadsilenceiv.customcurrencies.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.lynuxcraft.deadsilenceiv.customcurrencies.CustomCurrencies;
import us.lynuxcraft.deadsilenceiv.customcurrencies.gui.BalancesMenu;
import us.lynuxcraft.deadsilenceiv.customcurrencies.managers.InventoryManager;

public class Balances implements CommandExecutor{
    private InventoryManager inventoryManager;
    public Balances() {
        inventoryManager = CustomCurrencies.getInstance().getInventoryManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){
                BalancesMenu balancesMenu = new BalancesMenu(p);
                inventoryManager.addBalancesMenu(balancesMenu);
                balancesMenu.open(p,1);
            }
        }
        return true;
    }
}
