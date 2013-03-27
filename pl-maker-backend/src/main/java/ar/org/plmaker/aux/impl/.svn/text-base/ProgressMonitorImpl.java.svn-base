package ar.org.plmaker.aux.impl;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ar.org.plmaker.aux.ProgressMonitor;

/**
 * MySwing: Advanced Swing Utilites Copyright (C) 2005 Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
public class ProgressMonitorImpl implements ProgressMonitor {

	private final int total;
	private int current = -1;
	private final boolean indeterminate;
	private String status;
	private final Vector<ChangeListener> listeners = new Vector<ChangeListener>();
	private final ChangeEvent ce = new ChangeEvent(this);

	public ProgressMonitorImpl(int total, boolean indeterminate) {
		this.total = total;
		this.indeterminate = indeterminate;
	}

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	private void fireChangeEvent() {
		final Iterator<ChangeListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			iter.next().stateChanged(ce);
		}
	}

	public int getCurrent() {
		return current;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public int getTotal() {
		return total;
	}

	public boolean isIndeterminate() {
		return indeterminate;
	}

	public void removeChangeListener(ChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setCurrent(String status, int current) {
		if (current == -1) {
			throw new IllegalStateException("not started yet");
		}
		this.current = current;
		if (status != null) {
			this.status = status;
		}
		fireChangeEvent();
	}

	@Override
	public void start(String status) {
		if (current != -1) {
			throw new IllegalStateException("not started yet");
		}
		this.status = status;
		current = 0;
		fireChangeEvent();
	}
}
