package ww.bewhaled.diggyhole.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;
import ww.bewhaled.diggyhole.arena.Region;

public class RegionCommand implements ICommand
{
    private Main plugin;

    public RegionCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public void execute(Player player, String[] args)
    {
        Arena ar = this.plugin.getArenas().FindArena(args[1]);
        if(ar != null)
        {
            try
            {
                //Region reg = new Region()
                LocalSession ses = this.plugin.getWorldEdit().getSession(player);
                World world = player.getWorld();
                com.sk89q.worldedit.regions.Region sel = ses.getSelection(ses.getSelectionWorld());

                Region reg = new Region(sel,world);

                ar.setRegion(reg);

                this.plugin.getConfigHandler().SaveArena(ar);
            }
            catch (IncompleteRegionException e)
            {
                e.printStackTrace();
                player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                        ChatColor.RED + " Region is incomplete!");
            }
        }
        else
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                    ChatColor.RED + " The arena " + args[1] + " does not exist!");
        }
    }

    @Override
    public boolean hasPermission(Player player)
    {
        return player.hasPermission(this.getPermission());
    }

    @Override
    public String getName() {
        return "region";
    }

    @Override
    public String getDescription() {
        return "Change the block region.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.admin";
    }
}
