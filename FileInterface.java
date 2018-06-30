import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by josephrees on 13/03/2017.
 */
public class FileInterface {

    /**
     * Write the puzzles to a txt file
     * @param listOfPuzzles Take in list of puzzles that need to be written to a file
     */
    public void writeToFile(Puzzle[] listOfPuzzles){
        try{
            PrintWriter writer = new PrintWriter("myPuzzles.txt", "UTF-8");
            for (Puzzle currentPuzzle: listOfPuzzles) {
                writer.println(CryptoLib.byteArrayToString(currentPuzzle.getByte()));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error with IO");
        }
    }
   
    /**
     * Reading all the puzzle from text file
     * Store it to arraylist
     * @return arraylist of string
     */
    public ArrayList<String> readFile(){

    	ArrayList<String> arrayOfPuzzle = new ArrayList();
    	
    	try {
			Scanner in = new Scanner(new File("MyPuzzles.txt"));
			while(in.hasNextLine()){
				String puzzleString = in.nextLine();
				arrayOfPuzzle.add(puzzleString);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	return arrayOfPuzzle;
    }
    

    
}
