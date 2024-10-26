package dev.lone64.roseframework.spigot.util.location;

import dev.lone64.roseframework.spigot.util.item.MaterialUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cuboid {

    private final String worldName;
    private final int minX, minY, minZ;
    private final int maxX, maxY, maxZ;
    private final Location pos1, pos2;

    public Cuboid(Location pos1, Location pos2) {
        if (pos1.getWorld() == null || pos2.getWorld() == null)
            throw new IllegalArgumentException("Locations must be on the not found world");
        if (!pos1.getWorld().equals(pos2.getWorld()))
            throw new IllegalArgumentException("Locations must be on the same world");

        this.pos1 = pos1;
        this.pos2 = pos2;

        this.worldName = pos1.getWorld().getName();
        this.minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        this.minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        this.minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        this.maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        this.maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        this.maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public Cuboid(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this(new Location(world, minX, minY, minZ), new Location(world, maxX, maxY, maxZ));
    }

    public Cuboid(String worldName, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this(Bukkit.getWorld(worldName), minX, minY, minZ, maxX, maxY, maxZ);
    }

    public World getWorld() {
        World world = Bukkit.getWorld(this.worldName);
        if (world == null)
            throw new IllegalStateException("World '" + this.worldName + "' is not found");
        return world;
    }

    public Location getLowerPos() {
        return new Location(getWorld(), this.minX, this.minY, this.minZ);
    }

    public Location getUpperPos() {
        return new Location(getWorld(), this.maxX, this.maxY, this.maxZ);
    }

    public Location getCenterPos() {
        return new Location(this.getWorld(), (double) (this.getMinX() + this.getMaxX()) / 2, (double) (this.getMinY() + this.getMaxY()) / 2, (double) (this.getMinZ() + this.getMaxZ()) / 2);
    }

    public int getLowerBlockY() {
        if (getLowerPos() == null || getUpperPos() == null)
            throw new IllegalArgumentException("Location cannot be null");
        int blockY1 = getLowerPos().getBlockY();
        int blockY2 = getUpperPos().getBlockY();
        return Math.min(blockY1, blockY2);
    }

    public int getUpperBlockY() {
        if (getLowerPos() == null || getUpperPos() == null)
            throw new IllegalArgumentException("Location cannot be null");
        int blockY1 = getLowerPos().getBlockY();
        int blockY2 = getUpperPos().getBlockY();
        return Math.max(blockY1, blockY2);
    }

    public Location getHighestLocation(Location location) {
        if (location.getWorld() == null) return location;

        final int x = location.getBlockX();
        final int z = location.getBlockZ();

        final boolean sameHeight = this.getLowerPos().getY() == this.getUpperPos().getY();
        int y = (int) Math.max(this.getLowerPos().getY(), this.getUpperPos().getY()) - 1;
        if (sameHeight)
            y = y + 1;

        highestAvailableLookup: {
            for (; sameHeight ? y < location.getWorld().getMaxHeight() : y > 1; y = y + (sameHeight ? 1 : -1)) {
                final Block block = location.getWorld().getBlockAt(x, y, z);
                if (sameHeight) {
                    if (MaterialUtil.isAir(block)) {
                        location.setY(y - 1);
                        break highestAvailableLookup;
                    }
                } else {
                    if (!MaterialUtil.isAir(block)) {
                        location.setY(y);
                        break highestAvailableLookup;
                    }
                }
            }
            location.setY(y);
        }
        return location.add(0, 1, 0);
    }

    public Block[] corners() {
        Block[] corners = new Block[8];
        World w = this.getWorld();
        corners[0] = w.getBlockAt(this.minX, this.minY, this.minZ);
        corners[1] = w.getBlockAt(this.minX, this.minY, this.maxZ);
        corners[2] = w.getBlockAt(this.minX, this.maxY, this.minZ);
        corners[3] = w.getBlockAt(this.minX, this.maxY, this.maxZ);
        corners[4] = w.getBlockAt(this.maxX, this.minY, this.minZ);
        corners[5] = w.getBlockAt(this.maxX, this.minY, this.maxZ);
        corners[6] = w.getBlockAt(this.maxX, this.maxY, this.minZ);
        corners[7] = w.getBlockAt(this.maxX, this.maxY, this.maxZ);
        return corners;
    }

    public Location[] getCorrectedPoints() {
        return new Location[]{ getLowerPos(), getUpperPos() };
    }

    public List<Entity> getEntities() {
        final List<Entity> found = new ArrayList<>();
        final Location[] centered = getCorrectedPoints();
        if (centered == null)
            return found;

        final Location primary = centered[0];
        final Location secondary = centered[1];

        final int xMin = primary.getBlockX() >> 4;
        final int xMax = secondary.getBlockX() >> 4;
        final int zMin = primary.getBlockZ() >> 4;
        final int zMax = secondary.getBlockZ() >> 4;
        for (int cx = xMin; cx <= xMax; ++cx)
            for (int cz = zMin; cz <= zMax; ++cz)
                for (final Entity entity : getWorld().getChunkAt(cx, cz).getEntities())
                    if (entity.isValid() && contains(entity.getLocation()))
                        found.add(entity);
        return found;
    }

    public List<Chunk> getChunks() {
        List<Chunk> res = new ArrayList<>();
        World w = getWorld();
        int minX = getMinX() & ~0xf;
        int maxX = getMaxX() & ~0xf;
        int minZ = getMinZ() & ~0xf;
        int maxZ = getMaxZ() & ~0xf;
        for (int x = minX; x <= maxX; x += 16) {
            for (int z = minZ; z <= maxZ; z += 16) {
                res.add(w.getChunkAt(x >> 4, z >> 4));
            }
        }
        return res;
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ;
    }

    public boolean contains(Location location) {
        if (location.getWorld() == null)
            return false;
        if (!this.worldName.equals(location.getWorld().getName()))
            return false;
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean withInXZ(int x, int z) {
        return x >= this.minX && x <= this.maxX && z >= this.minZ && z <= this.maxZ;
    }

    public boolean withInXZ(Location location) {
        if (location.getWorld() == null)
            return false;
        if (!this.worldName.equals(location.getWorld().getName()))
            return false;
        return withInXZ(location.getBlockX(), location.getBlockZ());
    }

    public boolean contains(Block block) {
        return contains(block.getLocation());
    }

}