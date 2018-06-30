/**
 * Created by josephrees on 14/03/2017.
 */
public class RunCoursework2 {
    public static void main(String[] args) throws Exception{
        //Create new Alice and Bob
        Alice newAlice = new Alice();
        Bob newBob = new Bob();

        //Alice creates and stores the encrypted puzzles
        newAlice.createAndStorePuzzles();

        //Bob cracks a random puzzle and returns the puzzle number
        int puzzleNumber = newBob.crack();

        //Upon recieving the puzzle number, alice sends bob an encrypted message
        String encryptedMessage = (newAlice.receivePuzzleNumber(puzzleNumber));

        //Bob decrypts the encrypted message
        newBob.decryptMessage(encryptedMessage);

        
    }
}
