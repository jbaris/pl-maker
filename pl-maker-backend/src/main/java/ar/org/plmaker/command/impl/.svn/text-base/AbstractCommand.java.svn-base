package ar.org.plmaker.command.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.org.plmaker.command.Command;
import ar.org.plmaker.dao.PlMakerDAO;

public abstract class AbstractCommand implements Command {

	private PlMakerDAO plMakerDAO;
	private Map<String, String> params;

	protected Map<String, String> getParams() {
		return params;
	}

	public PlMakerDAO getPlMakerDAO() {
		return plMakerDAO;
	}

	@Override
	public void setParams(Collection<String[]> params) {
		final Map<String, String> par = new HashMap<String, String>();
		for (final String[] strings : params) {
			par.put(strings[0], strings[1]);
		}
		this.params = par;
	}

	public void setPlMakerDAO(PlMakerDAO plMakerDAO) {
		this.plMakerDAO = plMakerDAO;
	}

}
