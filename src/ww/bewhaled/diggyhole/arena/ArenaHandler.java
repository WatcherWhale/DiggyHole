package ww.bewhaled.diggyhole.arena;

import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ArenaHandler
{
    private Main plugin;
    private HashMap<String,Arena> arenas;

    public ArenaHandler(Main pl)
    {
        this.plugin = pl;
        this.arenas = new HashMap<>();
    }

    public boolean isPlayerInGame(Player player)
    {
        for(Arena ar : arenas.values())
        {
            for(DHPlayer pl : ar.getPlayers())
            {
                if(pl.getPlayer() == player && ar.isStarted()) return true;
            }
        }

        return false;
    }

    public boolean isPlayerJoined(Player player)
    {
        for(Arena ar : arenas.values())
        {
            for(DHPlayer pl : ar.getPlayers())
            {
                if(pl.getPlayer() == player) return true;
            }
        }

        return false;
    }

    public void AddArena(Arena ar)
    {
        arenas.put(ar.getName(),ar);
    }

    public Arena FindArena(String arena)
    {
        return arenas.get(arena);
    }

    public Arena FindArena(Player player)
    {
        for(Arena ar : arenas.values())
        {
            for(DHPlayer pl : ar.getPlayers())
            {
                if(pl.getPlayer() == player) return ar;
            }
        }

        return null;
    }

    public Collection<Arena> getArenas()
    {
        return this.arenas.values();
    }
}
