package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.*;
import ww.bewhaled.diggyhole.Main;

import java.util.ArrayList;

public class CommandHandler implements CommandExecutor
{
    Main plugin;
    ArrayList<ICommand> commands;

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

            for (ICommand comm : this.commands)
            {
                if(comm.getName().toLowerCase().equals(args[0].toLowerCase()))
                {
                    if(comm.hasPermission(player)) comm.execute(player,args);
                    else player.sendMessage(ChatColor.GREEN + "[Diggy Hole] "
                            + ChatColor.RED + "You do not have access to that command!");
                }
            }

        }

        return true;
    }

    public void InitCommands()
    {
        this.commands.add(new HelpCommand(this.plugin,this.commands));
        this.commands.add(new JoinCommand(this.plugin));
        this.commands.add(new LeaveCommand(this.plugin));
        this.commands.add(new CreateCommand(this.plugin));
        this.commands.add(new SetCommand(this.plugin));
        this.commands.add(new ReloadCommand(this.plugin));
    }

}
