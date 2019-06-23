package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;
import ww.bewhaled.diggyhole.arena.Arena;

public class LeaveCommand implements ICommand
{
    private Main plugin;

    public LeaveCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public void execute(Player player, String[] args)
    {
        Arena ar = this.plugin.getArenas().FindArena(player);
        ar.PlayerLeft(player);
    }

    @Override
    public boolean hasPermission(Player player)
    {
        return player.hasPermission(this.getPermission());
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave a game.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.player";
    }
}
