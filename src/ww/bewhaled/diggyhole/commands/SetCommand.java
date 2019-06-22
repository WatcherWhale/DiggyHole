package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class SetCommand implements ICommand
{
    private Main plugin;

    private final String name = "set";
    private final String description = "Set a special location.";

    public SetCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public boolean execute(Player player, String[] args)
    {
        Arena ar = this.plugin.getArenas().FindArena(args[1]);
        if(ar != null)
        {
            if(args[2].toLowerCase().equals("lobby"))
            {
                ar.setLobby(player.getLocation());

                player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                        ChatColor.WHITE + " Lobby location changed!");

            }
            else if(args[2].toLowerCase().equals("arena"))
            {
                ar.setArena(player.getLocation());

                player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                        ChatColor.WHITE + " Arena location changed!");
            }
            else
            {
                player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                        ChatColor.RED + " This location is not recognized!");
            }
        }
        else
        {
            player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                    ChatColor.RED + " The arena " + args[1] + " doesn't exist!");
        }

        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
