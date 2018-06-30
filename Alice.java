import javax.crypto.SecretKey;
import java.util.Random;


/**
 * Created by josephrees on 13/03/2017.
 */
public class Alice {

    Puzzle[] listOPuzzles = new Puzzle[1024];
    DES des = generateDES();

    /**
     * Generate the puzzles and write them to a txt file
     * @throws Exception
     */
    public void createAndStorePuzzles() throws Exception {
        Puzzle[] listOfEncryptedPuzzles = new Puzzle[1024];
        FileInterface fi = new FileInterface();


        //Generate list of encrypted puzzles
        int puzzleNumber = 0;
        //Foreach element in the puzzle array, create an encrypted and unencrypted puzzle and store them in the lists
        for (Puzzle currentPuzzle: listOfEncryptedPuzzles) {
            //Store unencrypted Puzzle in array
            Puzzle unencryptedPuzzle = new Puzzle(puzzleNumber, des.generateRandomKey());
    
            listOPuzzles[puzzleNumber] = unencryptedPuzzle;
            //Store encrypted Puzzle in array
            Puzzle encryptedPuzzle = encryptPuzzle(unencryptedPuzzle);
      
            listOfEncryptedPuzzles[puzzleNumber] = encryptedPuzzle;
            puzzleNumber ++;
        }

        //Write puzzles to file
        fi.writeToFile(listOfEncryptedPuzzles);

        System.out.println("Alice: 'I have finished writing them to file'");
    }

    /**
     * Encrypt a puzzle
     * @param unencryptedPuzzle Take in an unecrypted puzzle that needs encrypting with DES
     * @return Encrypted puzzle in the form of a puzzle object
     * @throws Exception
     */
    public Puzzle encryptPuzzle(Puzzle unencryptedPuzzle) throws Exception {

        byte[] randomByte = new byte[2];
        new Random().nextBytes(randomByte);
        byte[] blankBytes = new byte[6];
        byte[] key = new byte[8];
        System.arraycopy(randomByte, 0, key, 0, randomByte.length);
        System.arraycopy(blankBytes, 0, key, randomByte.length, blankBytes.length);

        SecretKey secretKey = CryptoLib.createKey(key);
        Puzzle encryptedPuzzle = new Puzzle(des.encryptPuzzle(unencryptedPuzzle, secretKey));
        
        return  encryptedPuzzle;
    }

    /**
     * Create a DES object from the class
     * @return a DES object
     */
    public DES generateDES() {
        try {
            des = new DES();
        }catch (Exception e) {
            System.out.println("ERROR");
        }
        return des;
    }

    /**
     * Method that is called with a puzzle number and a message is encrypted using key in puzzle
     * @param puzzleNumber The puzzle number supplied by Bob
     * @return The encrypted message
     */
 public String receivePuzzleNumber (int puzzleNumber) {
        String encryptedMessage = "";
        SecretKey secretKey = null;
        try {
            secretKey = CryptoLib.createKey(listOPuzzles[puzzleNumber].getKey());
            System.out.println("Alice: 'Sending message 'Hey There Bob'");
            encryptedMessage = des.encryptMessage("Hey There Bob",secretKey);
        }catch (Exception e){
            System.out.println("Error in creating encryptedMessage");
        }
        System.out.println("Alice: 'Sending encrypted message '"+encryptedMessage+"'");

        return encryptedMessage;
    }


}
