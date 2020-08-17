package us.lynuxcraft.deadsilenceiv.customcurrencies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EconomyResponse{
    @Getter private Account account;
    @Getter private Double amount;
}
