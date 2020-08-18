package us.lynuxcraft.deadsilenceiv.customcurrencies.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import us.lynuxcraft.deadsilenceiv.customcurrencies.Account;
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.InteractiveInventory;
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.builders.ItemBuilder;
import us.lynuxcraft.deadsilenceiv.customcurrencies.utils.builders.SkullBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalancesPage extends InteractiveInventory{
    private BalancesMenu balancesMenu;
    @Getter private Integer id;
    private List<Account> accounts;
    BalancesPage(BalancesMenu balancesMenu, Integer id) {
        this.balancesMenu = balancesMenu;
        this.id = id;
        this.accounts = new ArrayList<>();
        inventory = Bukkit.createInventory(null,54, ChatColor.translateAlternateColorCodes('&',"&c&k..&7Balances page - "+id+"&c&k.."));
        for(int i = 45; i <= 53;i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 7).build());
        }
    }

    public void open(Player player){
        balancesMenu.viewersChangingPage.add(player);
        player.openInventory(this.inventory);
        balancesMenu.viewersChangingPage.remove(player);
    }

    void load(){
        for(Account account : accounts){
            ItemStack accountStack = new ItemBuilder(Material.PAPER).setName("&a"+account.getEconomy().getName()).setLore(Arrays.asList("&7Balance&8: &7"+account.getBalance()+"&f$")).build();
            inventory.addItem(accountStack);
        }
        loadPageSelector();
    }

    private void loadPageSelector(){
        Integer totalPages = balancesMenu.getPages().size();
        if(totalPages > 1) {
            if (id.equals(1)) {
                inventory.setItem(50,new SkullBuilder().setOwner("MHF_ArrowRight").setName("&6&lNext page").build());
                addAction(new InventoryAction(50, ClickType.LEFT) {
                    @Override
                    public void execute(Player p) {
                        balancesMenu.open(p,id+1);
                    }
                });
            }
            if (id > 1 && id < totalPages) {
                inventory.setItem(50,new SkullBuilder().setOwner("MHF_ArrowRight").setName("&6&lNext page").build());
                inventory.setItem(48,new SkullBuilder().setOwner("MHF_ArrowLeft").setName("&6&lPrevious page").build());
                addAction(new InventoryAction(50, ClickType.LEFT) {
                    @Override
                    public void execute(Player p) {
                        balancesMenu.open(p,id+1);
                    }
                });
                addAction(new InventoryAction(48, ClickType.LEFT) {
                    @Override
                    public void execute(Player p) {
                        balancesMenu.open(p,id-1);
                    }
                });
            }
            if (id.equals(totalPages)) {
                inventory.setItem(48,new SkullBuilder().setOwner("MHF_ArrowLeft").setName("&6&lPrevious page").build());
                addAction(new InventoryAction(48, ClickType.LEFT) {
                    @Override
                    public void execute(Player p) {
                        balancesMenu.open(p,id-1);
                    }
                });
            }
        }
    }

    void addAccount(Account account){
        accounts.add(account);
    }
}
