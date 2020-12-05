package uk.co.voxelfox.itemframeutils;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import de.tr7zw.nbtapi.NBTEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ItemFrameUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setframevisible")) {
            if(!(args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false"))) {
                return false;
            }
            int distance, limit;
            try {
                distance = Integer.parseInt(args[1]);
                assert distance <= 100;
            }
            catch(NumberFormatException e) {
                return false;
            }
            catch(AssertionError e) {
                sender.sendMessage("You can only have a distance of up to 100 blocks.");
                return true;
            }
            try {
                limit = Integer.parseInt(args[2]);
            }
            catch(IndexOutOfBoundsException e) {
                limit = -1;
            }
            catch(NumberFormatException e) {
                return false;
            }
            setItemFrameInvisible(sender, distance, args[0].equalsIgnoreCase("false"), limit);
            return true;
        }

        return false;
    }

    private void setItemFrameInvisible(CommandSender sender, int distance, boolean invisible, int limit) {
        Server s = sender.getServer();
        Player p = s.getPlayer(sender.getName());
        Location l = p.getEyeLocation();
        List<Entity> itemFrames = new ArrayList<>(l.getNearbyEntities(distance, distance, distance));
        itemFrames.sort((o1, o2) -> o1.getLocation().distance(l) < o2.getLocation().distance(l) ? -1 : 1);
        int counter = 0;
        for(Entity e: itemFrames) {
            if(e.getType() == EntityType.ITEM_FRAME) {
                NBTEntity entityData = new NBTEntity(e);
                if(entityData.getBoolean("Invisible") == !invisible) {
                    entityData.setBoolean("Invisible", invisible);
                    counter++;
                    if(limit > 0 && counter >= limit) {
                        break;
                    }
                }
            }
        }
        sender.sendMessage(String.format("Changed the data of %d item frames.", counter));
    }
}
