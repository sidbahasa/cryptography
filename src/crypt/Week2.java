package crypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Week2 {

	public static final void main(String[] args) {
		myQuestion1();
		myQuestion2();
		myQuestion3();
		myQuestion4();
		System.out.println("----------------------------------");
		question1();
		question2();
		question3();
		question4();
	}

	/**
	 * Question 1
	 * 
	 * CBC key: 140b41b22a29beb4061bda66b6747e14 CBC Ciphertext 1:
	 * 4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81
	 */
	public static void question1() {
		String aesKey = "140b41b22a29beb4061bda66b6747e14";
		String ivPlusCipherText = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";
		String encryptedIv = ivPlusCipherText.substring(0, 32);
		String cipherString = ivPlusCipherText.substring(32);
		decryptCBC(cipherString, encryptedIv, aesKey);
	}

	public static void myQuestion1() {
		String aesKey = "140b41b22a29beb4061bda66b6747e14";
		String ivPlusCipherText = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";
		myDecryptCBC(aesKey, ivPlusCipherText);
	}

	/**
	 * 2. CBC key: 140b41b22a29beb4061bda66b6747e14 CBC Ciphertext 2:
	 * 5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253
	 */
	public static void question2() {
		String aesKey = "140b41b22a29beb4061bda66b6747e14";
		String ivPlusCipherText = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253";
		String encryptedIv = ivPlusCipherText.substring(0, 32);
		String cipherString = ivPlusCipherText.substring(32);
		decryptCBC(cipherString, encryptedIv, aesKey);
	}

	public static void myQuestion2() {
		String aesKey = "140b41b22a29beb4061bda66b6747e14";
		String ivPlusCipherText = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253";
		myDecryptCBC(aesKey, ivPlusCipherText);
	}

	/**
	 * 3. CTR key: 36f18357be4dbd77f050515c73fcf9f2 CTR Ciphertext 1:
	 * 69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329
	 */
	public static void question3() {
		String aesKey = "36f18357be4dbd77f050515c73fcf9f2";
		String ivPlusCipherText = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
		String encryptedIv = ivPlusCipherText.substring(0, 32);
		String cipherString = ivPlusCipherText.substring(32);
		decryptCTR(cipherString, encryptedIv, aesKey);
	}

	public static void myQuestion3() {
		String aesKey = "36f18357be4dbd77f050515c73fcf9f2";
		String ivPlusCipherText = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
		myDecryptCTR(aesKey, ivPlusCipherText);
	}

	/**
	 * 4. CTR key: 36f18357be4dbd77f050515c73fcf9f2 CTR Ciphertext 2:
	 * 770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451
	 */
	public static void question4() {
		String aesKey = "36f18357be4dbd77f050515c73fcf9f2";
		String ivPlusCipherText = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
		String encryptedIv = ivPlusCipherText.substring(0, 32);
		String cipherString = ivPlusCipherText.substring(32);
		decryptCTR(cipherString, encryptedIv, aesKey);
	}

	public static void myQuestion4() {
		String aesKey = "36f18357be4dbd77f050515c73fcf9f2";
		String ivPlusCipherText = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
		myDecryptCTR(aesKey, ivPlusCipherText);
	}

	/**
	 * Splits a string into blocks of size blockSize
	 * 
	 * @param string
	 *            A string with length = a whole number multiple of blockSize
	 * @param blockSize
	 *            The number of char in a block.
	 * @return A list of blocks.
	 */
	public static String[] splitStringIntoBlocks(String string, int blockSize) {
		int size = string.length();
		int listSize = 0;
		while (listSize * blockSize < size) {
			listSize++;
		}

		String[] list = new String[listSize];
		int index = 0;
		for (int i = 0; i < listSize; i++) {
			index = i * blockSize;
			list[i] = string.substring(index, index + blockSize);
		}

		return list;
	}

	/**
	 * Constructs an AES secret key
	 * 
	 * @param hexKey
	 *            The AES key in hex
	 * @return
	 */
	public static SecretKeySpec constructAesSecretKey(String hexKey) {
		SecretKeySpec secretKeySpec = null;
		byte[] keyBytes = Util.getBytesForHexString(hexKey);
		secretKeySpec = new SecretKeySpec(keyBytes, "AES");
		return secretKeySpec;
	}

	/**
	 * Constructs the IV parameter
	 * 
	 * @param encryptedIV
	 *            The encrypted IV.
	 * @return The IV parameter
	 */
	public static IvParameterSpec getIvParameterSpec(String encryptedIV) {
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Util.getBytesForHexString(encryptedIV));
		return ivParameterSpec;
	}

	/**
	 * Decrypts a CBC cipher text using the Cipher API
	 * 
	 * @param cipherText
	 *            The CBC encrypted cipher text to decrypt.
	 * @param encryptedIv
	 *            The encrypted iv.
	 * @param aesKey
	 *            The AES key in hex
	 */
	public static void decryptCBC(String cipherText, String encryptedIv, String aesKey) {

		System.out.println("Decrypting...");

		CipherInputStream cipherInputStream = null;
		
		try {
			Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			SecretKeySpec secretKeySpec = constructAesSecretKey(aesKey);
			aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, getIvParameterSpec(encryptedIv));

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(Util.getBytesForHexString(cipherText));
			cipherInputStream = new CipherInputStream(inStream, aesCipher);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
				System.out.println("Plain Text: " + new String(buf, 0, bytesRead));
				buf = new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (cipherInputStream != null) 
				try{cipherInputStream.close();}catch(Exception ignore){}
		}
	}

	/**
	 * Decrypts a CTR cipher text using the Cipher API
	 * 
	 * @param cipherText
	 *            The CTR encrypted cipher text to decrypt.
	 * @param encryptedIv
	 *            The encrypted iv.
	 * @param aesKey
	 *            The AES key in hex
	 */
	public static void decryptCTR(String cipherText, String encryptedIv, String aesKey) {

		System.out.println("Decrypting...");

		CipherInputStream cipherInputStream = null;
		try {
			Cipher aesCipher = Cipher.getInstance("AES/CTR/PKCS5Padding");

			SecretKeySpec secretKeySpec = constructAesSecretKey(aesKey);
			aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, getIvParameterSpec(encryptedIv));

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(Util.getBytesForHexString(cipherText));
			cipherInputStream = new CipherInputStream(inStream, aesCipher);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
				System.out.println("Plain Text: " + new String(buf, 0, bytesRead));
				buf = new byte[1024];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (cipherInputStream != null) 
				try{cipherInputStream.close();}catch(Exception ignore){}
			
		}
	}

	/**
	 * Decrypts a CBC encrypted cipher text using a AES/ECB/NoPadding cipher
	 * <ul>
	 * <li>Divides cipher text into blocks
	 * <li>block 0 is the encrypted IV
	 * <li>For blocks 1 to n:
	 * <ul>
	 * <li>Decrypt block
	 * <li>XOR decrypted block with previous encrypted block
	 * </ul>
	 * </ul>
	 * 
	 * @param aesKey
	 *            The AES key in hex
	 * @param cipherText
	 *            The cipher text includes the encrypted iv.
	 */
	public static void myDecryptCBC(String aesKey, String cipherText) {
		byte[] encryptedBytes = Util.getBytesForHexString(cipherText);
		System.out.println("Decrypting...");

		try {
			Cipher aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
			SecretKeySpec secretKeySpec = constructAesSecretKey(aesKey);
			aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			int nBytes = encryptedBytes.length;
			int nBlocks = nBytes / 16;
			byte[][] blocks = new byte[nBlocks][16];
			int j = 0;
			int k = 0;
			for (int i = 0; i < nBytes; i++) {
				blocks[j][k] = encryptedBytes[i];
				k++;
				if (k == 16) {
					j++;
					k = 0;
				}
			}
			String plainText = "";
			for (int i = nBlocks - 1; i >= 0; i--) {
				if (i - 1 >= 0) {
					byte[] decrypted = aesCipher.doFinal(blocks[i]);
					String string = xorBytes(decrypted, blocks[i - 1], i == nBlocks - 1);
					plainText = string + plainText;
				}
			}
			System.out.println("Plain Text: " + plainText);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Decrypts a CTR encrypted cipher text using a AES/ECB/NoPadding cipher
	 * <ul>
	 * <li>Divides cipher text into blocks
	 * <li>block 0 is the plain text IV
	 * <li>For blocks 1 to n:
	 * <ul>
	 * <li>Encrypt (IV + ( block nbr - 1 ))
	 * <li>XOR encrypted (IV + ( block nbr - 1 )) with encrypted block
	 * </ul>
	 * </ul>
	 * 
	 * @param aesKey
	 *            The AES key in hex
	 * @param cipherText
	 *            The cipher text includes the plain text iv.
	 */
	public static void myDecryptCTR(String aesKey, String cipherText) {
		System.out.println("Decrypting...");
		byte[] encryptedBytes = Util.getBytesForHexString(cipherText);

		try {
			Cipher aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
			SecretKeySpec secretKeySpec = constructAesSecretKey(aesKey);
			aesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			int nBytes = encryptedBytes.length;
			int nBlocks = 0;
			for (int i = 0; i < nBytes; i += 16) {
				nBlocks++;
			}

			byte[][] blocks = new byte[nBlocks][];
			int remainder = nBytes - ((nBlocks - 1) * 16);

			for (int i = 0; i < nBlocks; i++) {
				if (i + 1 == nBlocks) {
					blocks[i] = new byte[remainder];
				} else {
					blocks[i] = new byte[16];
				}
			}

			int j = 0;
			int k = 0;
			for (int i = 0; i < nBytes; i++) {
				blocks[j][k] = encryptedBytes[i];
				k++;
				if (k == 16) {
					j++;
					k = 0;
				}
			}

			BigInteger bigIv = new BigInteger(blocks[0]);

			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < nBlocks; i++) {
				byte[] encryptedIv = aesCipher.doFinal(ByteBuffer.allocate(16).put(bigIv.toByteArray()).array());
				String string = xorBytes(encryptedIv, blocks[i]);
				sb.append(string);
				bigIv = bigIv.add(BigInteger.ONE);
			}
			System.out.println("Plain Text: " + sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * XOR two byte arrays accounting for a pad in array one.
	 * 
	 * @param one
	 *            A potentially padded byte array
	 * @param two
	 *            The other byte array
	 * @param pad
	 *            true if the first byte array is padded; false otherwise
	 * @return The XOR of two byte arrays
	 */
	public static String xorBytes(byte[] one, byte[] two, boolean pad) {
		String ret = "";
		Util.xorByteArrays(one, two);
		if (pad) {
			int padSize = one[15];
			int length = one.length - padSize;
			ret = new String(one, 0, length);
		} else {
			ret = new String(one);
		}
		return ret;
	}

	/**
	 * XORs two byte arrays (upto the smaller of the two arrays)
	 * 
	 * @param one
	 *            The first byte array.
	 * @param two
	 *            The second byte array.
	 * @return The XOR of two byte arrays.
	 */
	public static String xorBytes(byte[] one, byte[] two) {
		String ret = "";
		int min = one.length;
		if (two.length < min) {
			min = two.length;
		}
		Util.xorByteArrays(one, two);
		ret = new String(one, 0, min);
		return ret;
	}
}
