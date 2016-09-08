package crypt;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
In this project you will experiment with a padding oracle attack against a toy web site hosted at crypto-class.appspot.com . 
Padding oracle vulnerabilities affect a wide variety of products, including secure tokens .

This project will show how they can be exploited. 
We discussed CBC padding oracle attacks in week 3 (segment number 5), but if you want to read more about them, please see Vaudenay's paper .

Now to business. Suppose an attacker wishes to steal secret information from our target web site crypto-class.appspot.com . 
The attacker suspects that the web site embeds encrypted customer data in URL parameters such as this:




1
http://crypto-class.appspot.com/po?er
    =f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4
That is, when customer Alice interacts with the site, the site embeds a URL like this in web pages it sends to Alice. 
The attacker intercepts the URL listed above and guesses that the ciphertext following the "po?er=" is a 
hex encoded AES CBC encryption with a random IV of some secret data about Alice's session.

After some experimentation the attacker discovers that the web site is vulnerable to a CBC padding oracle attack. 
In particular, when a decrypted CBC ciphertext ends in an invalid pad the web server returns a 403 error code (forbidden request). 
When the CBC padding is valid, but the message is malformed, the web server returns a 404 error code (URL not found).

Armed with this information your goal is to decrypt the ciphertext listed above. 
To do so you can send arbitrary HTTP requests to the web site of the form



1
http://crypto-class.appspot.com/po?er="your ciphertext here"
and observe the resulting error code. The padding oracle will let you

decrypt the given ciphertext one byte at a time. 
To decrypt a single byte you will need to send up to 256 HTTP requests to the site. 
Keep in mind that the first ciphertext block is the random IV. 
The decrypted message is ASCII encoded.

To get you started here is a short Python script that sends a ciphertext supplied on the command line to the site 
and prints the resulting error code. 
You can extend this script (or write one from scratch) to implement the padding oracle attack. 
Once you decrypt the given ciphertext, please enter the decrypted message in the box below.

This project shows that when using encryption you must prevent padding oracle attacks by either using 
encrypt-then-MAC as in EAX or GCM, or if you must use MAC-then-encrypt then 
ensure that the site treats padding errors the same way it treats MAC errors.
 * @author JShaw
 *
 */
public class Week4 {
	
	private static final String ORIG_CIPHER=
			"f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4";

	public static void main(String[] args)
	{
		
		String[] blocks = split(ORIG_CIPHER);
		int size = blocks.length;
		
		String plainText = "";
		for (int i=size;i>0;i--)
		{
			if (i-2>=0)
			{
				plainText = 
						paddingOracleAttack(blocks[i-1],blocks[i-2]) + plainText;
			}
		}
		
		System.out.println("Plain Text:" + plainText);
	}
	

	/**
	 * Concatenate bytes to create a hex string
	 * @param bytes The components of the hex string
	 * @return The hex string
	 */
	private static String createMask(String[] bytes)
	{
		StringBuilder sb = new StringBuilder(32);
		for (int i=0;i<16;i++)
		{
			sb.append(bytes[i]);
		}
		return sb.toString();
	}
	
	/**
	 * Gets the 2 char hex string for an integer
	 * @param i The integer
	 * @return
	 */
	private static String getHexString(int i)
	{
		String hex = Integer.toHexString(i);
		if (i<=15) hex = "0" + hex;
		return hex;
	}
	
	/**
	 * Creates a mask by xor'ing pad with elements of decrypted
	 * @param decrypted
	 * @param pad
	 * @return The mask.
	 */
	private static String[] getMask(String[] decrypted, int pad)
	{
		String[] mask = new String[16];
		
		for (int i=0;i<16;i++)
		{
			int j = Integer.parseInt(decrypted[i], 16);
			int xor = pad ^ j;
			mask[i] = getHexString(xor);
		}
		
		return mask;
	}
	
	/**
	 * Creates a plain text string from an array of hex encoded characters.
	 * @param decrypted The hex encoded characters
	 * @return The plain text string.
	 */
	private static String toString(String[] decrypted)
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<16;i++)
		{
			sb.append(decrypted[i]);
		}
		return sb.toString();
	}
	
	/**
	 * Executes a padding oracle attack.
	 * @param block The block to attack
	 * @param realMask The cipher mask
	 */
	private static String paddingOracleAttack(String block, String realMask)
	{
		String[] decrypted = {"00","00","00","00","00","00","00","00","00","00","00","00","00","00","00","00"};
		
		int pad = 1;
		for (int pos=16; pos > 0; pos--)
		{
			boolean goodGuess = false;
			
			String[] mask = getMask(decrypted, pad);

			for (int g = 0; g<256; g++)
			{
				String hex = getHexString(g);
				mask[pos-1] = hex;
				String maskString = createMask(mask);

				String newCipher = maskString + block;
				int ret = connect(newCipher);
				if (ret == 404){
					goodGuess = true;
					int i = g ^ pad;
					decrypted[pos-1] = getHexString(i);
					break;
				}
			}
			if (!goodGuess){
				System.out.println("Error");
				break;
			}
			pad++;
		}
		
		String encoded = Util.xorHexString(toString(decrypted), realMask);
		String plainText = Util.hexToPlainText(encoded);
		System.out.println("PlainText:" + plainText);
		return plainText;
		
	}
	
	/**
	 * Splits a string into substrings of 32 chars
	 * @param string The string to split (length assumed to be multiple of 32)
	 * @return The substrings as an array.
	 */
	private static String[] split(String string)
	{
		int size = string.length();
		int nBlocks = size/32;
		String[] blocks = new String[nBlocks];
		for (int i=0;i<nBlocks;i++){
			blocks[i] = string.substring(i*32,(i*32)+32);
		}
		return blocks;
	}
	
	/**
	 * onnects to web-server
	 * @param cipherText
	 * @return
	 */
	public static int connect(String cipherText){
		int ret = HttpsURLConnection.HTTP_ACCEPTED;
		InputStream is = null;
		try{
			URL url = new URL("http://crypto-class.appspot.com/po?er="+cipherText);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			int response = connection.getResponseCode(); 
			if (response == HttpsURLConnection.HTTP_FORBIDDEN){
				ret = response;//bad pad
			}
			if (response == HttpsURLConnection.HTTP_NOT_FOUND){
				ret = response; //malformed
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (is != null) try{ is.close(); }catch(Exception ignore){}
		}
		return ret;
	}
	
}
