package com.cogito.bukkit.bob;

import java.util.List;

import org.bukkit.entity.Player;

class BusinessAccount extends Account {
    private List<Player> directors;

    public BusinessAccount(List<Player> directors) {
        super();
        this.directors = directors;
    }

    @Override
    void sendMessage(String message) {
        for (Player director : directors){
            director.sendMessage(message);
        }
    }
}
