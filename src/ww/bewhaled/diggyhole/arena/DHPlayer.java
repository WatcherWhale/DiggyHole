package ww.bewhaled.diggyhole.arena;

import org.bukkit.entity.Player;

public class DHPlayer
{
    private Player player;
    private int points;
    private String arena;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }
}
