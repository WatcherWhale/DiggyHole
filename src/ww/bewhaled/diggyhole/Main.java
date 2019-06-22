package ww.bewhaled.diggyhole;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    WorldEditPlugin we;

    @Override
    public void onEnable()
    {
        this.we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        this.getCommand("dh").setExecutor(new CommandHandler(this));
        this.getCommand("diggyhole").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable()
    {

    }

    public WorldEditPlugin getWorldEdit()
    {
        return  this.we;
    }
}
