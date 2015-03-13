package eu.cloudopting.ui.ToscaUI.server.model;

/**
 * 
 * @author xeviscc
 *
 */
public class SLA {
	public String numCpus;
	public String memory;
	public String price;
	public String disk;
	public String chosen;

	@Override
	public String toString() {
		return "numCpus " +  numCpus
				+ ", memory " + memory
				+ ", price " + price
				+ ", disk " + disk 
				+ ", chosen " + chosen;
	}
}
