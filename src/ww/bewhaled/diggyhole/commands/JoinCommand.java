package ww.bewhaled.diggyhole.commands;

import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class JoinCommand implements ICommand
{
    private Main plugin;

    public JoinCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public boolean execute(Player player, String[] args)
    {
        Arena ar = this.plugin.getArenas().FindArena(args[1]);
        ar.PlayerJoined(player);

        return true;
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join an arena.";
    }
}
