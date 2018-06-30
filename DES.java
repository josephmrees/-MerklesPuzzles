import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {
	static Cipher cipher;
	
	public DES() throws Exception {
		cipher = Cipher.getInstance("DES");
		
	}
	
	/**
	Initializes this cipher with a key.
	Encrypts or decrypts data in a single-part operation, or finishes a multiple-part operation.
	
	Returns a Base64.Encoder that encodes using the Basic type base64 encoding scheme.
	Encodes the specified byte array into byte array using the Base64 encoding scheme.
	**/
	public byte[] encryptPuzzle(Puzzle plainPuzzle, SecretKey secretKey) throws Exception {
		//Convert plaintext into byte representation
		byte[] plainTextByte = plainPuzzle.getByte();
				
		//Initialise the cipher to be in encrypt mode, using the given key.
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		//Perform the encryption
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		
		//Get a new Base64 (ASCII) encoder and use it to convert ciphertext back to a string
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		
		
		return encryptedByte;
	}
	
	/**
	Initializes this cipher with a key.
	Decrypt data in a single-part operation, or finishes a multiple-part operation.
	
	Returns a Base64.Decoder that decodes using the Basic type base64 decoding scheme.
	Decodes the specified byte array into a byte array using the Base64 decoding scheme.
	**/
	public byte[] decryptPuzzle(byte[] encryptedpuzzle, SecretKey secretKey)throws IOException, InvalidKeyException,BadPaddingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException  {
		byte[] decryptedByte = null;
		
		//Initialise the cipher to be in decrypt mode, using the given key.
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		//Perform the decryption
		decryptedByte= cipher.doFinal(encryptedpuzzle);
		
		return decryptedByte;
	}
	
	
	public String encryptMessage(String plainText, SecretKey secretKey) throws Exception {

		//Convert plaintext into byte representation
		byte[] plainTextByte = plainText.getBytes();

		//Initialise the cipher to be in encrypt mode, using the given key.
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		//Perform the encryption
		byte[] encryptedByte = cipher.doFinal(plainTextByte);

		//Get a new Base64 (ASCII) encoder and use it to convert ciphertext back to a string
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);

		return encryptedText;
	}

	public String decrypt(String encryptedText, SecretKey secretKey)throws IOException, InvalidKeyException,BadPaddingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException  {
		//Get a new Base64 (ASCII) decoder and use it to convert ciphertext from a string into bytes
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		
		//Initialise the cipher to be in decryption mode, using the given key.
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		//Perform the decryption
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		
		//Convert byte representation of plaintext into a string
		String decryptedText = new String(decryptedByte);
		
		return decryptedText;
	}


	public byte[] generateRandomKey() {
		//Use java's key generator to produce a random key.
		byte[] secretKeyByte = new byte[8];
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			SecretKey secretKey = keyGenerator.generateKey();
			secretKeyByte = secretKey.getEncoded();

			


		} catch(Exception NoSuchAlgorithmException){
			System.out.println("No Such Algorithm Found");
		}

		return secretKeyByte;
	}
	
	
	
	/**
		getInstance(String algorithm) - Returns a MessageDigest object that implements the specified digest algorithm
		digest(byte[] input) - Performs a final update on the digest using the specified array of bytes, then completes the digest computation.
		
		SecretKeySpec(byte[] key, String algorithm)
		Constructs a secret key from the given byte array.
		
		getEncoded()
		Returns the key material of this secret key
	**/
	public SecretKey generateKeyFromPassword(String password) throws Exception{

		//Get byte representation of password.
		//Note here you should ideally also use salt!
		byte[] passwordInBytes = (password).getBytes("UTF-8");

		//Use sha to generate a message digest of the password
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] key = sha.digest(passwordInBytes);

		//AES keys are only 128bits (16 bytes) so take first 128 bits of digest.
		key = Arrays.copyOf(key, 16);

		//Generate secret key using
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

		//print the key
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println(encodedKey);

		return secretKey;
	}
	
}