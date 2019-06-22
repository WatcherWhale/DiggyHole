package ww.bewhaled.diggyhole;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EventHandler implements Listener
{
    private Main plugin;

    public EventHandler(Main pl)
    {
        this.plugin = pl;
    }

    public void OnBlockBreak(BlockBreakEvent e)
    {

    }
}
