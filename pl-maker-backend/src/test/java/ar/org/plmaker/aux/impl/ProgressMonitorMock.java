package ar.org.plmaker.aux.impl;

import ar.org.plmaker.aux.ProgressMonitor;

public class ProgressMonitorMock implements ProgressMonitor {

	@Override
	public int getTotal() {
		return 0;
	}

	@Override
	public void setCurrent(String status, int current) {

	}

	@Override
	public void start(String status) {

	}

}
