package AlternativeInteractionControllers.FightController;

import Player.Player;
import Player.PlayerState;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Arena {
    private Queue<Player> players;
    private ArrayList<Battle> battles;

    public Arena() {
        battles = new ArrayList<Battle>();
        players = new ArrayDeque<Player>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setPlayerState(PlayerState.OnArena);
        this.tryStartBattle();
    }

    public int getQueueSize() {
        return players.size();
    }



    public boolean tryStartBattle() {
        if(players.size() >= 2) {
            Player p1 = players.poll();
            Player p2 = players.poll();
            p1.setPlayerState(PlayerState.InBattle);
            p2.setPlayerState(PlayerState.InBattle);
            battles.add(new Battle(p1, p2));
            return true;
        }
        return false;
    }
}
