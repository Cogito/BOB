package com.cogito.bukkit.bob;

import org.bukkit.entity.Player;

class PlayerAccount extends Account{

    private Player accountHolder;

    public PlayerAccount(Player accountHolder) {
        super();
        this.accountHolder = accountHolder;
    }

    @Override
    void sendMessage(String message) {
        accountHolder.sendMessage(message);
    }
}
