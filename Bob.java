import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class Bob {
	
	
	
	String encrptedMsg;
	DES des = generateDES();
	
	Puzzle puzzle;
	
    public DES generateDES() {
        try {
            des = new DES();
        }catch (Exception e) {
            System.out.println("ERROR");
        }
        return des;
    }
    
    /**
     * Creating a random puzzle by getting a random byte from the Puzzle file
     * @return a string of random puzzle
     */
    
    public String getPuzzle(){
    	String puzzle = "";
    	FileInterface fi = new FileInterface();
		Random random = new Random();
		
		//getting a random puzzle in arraylist of puzzle
		ArrayList<String> arrayOfPuzzle = fi.readFile();
		puzzle =  arrayOfPuzzle.get(random.nextInt(arrayOfPuzzle.size()));
    	
    	return puzzle;
    }
    
    
    /**
     * This is cracking a random puzzle by taking a random key
     * 
     * @return int of puzzle number for Alice to encrypted message
     */
    public int crack() throws IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
       
    	//To check the key is correctly crack and then exit while loop
       	boolean isCrack = false;
       	String puzzleString = getPuzzle();
       	byte[] key = new byte[8];
       	byte[] decryptedPuzzle = new byte[26];
		
       	//Do while loop for cracking
       	//a puzzle by taking random key until puzzle is cracked
       	do{
       		byte[] randomByte = new byte[2];
            new Random().nextBytes(randomByte);
            byte[] blankBytes = new byte[6];
            
            System.arraycopy(randomByte, 0, key, 0, randomByte.length);
            System.arraycopy(blankBytes, 0, key, randomByte.length, blankBytes.length);

	    	
	    	byte[] puzzleByte = CryptoLib.stringToByteArray(puzzleString);
	        SecretKey secretKey = CryptoLib.createKey(key);
	    	try{
	    		decryptedPuzzle = des.decryptPuzzle(puzzleByte, secretKey);
	    		
	    		//Checking the decrypted puzzle is 26 byte or not
	    		isCrack = checkFor0s(decryptedPuzzle);
		    	
	    	}catch (BadPaddingException e){
		    	isCrack = false;
		    }
		    }while(isCrack != true);
       	
       	puzzle  = new Puzzle(decryptedPuzzle);
       	return CryptoLib.byteArrayToSmallInt(puzzle.number);
    }
    
    
    /**
     * Checking a key is valid or not
     * Some decrypted puzzle is not correct after decrypting with a random key
     * @param puzzle
     * @return true when the puzzle is 26 byte
     */
    public boolean checkFor0s(byte[] puzzle){
    	boolean valid = true;
    	if (puzzle.length == 26){
	    	for (int i = 0; i < 16; i++){
	    		if (puzzle[i] != 0){
	    			valid = false;
	    		}else{
	 
	    		} valid = true;
	    	}
    	}else valid = false;
    	return valid;
    }

    /**
     * Decrypting a message received from Alice with key from puzzle
     * @param encryptedMessage
     */
	public void decryptMessage(String encryptedMessage) throws Exception {
		byte[] key = puzzle.getKey();
		SecretKey secretKey =  CryptoLib.createKey(key);
		System.out.println("Bob 'This is the message I've decrypted: " + des.decrypt(encryptedMessage, secretKey) + "'");
		
	}

}