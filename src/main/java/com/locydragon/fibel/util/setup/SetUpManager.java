package com.locydragon.fibel.util.setup;

import com.locydragon.fibel.Fibel;
import com.locydragon.fibel.file.ZipFileManager;
import com.locydragon.fibel.network.DataDownland;
import com.locydragon.fibel.util.ObjectUtil;
import com.locydragon.fibel.util.configuration.FibelConfig;
import org.bukkit.entity.Player;

import java.io.File;


public class SetUpManager {
	private static final Integer fileBig = 1500;
	public static boolean hasBeenSetUp() {
		return (Boolean) FibelConfig.get("SetUp");
	}

	public static void setUpImmediately(Player visitor) {
		File thisPath = new File(".//plugins//Fibel");
		if ((thisPath.getFreeSpace()/1024/1024) <= fileBig) {
			visitor.sendMessage("§c§l硬盘空间不足.剩余空间:"+thisPath.getFreeSpace()/1024/1024+"MB.");
			return;
		}
		Thread async = new Thread(new Runnable() {
			@Override
			public void run() {
				DataDownland downlander = new DataDownland();
				visitor.sendMessage("§f[§a§lFibel§f]§c§l多线程下载引擎初始化.现在开始下载.");
				downlander.downLoadData((Integer)FibelConfig.get("DownLoadThreads"));
				for (Thread thread : downlander.watchThreadList) {
					try {
						thread.join();
					} catch (Exception exc) {
						exc.printStackTrace();
						visitor.sendMessage("§f[§a§lFibel§f]§c§l资源文件下载失败.请在后台查看报错信息并反馈给开发者.");
						return;
					}
				}
				visitor.sendMessage("§f[§a§lFibel§f]§b§l资源文件下载成功!现在开始安装....");
				try {
					ZipFileManager.unzip(ObjectUtil.downlandWhere, ObjectUtil.unZipTo, false);
				} catch (Exception exc) {
					exc.printStackTrace();
					visitor.sendMessage("§f[§a§lFibel§f]§c§l资源文件解压失败.请在后台查看报错信息并反馈给开发者.");
					return;
				}
				visitor.sendMessage("§f[§a§lFibel§f]§e§l恭喜你!下载成功!请尽情使用Fibel吧!");
				FibelConfig.configuration.set("SetUp" ,true);
			}
		});
		async.start();
		visitor.sendMessage("§a已经开始下载任务.在此过程中请不要关闭服务器.");
	}

}
