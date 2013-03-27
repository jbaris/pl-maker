package ar.org.plmaker.utils;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import ar.org.plmaker.aux.ProgressMonitor;
import ar.org.plmaker.aux.impl.ProgressMonitorImpl;
import ar.org.plmaker.ui.aux.ProgressDialog;

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
public class ProgressUtil {

	public static ProgressMonitor createModalProgressMonitor(Component owner,
			boolean indeterminate, ResourceBundle captions) {
		final ProgressMonitorImpl monitor = new ProgressMonitorImpl(100,
				indeterminate);
		final ProgressDialog dlg = owner instanceof Frame ? new ProgressDialog(
				(Frame) owner, monitor, captions) : new ProgressDialog(
				(Dialog) owner, monitor, captions);
		dlg.setSize(400, 50);
		dlg.setLocationRelativeTo(null);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				dlg.setVisible(true);
			}
		});
		return monitor;
	}
}