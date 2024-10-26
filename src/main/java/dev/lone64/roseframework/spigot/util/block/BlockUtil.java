package dev.lone64.roseframework.spigot.util.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {

    public static Location getFacingLocation(Block block, Location loc) {
        Chest chest = (Chest) block.getBlockData();
        BlockFace face = getFacing(block);
        if (chest.getType() == Chest.Type.LEFT) {
            if (face == BlockFace.NORTH) {
                loc.add(1, 0, 0);
            } else if (face == BlockFace.SOUTH) {
                loc.subtract(1, 0, 0);
            } else if (face == BlockFace.WEST) {
                loc.subtract(0, 0, 1);
            } else if (face == BlockFace.EAST) {
                loc.add(0, 0, 1);
            }
        } else if (chest.getType() == Chest.Type.RIGHT) {
            if (face == BlockFace.NORTH) {
                loc.subtract(1, 0, 0);
            } else if (face == BlockFace.SOUTH) {
                loc.add(1, 0, 0);
            } else if (face == BlockFace.WEST) {
                loc.add(0, 0, 1);
            } else if (face == BlockFace.EAST) {
                loc.subtract(0, 0, 1);
            }
        }
        return loc;
    }

    public static BlockFace getFacing(Block block) {
        BlockData data = block.getBlockData();
        BlockFace f = null;
        if (data instanceof Directional && data instanceof Waterlogged && ((Waterlogged) data).isWaterlogged()) {
            String str = data.toString();
            if (str.contains("facing=west")) {
                f = BlockFace.WEST;
            } else if (str.contains("facing=east")) {
                f = BlockFace.EAST;
            } else if (str.contains("facing=south")) {
                f = BlockFace.SOUTH;
            } else if (str.contains("facing=north")) {
                f = BlockFace.NORTH;
            }
        } else if (data instanceof Directional) {
            f = ((Directional) data).getFacing();
        }
        return f;
    }

    public static List<Block> getNearbyBlocksXZ(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                World world = location.getWorld();
                if (world == null) continue;

                blocks.add(world.getBlockAt(x, location.getBlockY(), z));
            }
        }
        return blocks;
    }

    public static List<Block> getNearbyBlocksXYZ(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    World world = location.getWorld();
                    if (world == null) continue;

                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static List<Block> getBlocksBetweenPoints(Location l1, Location l2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (Math.max(l1.getBlockX(), l2.getBlockX()));
        int bottomBlockX = (Math.min(l1.getBlockX(), l2.getBlockX()));

        int topBlockY = (Math.max(l1.getBlockY(), l2.getBlockY()));
        int bottomBlockY = (Math.min(l1.getBlockY(), l2.getBlockY()));

        int topBlockZ = (Math.max(l1.getBlockZ(), l2.getBlockZ()));
        int bottomBlockZ = (Math.min(l1.getBlockZ(), l2.getBlockZ()));

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                    World world = l1.getWorld();
                    if (world == null) continue;

                    Block block = world.getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public static List<Block> getBlocksBetween(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<>();
        for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc1.getBlockX(), loc2.getBlockX()); x++) {
            for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc1.getBlockZ(), loc2.getBlockZ()); z++) {
                for (int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y <= Math.max(loc1.getBlockY(), loc2.getBlockY()); y++) {
                    blocks.add(loc1.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static void drawRectangle(Location corner, Location corner2, Material mat) {
        int maxX = Math.max(corner.getBlockX(), corner2.getBlockX());
        int maxZ = Math.max(corner.getBlockZ(), corner2.getBlockZ());
        int maxY = Math.max(corner.getBlockY(), corner2.getBlockY());

        int minX = Math.min(corner.getBlockX(), corner2.getBlockX());
        int minZ = Math.min(corner.getBlockZ(), corner2.getBlockZ());
        int minY = Math.min(corner.getBlockY(), corner2.getBlockY());

        for(int x = minX; x <= maxX; x++){
            for(int z = minZ; z <= maxZ; z++){
                for(int y = minY; y <= maxY; y++){
                    corner.getWorld().getBlockAt(x, y, z).setType(mat);
                }
            }
        }
    }

    public static void drawRectangle(Player player, Location corner, Location corner2, Material mat) {
        World world = player.getWorld();

        int maxX = Math.max(corner.getBlockX(), corner2.getBlockX());
        int maxZ = Math.max(corner.getBlockZ(), corner2.getBlockZ());
        int maxY = Math.max(corner.getBlockY(), corner2.getBlockY());

        int minX = Math.min(corner.getBlockX(), corner2.getBlockX());
        int minZ = Math.min(corner.getBlockZ(), corner2.getBlockZ());
        int minY = Math.min(corner.getBlockY(), corner2.getBlockY());

        for(int x = minX; x <= maxX; x++){
            for(int z = minZ; z <= maxZ; z++){
                for(int y = minY; y <= maxY; y++){
                    player.sendBlockChange(new Location(world, x, y, z).clone(), mat.createBlockData());
                }
            }
        }
    }

}