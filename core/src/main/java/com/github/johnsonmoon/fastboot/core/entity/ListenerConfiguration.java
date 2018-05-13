package com.github.johnsonmoon.fastboot.core.entity;

import java.util.EventListener;
import java.util.List;

/**
 * Create by johnsonmoon at 2018/5/13 10:38.
 */
public class ListenerConfiguration {
	private List<EventListener> listeners;

	public ListenerConfiguration() {
	}

	public ListenerConfiguration(List<EventListener> listeners) {
		this.listeners = listeners;
	}

	public List<EventListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<EventListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public String toString() {
		return "ListenerConfiguration{" +
				"listeners=" + listeners +
				'}';
	}
}
