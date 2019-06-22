package ww.bewhaled.diggyhole.commands;

import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class JoinCommand implements ICommand
{
    Main plugin;

    final String name = "join";
    final String description = "Join an arena.";

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
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
