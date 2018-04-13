package com.locydragon.fibel.util.concurrent;

public enum TimeUnit {
	SECOND(1000L), MINUTE(1000 * 60L), HOUR(1000 * 60 * 60L);
	public Long time;

	private TimeUnit(Long unit) {
		this.time = unit;
	}
}
