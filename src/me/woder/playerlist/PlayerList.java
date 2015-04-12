package me.woder.playerlist;

import java.util.HashMap;
import java.util.UUID;

import me.woder.bot.Client;

public class PlayerList {
    public HashMap<UUID, PlayerL> players = new HashMap<UUID, PlayerL>();
    Client c;
    
    public PlayerList(Client client) {
        this.c = client;
    }
}