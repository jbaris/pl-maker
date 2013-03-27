package ar.org.plmaker.command;

import java.util.Collection;

public interface Command {

	void execute();

	void setParams(Collection<String[]> params);

	void undo();

}
