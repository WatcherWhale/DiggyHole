package ww.bewhaled.diggyhole.arena;

import org.bukkit.World;

public class Region
{
    private int[] max,min;
    private int height, width, length;
    private World world;

    public Region(com.sk89q.worldedit.regions.Region region, World world)
    {
        this.length = region.getLength();
        this.height = region.getHeight();
        this.width = region.getWidth();
        this.max = new int[]{region.getMaximumPoint().getBlockX(),
                region.getMaximumPoint().getBlockY(),region.getMaximumPoint().getBlockZ()};
        this.min = new int[]{region.getMinimumPoint().getBlockX(),
                region.getMinimumPoint().getBlockY(),region.getMinimumPoint().getBlockZ()};

        this.world =  world;
    }

    public Region(World world, int[] min, int[] max, int width, int height, int length)
    {
        this.length = length;
        this.height = height;
        this.width = width;
        this.max = max;
        this.min = min;
        this.world = world;
    }

    public Region(World world, int[] min, int[] max)
    {
        this.length = Math.abs(max[2] - min[2]);
        this.height = Math.abs(max[1]- min[1]);
        this.width = Math.abs(max[0] - min[0]);
        this.max = max;
        this.min = min;
        this.world = world;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(int[] max) {
        this.max = max;
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int[] min) {
        this.min = min;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getVolume()
    {
        return this.width * this.height * this.length;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
