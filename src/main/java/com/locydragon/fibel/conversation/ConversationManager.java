package com.locydragon.fibel.conversation;

import com.locydragon.fibel.util.concurrent.TimeUnit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class ConversationManager implements Listener {
	private static List<Conversation> conList = new ArrayList<>();
	private static Object lock = new Object();

	public static void addConversation(Conversation conversation) {
		if (conList.contains(conversation)) {
			return;
		}
		conList.add(conversation);
	}

	public static void addConversationForTime(Conversation conversation, int second) {
		if (conList.contains(conversation)) {
			return;
		}
		conList.add(conversation);
		Thread runLater = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(second * TimeUnit.SECOND.time);
					synchronized (lock) {
						if (conList.contains(conversation)) {
							conList.remove(conversation);
						}
					}
				} catch (Exception exc) {
				}
			}
		});
		runLater.start();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		for (Conversation conv : conList) {
			if (conv.playerName.equalsIgnoreCase(e.getPlayer().getName())) {
				conv.runnable.onConversation(e.getPlayer(), e.getMessage());
				conList.remove(conv);
			} else {
				continue;
			}
		}
	}
}
