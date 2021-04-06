package me.shanodis.TreeCapitatorPlugin;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PluginManager pluginManager = getServer().getPluginManager();
		PluginListener pluginListener = new PluginListener(JavaPlugin.getPlugin(Main.class));
		
		pluginManager.registerEvents(pluginListener, this);
		getLogger().info("Item Drop Plugin has been loaded!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Item Drop Plugin has been removed!");
	}
}
