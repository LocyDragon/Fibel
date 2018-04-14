package com.locydragon.fibel.util.configuration;

import com.locydragon.fibel.Fibel;
import org.bukkit.configuration.file.FileConfiguration;

public class FibelConfig {
	public static FileConfiguration configuration; //这个变量的赋值在主类中实现.

	public static void reloadConfig() {
		Fibel.reloadSettings();
	}

	public static Object get(String path) {
		return configuration.get(path);
	}
}
