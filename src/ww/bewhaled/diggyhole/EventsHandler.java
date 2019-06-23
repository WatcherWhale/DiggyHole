package ww.bewhaled.diggyhole;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ww.bewhaled.diggyhole.arena.Arena;

public class EventsHandler implements Listener
{
    private Main plugin;

    public EventsHandler(Main pl)
    {
        this.plugin = pl;
    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        boolean joined = this.plugin.getArenas().isPlayerJoined(player);
        boolean inGame = this.plugin.getArenas().isPlayerInGame(player);

        if(joined && !inGame)
        {
            e.setCancelled(true);
        }
        else if(inGame)
        {
            Arena ar = this.plugin.getArenas().FindArena(player);

            e.setDropItems(false);
            e.setCancelled(true);

            if(GetCancelState(e.getBlock().getType(),ar, player))
            {
                e.getBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player player = (Player)e.getEntity();

            if(this.plugin.getArenas().isPlayerJoined(player))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        if(this.plugin.getArenas().isPlayerJoined(player))
        {
            player.performCommand("dh leave");
        }
    }

    public boolean GetCancelState(Material mat, Arena ar, Player player)
    {
        if(mat == Material.DIAMOND_ORE)
        {
            ar.ScorePoint(player);
            return false;
        }
        else if(mat == Material.COAL_ORE)
        {
            ar.Blind(player);
            return false;
        }
        else if(mat == Material.EMERALD_ORE)
        {
            ar.Confuse(player);
        }
        else if(mat == Material.IRON_ORE)
        {
            ar.FastMining(player);
        }
        else if(mat == Material.GOLD_ORE)
        {
            ar.Speed(player);
        }
        else if(mat == Material.REDSTONE_ORE)
        {
            ar.RemoveEffects(player);
        }
        else if(mat == Material.LAPIS_ORE)
        {
            ar.Slow(player);
        }
        else
        {
            return true;
        }

        return true;
    }
}