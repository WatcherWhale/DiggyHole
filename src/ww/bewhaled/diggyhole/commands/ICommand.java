package ww.bewhaled.diggyhole.commands;

import ww.bewhaled.diggyhole.Main;
import org.bukkit.entity.Player;

public interface ICommand
{
    Main plugin = null;

    String name = "";
    String description = "";

    boolean execute(Player player, String[] args);

    String getName();
    String getDescription();
}
