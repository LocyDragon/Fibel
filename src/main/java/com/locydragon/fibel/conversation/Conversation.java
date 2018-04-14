package com.locydragon.fibel.conversation;

public class Conversation {
	protected String playerName;
	protected ConRunnable runnable;

	public Conversation(String playerName, ConRunnable runnable) {
		this.playerName = playerName;
		this.runnable = runnable;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Conversation) {
			Conversation conv = (Conversation) obj;
			if (conv.playerName.equalsIgnoreCase(this.playerName)) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
}
