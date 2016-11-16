import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Source {

	public static void main(String[] args) throws IOException {
		
		Path path = Paths.get("C:\\Users\\Joey\\Desktop\\test.txt");
		Charset characters = StandardCharsets.ISO_8859_1;
		List<String> names = Files.readAllLines(path, characters);
		
		final int MAX = 4;
		
		Chain chain1 = new Chain(MAX, names);
		
		//1 + (MAX-1)(n-1) --> this was modified to approximate the input in generate()
		chain1.generate(25);
		
		
	}
	
}
