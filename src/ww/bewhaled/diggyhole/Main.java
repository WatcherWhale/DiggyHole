package ww.bewhaled.diggyhole;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ww.bewhaled.diggyhole.arena.Arena;
import ww.bewhaled.diggyhole.arena.ArenaHandler;

public class Main extends JavaPlugin
{
    private WorldEditPlugin we;
    private ConfigHandler config;

    private ArenaHandler arenas;

    @Override
    public void onEnable()
    {
        //Load WorldEdit
        this.we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        //Load all configs
        this.config = new ConfigHandler(this);
        YamlConfiguration[] arConfigs = this.config.GetArenaConfigs();

        //Load Arenas
        this.arenas = new ArenaHandler(this);
        for(YamlConfiguration config : arConfigs)
        {
            Arena ar = new Arena(this,config);
            this.arenas.AddArena(ar);
        }

        //Register the commands
        this.getCommand("dh").setExecutor(new CommandHandler(this));
        this.getCommand("diggyhole").setExecutor(new CommandHandler(this));

        //Register the events
        Bukkit.getPluginManager().registerEvents(new EventsHandler(this),this);
    }

    @Override
    public void onDisable()
    {
        //Save all arenas just in case
        for(Arena ar : this.arenas.getArenas())
        {
            this.config.SaveArena(ar);
        }
    }

    public WorldEditPlugin getWorldEdit()
    {
        return this.we;
    }

    public ConfigHandler getConfigHandler()
    {
        return this.config;
    }

    public ArenaHandler getArenas()
    {
        return this.arenas;
    }
}
