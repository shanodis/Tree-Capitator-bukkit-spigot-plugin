package me.shanodis.TreeCapitatorPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import me.shanodis.TreeCapitatorPlugin.TreeSpecies.Type;

public class PluginListener implements Listener {
	
	private Plugin plugin;
	
	public PluginListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		// Variables
		Block block = event.getBlock();
		Player player = event.getPlayer();
		
		if(block.hasMetadata("PLACED"))
			return;
		
		Vector3i regularSize = null, bigSize = null;
		Material treeMaterial = block.getType();
		
		switch(treeMaterial) {
		
		case OAK_LOG:
			regularSize = new Vector3i(2, 7, 2);
			bigSize = new Vector3i(4, 19, 4);
			break;
			
		case BIRCH_LOG:
			regularSize = new Vector3i(2, 8, 2);
			break;
		
		case SPRUCE_LOG:
			regularSize = new Vector3i(3, 10, 3);
			break;
			
		case JUNGLE_LOG:
			regularSize = new Vector3i(2, 13, 2);
			bigSize = new Vector3i(6, 32, 6);
			break;
			
		case ACACIA_LOG:
			regularSize = new Vector3i(4, 10, 4);
			break;
			
		case DARK_OAK_LOG:
			regularSize = new Vector3i(5, 11, 5);
			break;
			
		default:
			break;
		}
		
		Type treeType = new Type(block, regularSize, bigSize);
		treeType.addTreeBlocks();
		treeType.destroyBlocks();
		player.sendMessage(ChatColor.DARK_GREEN  + "(Tree Capitator): " 
		+ ChatColor.ITALIC.toString() + "Size of the tree: " + treeType.getSize() + ", name: " + treeType.getName());
	}
	
	@EventHandler
	public void onBlockPlaced(BlockPlaceEvent event) {
		event.getBlock().setMetadata("PLACED", new FixedMetadataValue(plugin, "Block has been placed by a player"));
	}
}
