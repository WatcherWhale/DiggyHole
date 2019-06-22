package ww.bewhaled.diggyhole.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class CreateCommand implements ICommand
{
    private Main plugin;

    public CreateCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public boolean execute(Player player, String[] args)
    {
        LocalSession ses = this.plugin.getWorldEdit().getSession(player);

        try
        {
            Region region = ses.getSelection(ses.getSelectionWorld());

            Arena ar = new Arena(this.plugin,args[1],new ww.bewhaled.diggyhole.arena.Region(region,player.getWorld()));

            this.plugin.getArenas().AddArena(ar);
        }
        catch (IncompleteRegionException e)
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole] " + ChatColor.DARK_RED + e.getMessage());
        }

        return true;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a Diggy Hole Arena.";
    }
}
