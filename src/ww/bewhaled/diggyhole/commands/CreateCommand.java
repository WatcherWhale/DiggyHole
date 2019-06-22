package ww.bewhaled.diggyhole.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;

public class CreateCommand implements ICommand
{
    Main plugin;

    final String name = "create";
    final String description = "Create a Diggy Hole Arena";

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

            //TODO: Save region
        }
        catch (IncompleteRegionException e)
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole] " + ChatColor.DARK_RED + e.getMessage());
        }

        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
