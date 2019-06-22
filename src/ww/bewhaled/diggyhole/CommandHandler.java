package ww.bewhaled.diggyhole;

import org.bukkit.entity.Player;
import org.bukkit.command.*;
import ww.bewhaled.diggyhole.commands.*;

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

            for (ICommand comm : commands)
            {
                if(comm.getName().toLowerCase() == args[0].toLowerCase()) return comm.execute(player,args);
            }


            return this.commands.get(0).execute(player,args);
        }

        return false;
    }

    public void InitCommands()
    {
        this.commands.add(new HelpCommand(this.plugin,this.commands));
        this.commands.add(new CreateCommand(this.plugin));
        this.commands.add(new JoinCommand(this.plugin));
        this.commands.add(new SetCommand(this.plugin));
    }

}
