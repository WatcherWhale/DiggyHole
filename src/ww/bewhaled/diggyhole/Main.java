package ww.bewhaled.diggyhole;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    WorldEditPlugin we;
    ConfigHandler config;

    @Override
    public void onEnable()
    {
        //Load WorldEdit
        this.we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        //Load all configs
        this.config = new ConfigHandler(this);

        //Register the commands
        this.getCommand("dh").setExecutor(new CommandHandler(this));
        this.getCommand("diggyhole").setExecutor(new CommandHandler(this));

        //Register the events
        Bukkit.getPluginManager().registerEvents(new EventHandler(this),this);
    }

    @Override
    public void onDisable()
    {

    }

    public WorldEditPlugin getWorldEdit()
    {
        return this.we;
    }
}
