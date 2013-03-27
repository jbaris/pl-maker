package ar.org.plmaker.aux;

public interface ProgressMonitor {

	int getTotal();

	void setCurrent(String status, int current);

	void start(String status);

}
