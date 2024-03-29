package ww.bewhaled.diggyhole.commands;

import ww.bewhaled.diggyhole.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ICommand
{
    Main plugin = null;

    void execute(Player player, String[] args);

    boolean hasPermission(Player player);
    
    ArrayList<String> getCompletions(int arg);

    String getName();
    String getDescription();
    String getPermission();
}
