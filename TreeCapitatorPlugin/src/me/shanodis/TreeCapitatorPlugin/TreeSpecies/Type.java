package me.shanodis.TreeCapitatorPlugin.TreeSpecies;

import java.util.ArrayList;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import me.shanodis.TreeCapitatorPlugin.Vector3i;

public class Type extends Tree {
	
	// Fields
	private enum TreeSize {
		REGULAR, BIG;
	}

	private List<Block> treeBlocks;
	private String destroyedLogName;
	private Location destroyedLogLocation;
	private Vector3i regularSize, bigSize;
	
	// Constructor
	public Type(Block destroyedLog, Vector3i regularSize, Vector3i bigSize) {
		destroyedLogName = destroyedLog.getType().name();
		name = TreeSize.REGULAR.toString() + " " + destroyedLogName.replace("_LOG", "");
		
		destroyedLogLocation = destroyedLog.getLocation();
		
		treeBlocks = new ArrayList<>();
		this.regularSize = regularSize;
		this.bigSize = bigSize;
	}
	
	// Private Methods
	private boolean isRootDestroyed() {
		Location location = destroyedLogLocation.clone().add(0, -1, 0);
		Block ground = location.getBlock();
		String blockType = ground.getType().name();
		
		return (boolean) (blockType.equals(destroyedLogName) ? false : true);
	}
	
	private void specifyTreeSize() {
		Block nextBlock;
		
		for(int y = 0; y < bigSize.y; y++)
			for(int x = - 1; x < 2; x++)
				for(int z = -1; z < 2; z++) {
					
					Location trunkLoc = destroyedLogLocation.clone().add(x, y, z);
					nextBlock = trunkLoc.getBlock();
					String type = nextBlock.getType().name();
					
					if( (type.equals(destroyedLogName)) && (x != 0 || z != 0 ) ) {
						name = TreeSize.BIG.toString() + " " + name;
						return;
					}
				}
	}
	
	private void addBlocks(Vector3i treeSize) {
		Block nextBlock;
		String leavesType = destroyedLogName.replace("_LOG", "") + "_LEAVES";
		
		for(int y = 0; y < treeSize.y; y++)
			for(int x = -treeSize.x; x <= treeSize.x; x++)
				for(int z = -treeSize.z; z <= treeSize.z; z++) {		
					Location trunkLoc = destroyedLogLocation.clone().add(x, y, z);
					nextBlock = trunkLoc.getBlock();
					String type = nextBlock.getType().name();
					
					if(type.equals(destroyedLogName) || type.equals(leavesType))
						treeBlocks.add(nextBlock);
				}
	}
	
	// Public Methods
	public void addTreeBlocks() {
		
		if(!isRootDestroyed())
			return;

		if(destroyedLogName.startsWith("OAK") || destroyedLogName.startsWith("JUNGLE"))
			specifyTreeSize();
		
		if(name.startsWith(TreeSize.REGULAR.toString()))
			addBlocks(regularSize);
		else
			addBlocks(bigSize);
	}
	
	public void destroyBlocks() {
		for (Block block : treeBlocks)
			block.breakNaturally();
	}
	
	public int getSize() {
		return treeBlocks.size();
	}
}
