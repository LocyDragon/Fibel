package com.locydragon.fibel.listeners.setup;

import com.locydragon.fibel.conversation.ConRunnable;
import com.locydragon.fibel.conversation.Conversation;
import com.locydragon.fibel.conversation.ConversationManager;
import com.locydragon.fibel.util.setup.SetUpManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PluginSetUpListener implements Listener {
	public static final String iName = "SetUpFibel";

	@EventHandler
	public void onOpJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().isOp()) {
			return;
		}
		if (SetUpManager.hasBeenSetUp()) {
			return;
		}
		e.getPlayer().sendMessage("§7[§b§lFibel_AI插件制作插件§7]§c本插件未安装.");
		e.getPlayer().sendMessage("§b§l请在10秒内在聊天栏里输入: " + iName + " 以安装插件.");
		e.getPlayer().sendMessage("§c§l注意.安装插件需要大约1.5GB的硬盘空闲空间.");
		e.getPlayer().sendMessage("§c安装完毕后会删除一个670MB的文件.总大小约为1G.");
		ConversationManager.addConversationForTime(new Conversation(e.getPlayer().getName(), new ConRunnable() {
			@Override
			public void onConversation(Player player, String chatMsg) {
				if (chatMsg.equalsIgnoreCase(iName)) {

				}
			}
		}), 10);
	}
}
