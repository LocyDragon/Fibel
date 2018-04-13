package com.locydragon.fibel.file.readme;

import com.locydragon.fibel.Fibel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReadMe {
	public static boolean checkAndCreate() {
		File readMeFile = new File(".//plugins//Fibel//ReadMe.txt");
		if (readMeFile.exists()) {
			return true;
		}
		readMeFile.getParentFile().mkdirs();
		try {
			readMeFile.createNewFile();
			FileWriter writer = new FileWriter(readMeFile);
			StringBuilder readMeInfo = new StringBuilder();
			readMeInfo.append("=====================\n");
			readMeInfo.append("Welcome to use Fibel!\n");
			readMeInfo.append("This is just a free plugin.\n");
			readMeInfo.append("Why do we develop this plugin?\n");
			readMeInfo.append("Because we enjoy doing pro bono work!\n");
			readMeInfo.append("It is not our job, but we enjoy it!\n");
			readMeInfo.append("So could you give us any gold particles or any encourage?\n");
			readMeInfo.append("Do not hesitate!Do it now!\n");
			readMeInfo.append("You can access the web site to give us encourage:\n");
			readMeInfo.append(Fibel.website+"\n");
			readMeInfo.append("Author: LocyDragon\n");
			readMeInfo.append("=====================\n");
			readMeInfo.append("What plug-in is this?\n");
			readMeInfo.append("This plugin can create simple plugins which you want!\n");
			readMeInfo.append("However, you can create only simple plugins by this plugin!\n");
			readMeInfo.append("So, if you want some custom functions.\n");
			readMeInfo.append("You still need to find plugin developers to make plugins.\n");
			readMeInfo.append("So, We hope you will have a good time with this plugin!");
			readMeInfo.append("=====================\n");
			readMeInfo.append("You don't know how to use this plugin?\n");
			readMeInfo.append("You can access the web site:\n");
			readMeInfo.append(Fibel.website+"\n");
			readMeInfo.append("And then, you will use this plugin successfully!\n");
			readMeInfo.append("=====================\n");
			writer.write(readMeInfo.toString());
			writer.close();
		} catch (IOException exc) {
			exc.printStackTrace();
			return false;
		}
		return true;
	}
}
