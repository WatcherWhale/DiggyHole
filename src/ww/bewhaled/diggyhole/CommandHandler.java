package ww.bewhaled.diggyhole;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.entity.Player;
import org.bukkit.command.*;

public class CommandHandler implements CommandExecutor
{
    WorldEditPlugin we;

    public CommandHandler(WorldEditPlugin we)
    {
        this.we = we;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if(sender instanceof Player)
        {
            Player player = (Player)sender;




            return true;
        }

        return false;
    }
}
