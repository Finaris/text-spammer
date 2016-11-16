import java.util.*;

public class Chain {

	private String[] totalWords;
	private Set<String> uniqWords;
	private double totalSize, dictionarySize;
	private int steps;
	private Hashtable<String, Probabilities> hash;
	private ArrayList<String> uniqueWordsArray;
	private Random rand;
	
	//creates and stores initial needed values
	public Chain(int size, List<String> words) {
		
		//creates array of all words
		String temp = "";
		for (String x: words) {
			if (temp.equals(""))
				temp = x;
			else
				temp = temp + " " + x;
		}
		totalWords = temp.split(" ");
		
		//makes a set of the unique words
		uniqWords = new TreeSet<String>();
		uniqWords.addAll(Arrays.asList(totalWords));
		
		//initializes sizes of lists and Hashtable -- also sets sizes of lists
		steps = size;
		uniqueWordsArray = new ArrayList<String>();
		totalSize = totalWords.length;
		dictionarySize = uniqWords.size();
		hash = new Hashtable<String, Probabilities>();
		
		//creates Probabilities objects associated with each word
		for (String unique: uniqWords) {
			hash.put(unique, new Probabilities());
			uniqueWordsArray.add(unique);
		}
		
		//creates links of words
		createLinks();
		
	}
	
	public void generate(int amount) {
		
		//Creates variables
		rand = new Random();
		String temp = "";
		boolean beginning = true;
		
		int modifiedAmount = (int) Math.ceil(((amount - 1) / (steps - 1)) + 1);
		
		for (int i = 0; i < modifiedAmount; i++) {
			//Checks whether or not it is the beginning of a sentences
			if (beginning) {
				
				//randomly retrieve values until a capitalized word is found, then breaks out of the while loop
				while (true) {
					temp = uniqueWordsArray.get(rand.nextInt(uniqueWordsArray.size()));
					if (((temp.charAt(0) >= 65 && temp.charAt(0) <= 90) || (temp.charAt(0) == '"')) 
							&& (!(temp.charAt(temp.length() - 1) == '.') || !(temp.charAt(temp.length() - 1) == '"')))
						break;
				}
				//print+makes sure that beginning words aren't found
				System.out.print(temp + " ");
				beginning = false;
				
			} else {
				//if the selected option is greater than a pair, this gets the last word.
				String[] tempStrings = temp.split(" ");
				temp = tempStrings[tempStrings.length - 1];
				
				//get a random pair of the previous word, then set it equal to temp. Print out then check if the word is at the end of a sentence
				int index = 0;
				
				//in case no pair is found
				try {
					index = rand.nextInt(hash.get(temp).getAssoc().size());
				} catch (Exception e) {
					beginning = true;
					if(!(temp.charAt(temp.length() - 1) == '.') || (temp.charAt(temp.length() - 1) == '"')) {
						System.out.print(". ");
						continue;
					} else {
						System.out.print(" ");
						continue;
					}
				}
				
				temp = hash.get(temp).getAssoc().get(index);
				System.out.print(temp + " ");
				if(!(temp.charAt(temp.length() - 1) == '.') || (temp.charAt(temp.length() - 1) == '"'))
					continue;
				else
					beginning = true;
				
			}
		}
		
	}
	
	public void createLinks() {
		
		for (int i = 0; i < totalWords.length - steps + 1; i++) {
			
			String first = totalWords[i];
			String last = "";
			
			for (int j = 1; j < steps; j++) {
				if (last.equals(""))
					last = totalWords[i+j];
				else
					last = last + " " + totalWords[i+j];
			}
			
			hash.get(first).addLink(last);
			
		}
		
	}
	
}
