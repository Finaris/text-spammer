import java.util.*;

public class Probabilities {

	private Vector<String> associations;
	
	public Probabilities() {
		associations = new Vector<String>();
	}
	
	public void addLink(String n) {
		associations.addElement(n);
	}
	
	public Vector<String> getAssoc() {
		return associations;
	}
	
}
