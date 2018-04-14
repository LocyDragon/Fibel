package com.locydragon.fibel;

import com.locydragon.fibel.listeners.Main;
import com.locydragon.fibel.listeners.setup.PluginSetUpListener;
import com.locydragon.fibel.util.configuration.FibelConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Fibel extends JavaPlugin {
	public static final String website = "";
	public static Fibel fibel = null;
	public static PluginManager pluginManager;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		FibelConfig.configuration = getConfig();
		fibel = this;
		pluginManager = Bukkit.getPluginManager();
		registerEvents();
	}

	public static void reloadSettings() {
		fibel.saveConfig();
		fibel.reloadConfig();
		FibelConfig.configuration = fibel.getConfig();
	}

	private void registerEvents() {
		registerEvent(new PluginSetUpListener());
		registerEvent(new Main());
	}

	public static void registerEvent(Listener which) {
		pluginManager.registerEvents(which, fibel);
	}
}

