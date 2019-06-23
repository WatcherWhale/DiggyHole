package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;

public class HelpCommand implements ICommand
{
    Main plugin;
    ArrayList<ICommand> commands;

    public HelpCommand(Main pl, ArrayList<ICommand> commands)
    {
        this.plugin = pl;
        this.commands = commands;
    }

    @Override
    public void execute(Player player, String[] args)
    {
        player.sendMessage("Diggy Hole");
        for(ICommand command : commands)
        {
            if(command.hasPermission(player))
            {
                player.sendMessage(ChatColor.GREEN + "/dh " + command.getName()
                        + ChatColor.WHITE + " " + command.getDescription());
            }
        }
    }

    @Override
    public boolean hasPermission(Player player)
    {
        return player.hasPermission(this.getPermission());
    }

    public String getName()
    {
        return "help";
    }

    public String getDescription()
    {
        return "Get a list of commands.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.player";
    }
}
