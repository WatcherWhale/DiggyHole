package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.*;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandHandler implements TabExecutor
{
    private Main plugin;
    private ArrayList<ICommand> commands;

    public CommandHandler(Main pl)
    {
        this.plugin = pl;
        this.commands = new ArrayList<>();

        InitCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            
            ICommand comm = getCommand(args[0]);
            
            if(comm != null)
            {
                if(comm.getName().toLowerCase().equals(args[0].toLowerCase()))
                {
                    if(comm.hasPermission(player)) comm.execute(player,args);
                    else player.sendMessage(ChatColor.GREEN + "[Diggy Hole] "
                            + ChatColor.RED + "You do not have access to that command!");
                }
            }
            else
            {
                player.sendMessage(ChatColor.GREEN + "[Diggy Hole] "
                        + ChatColor.RED + "Command does not exist type /dh help for a list of commands!");
            }

        }

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        ArrayList<String> completions = new ArrayList<>();
        int argIndex = args.length - 1;
        
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
    
            if (args.length <= 1)
            {
                String arg;
                if (args.length <= 0) arg = "";
                else arg = args[0];
    
                for (ICommand comm : this.commands)
                {
                    if (comm.hasPermission(player) && comm.getName().contains(arg))
                    {
                        completions.add(comm.getName());
                    }
                }
            }
            else
            {
                ICommand comm = getCommand(args[0]);
                
                if(comm != null && comm.hasPermission(player))
                {
                    completions = comm.getCompletions(argIndex);
                }
            }
        }
        
        return completions;
    }
    
    private void InitCommands()
    {
        this.commands.add(new HelpCommand(this.plugin,this.commands));
        this.commands.add(new JoinCommand(this.plugin));
        this.commands.add(new LeaveCommand(this.plugin));
        this.commands.add(new CreateCommand(this.plugin));
        this.commands.add(new SetCommand(this.plugin));
        this.commands.add(new RegionCommand(this.plugin));
        this.commands.add(new ReloadCommand(this.plugin));
    }
    
    private ICommand getCommand(String name)
    {
        for (ICommand comm : this.commands)
        {
            if(comm.getName().equalsIgnoreCase(name))
            {
                return comm;
            }
        }
        
        return null;
    }
    
}
