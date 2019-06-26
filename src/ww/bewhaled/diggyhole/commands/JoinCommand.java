package ww.bewhaled.diggyhole.commands;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

import java.util.ArrayList;

public class JoinCommand implements ICommand
{
    private Main plugin;

    public JoinCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public void execute(Player player, String[] args)
    {
        if(args.length < 2)
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole] "
                                + ChatColor.RED + "Arena name cannot be empty!");
            return;
        }

        Arena ar = this.plugin.getArenas().FindArena(args[1]);

        if(ar == null)
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole] "
                    + ChatColor.RED + "Arena does not exist!");
            return;
        }

        ar.PlayerJoined(player);
    }
    
    public ArrayList<String> getCompletions(int arg)
    {
        if(arg == 1)
            return new ArrayList<>(this.plugin.getArenas().getArenaNames());
        else
            return new ArrayList<>();
    }
    
    @Override
    public boolean hasPermission(Player player)
    {
        return player.hasPermission(this.getPermission());
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join an arena.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.player";
    }
}
