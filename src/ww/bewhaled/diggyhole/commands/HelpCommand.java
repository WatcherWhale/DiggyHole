package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;

public class HelpCommand implements ICommand
{
    Main plugin;
    ArrayList<ICommand> commands;

    final String name = "Help";
    final String description = "Get a list of commands.";

    public HelpCommand(Main pl, ArrayList<ICommand> commands)
    {
        this.plugin = pl;
        this.commands = commands;
    }

    @Override
    public boolean execute(Player player, String[] args)
    {
        player.sendMessage("Diggy Hole");
        for(ICommand command : commands)
        {
            player.sendMessage(ChatColor.GREEN + "/dh" + command.getName()
                    + ChatColor.WHITE + ": " + command.getDescription());
        }

        return true;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
