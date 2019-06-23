package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class SetCommand implements ICommand
{
    private Main plugin;

    public SetCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public void execute(Player player, String[] args)
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

            this.plugin.getConfigHandler().SaveArena(ar);
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
        return "set";
    }

    @Override
    public String getDescription() {
        return "Set a special location.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.admin";
    }
}
