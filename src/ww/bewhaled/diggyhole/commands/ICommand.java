package ww.bewhaled.diggyhole.commands;

import ww.bewhaled.diggyhole.Main;
import org.bukkit.entity.Player;

public interface ICommand
{
    Main plugin = null;

    void execute(Player player, String[] args);

    boolean hasPermission(Player player);

    String getName();
    String getDescription();
    String getPermission();
}
