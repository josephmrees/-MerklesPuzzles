/**
 * Created by josephrees on 13/03/2017.
 */


public class Puzzle {

    byte[] puzzleByte = new byte[26];

    //Start is initialised to 0 by default
    byte[] start = new byte[16];
    byte[] number = new byte[2];
    byte[]  key = new byte[8];

    /**
     * Create a puzzle using a puzzle number, supplied by Alice, and a key
     * @param numberFromAlice Puzzle number supplied by Alice
     * @param newKey DES key
     */
    Puzzle(int numberFromAlice, byte[] newKey) {

        number = CryptoLib.smallIntToByteArray(numberFromAlice);
        key = newKey;

        puzzleByte = makeByte();
    }

    /**
     * Create a puzzle object using one puzzle byte
     * @param encryptedPuzzleByte
     */
    Puzzle(byte[] encryptedPuzzleByte) {
       puzzleByte = encryptedPuzzleByte;
       setStart();
       setNumber();
       setKey();
    }



	/**
     * Put the three seperate byte arrays, the start, the number and  key, together into a single byte array
     * @return The single byte array
     */
    public byte[] makeByte() {
        //Combine 3 seperate arrays of bytes into one array
        byte[] returnByte = new byte[start.length + number.length + key.length];
        System.arraycopy(start, 0, returnByte, 0, start.length);
        System.arraycopy(number, 0, returnByte, start.length, number.length);
        System.arraycopy(key, 0, returnByte, start.length + number.length, key.length);

        return returnByte;
    }

    /**
     * Get the puzzle in the form of a byte array
     * @return The puzzle in the form of a byte array
     */
    public byte[] getByte(){
        return  puzzleByte;
    }

    /**
     * Get the key from a puzzle
     * @return The key
     */
    public byte[] getKey() {
        return key;
    }
    
    public void setStart(){
    	System.arraycopy(puzzleByte, 0, start, 0, start.length);

    }

    /**
     * set the puzzle number
     */
    public void setNumber(){
    	System.arraycopy(puzzleByte, start.length, number, 0, number.length);

    }

    /**
     * set the puzzle key
     */
    private void setKey() {
    	System.arraycopy(puzzleByte, number.length+start.length, key, 0, key.length);
		
	}

    

}
