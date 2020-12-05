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
        if(command.getName().equalsIgnoreCase("setframeinvisible")) {
            int distance;
            try {
                distance = Integer.parseInt(args[0]);
                assert distance <= 100;
            }
            catch(NumberFormatException e) {
                return false;
            }
            catch(AssertionError e) {
                sender.sendMessage("You can only have a distance of up to 100 blocks.");
                return true;
            }
            setItemFrameInvisible(sender, true);
            return true;
        }
        else if(command.getName().equalsIgnoreCase("setframevisible")) {
            int distance;
            try {
                distance = Integer.parseInt(args[0]);
                assert distance <= 100;
            }
            catch(NumberFormatException e) {
                return false;
            }
            catch(AssertionError e) {
                sender.sendMessage("You can only have a distance of up to 100 blocks.");
                return true;
            }
            setItemFrameInvisible(sender, false);
            return true;
        }

        return false;
    }

    private void setItemFrameInvisible(CommandSender sender, boolean invisible) {
        Server s = sender.getServer();
        Player p = s.getPlayer(sender.getName());
        Location l = p.getLocation();
        List<Entity> itemFrames = new ArrayList<>(l.getNearbyEntities(l.getX(), l.getY(), l.getZ()));
        int counter = 0;
        for(Entity e: itemFrames) {
            if(e.getType() == EntityType.ITEM_FRAME) {
                NBTEntity entityData = new NBTEntity(e);
                entityData.setBoolean("Invisible", invisible);
                counter++;
            }
        }
        sender.sendMessage(String.format("Changed the data of %d item frames.", counter));
    }
}
