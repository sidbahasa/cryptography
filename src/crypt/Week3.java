/**
 * Suppose a web site hosts large video file F that anyone can download. Browsers who download the file need to make sure the file is authentic before displaying the content to the user. One approach is to have the web site hash the contents of F using a collision resistant hash and then distribute the resulting short hash value h=H(F) to users via some authenticated channel (later on we will use digital signatures for this). Browsers would download the entire file F, check that H(F) is equal to the authentic hash value h and if so, display the video to the user. Unfortunately, this means that the video will only begin playing after the *entire* file F has been downloaded.

Our goal in this project is to build a file authentication system that lets browsers authenticate and play video chunks as they are downloaded without having to wait for the entire file. Instead of computing a hash of the entire file, the web site breaks the file into 1KB blocks (1024 bytes). It computes the hash of the last block and appends the value to the second to last block. It then computes the hash of this augmented second to last block and appends the resulting hash to the third block from the end. This process continues from the last block to the first as in the following diagram:


The final hash value h0 – a hash of the first block with its appended hash – is distributed to users via the authenticated channel as above.

Now, a browser downloads the file F one block at a time, where each block includes the appended hash value from the diagram above. When the first block (B0 ∥∥ h1) is received the browser checks that H(B0 ∥∥ h1) is equal to h0 and if so it begins playing the first video block. When the second block (B1 ∥∥ h2) is received the browser checks that H(B1 ∥ h2) is equal to h1 and if so it plays this second block. This process continues until the very last block. This way each block is authenticated and played as it is received and there is no need to wait until the entire file is downloaded.

It is not difficult to argue that if the hash function H is collision resistant then an attacker cannot modify any of the video blocks without being detected by the browser. Indeed, since h0=H(B0 ∥∥ h1) an attacker cannot find a pair (B′0,h′1)≠(B0,h1) such that h0=H(B′0 ∥∥ h′1) since this would break collision resistance of H. Therefore after the first hash check the browser is convinced that both B0 and h1 are authentic. Exactly the same argument proves that after the second hash check the browser is convinced that both B1 and h2 are authentic, and so on for the remaining blocks.

In this project we will be using SHA256 as the hash function. For an implementation of SHA256 use an existing crypto library such as PyCrypto (Python), Crypto++ (C++), or any other.

When appending the hash value to each block, please append it as binary data, that is, as 32 unencoded bytes (which is 256 bits). If the file size is not a multiple of 1KB then the very last block will be shorter than 1KB, but all other blocks will be exactly 1KB.

Your task is to write code to compute the hash h0 of a given file F and to verify blocks of F as they are received by the client.

In the box below please enter the (hex encoded) hash h0 for this video file.

You can check your code by using it to hash a different file. In particular, the hex encoded h0 for this video file is:

03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8
 */
package crypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author JShaw
 */
public class Week3 {

	private static final String SAMPLE_H0 = "03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8";
	/**
	 * 03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8
	 * @param args
	 */
	public static void main(String[] args) {
		if (getH0("C:\\Users\\jshaw\\Desktop\\6.2.birthday.mp4").equals(SAMPLE_H0)){
			System.out.println("h0:" + getH0("C:\\Users\\jshaw\\Desktop\\6.1.intro.mp4"));			
		}
		else{
			System.out.println("Bad h0:" + getH0("C:\\Users\\jshaw\\Desktop\\6.1.intro.mp4"));			
		}
		
	}
	
	private static String getH0(String filename){
		FileInputStream is = null;
		String hash = null;
		try {
			File file = new File(filename);
			is = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(is);
			byte[][] blocks = split(bytes,1024);
			hash = Util.getHexStringFromBytes(calculateSha256ForH0(blocks));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ignore) {
			}
		}
		
		return hash;
	}

	/**
	 * Split file bytes into blocks of blockSize each.
	 * @param bytes The bytes to split.
	 * @param blockSize The size of a block.
	 * @return The blocks
	 */
	private static byte[][] split(byte[] bytes, int blockSize){
		int nBlocks = bytes.length/blockSize;
		int last = bytes.length - (nBlocks * blockSize);
		if (last > 0) nBlocks++;
		if (last == 0) last = blockSize;
		byte[][] blocks = new byte[nBlocks][];
		
		byte[] aBlock = {};
		for (int i=0;i<nBlocks;i++)
		{
			
			if (i == nBlocks-1)
			{
				aBlock = new byte[last];
			}
			else{
				aBlock = new byte[blockSize];
			}
				
			System.arraycopy(bytes, i*blockSize, aBlock, 0, aBlock.length);
			blocks[i] = aBlock;
		}
				
		return blocks;
	}
	
	/**
	 * Concatenates two byte arrays
	 * @param one 
	 * @param two
	 * @return The concatenated bytes arrays
	 */
	private static byte[] concat(byte[] one, byte[] two)
	{
		byte[] concat = new byte[one.length+two.length];
		
		if (one.length > 0)
			System.arraycopy(one, 0, concat, 0, one.length);
		if (two.length > 0)
			System.arraycopy(two, 0, concat, one.length, two.length);
		
		return concat;
		
	}
	
	/**
	 * Calculates the sha256 hash for h0
	 * @param blocks The blocks of the file.
	 * @throws Exception
	 */
	private static byte[] calculateSha256ForH0(byte[][] blocks) throws Exception {
		
		byte[] mdBytes = {};
		byte[] concat = {};
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		for (int i=blocks.length; i> 0; i--)
		{
			concat = concat(blocks[i-1],mdBytes);
			md.update(concat,0,concat.length);
			mdBytes = md.digest();
			md.reset();
		}
		
		return mdBytes;
	}
}
