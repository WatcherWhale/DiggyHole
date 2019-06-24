package ww.bewhaled.diggyhole.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ww.bewhaled.diggyhole.Main;

public class ReloadCommand implements ICommand
{
    private Main plugin;

    public ReloadCommand(Main pl)
    {
        this.plugin = pl;
    }

    @Override
    public void execute(Player player, String[] args)
    {
        this.plugin.ReloadPlugin();
        player.sendMessage(ChatColor.GREEN + "[Diggy Hole]" +
                ChatColor.WHITE + " Successfully reloaded the configuration files!");
    }

    @Override
    public boolean hasPermission(Player player)
    {
        return player.hasPermission(this.getPermission());
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload all the configs of Diggy Hole.";
    }

    @Override
    public String getPermission() {
        return "diggyhole.admin";
    }
}
