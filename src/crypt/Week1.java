/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypt;

import java.util.List;
import java.util.Map;


/**
 *
 * @author JShaw
 */
public class Week1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Util week1 = new Util();
        String asciiString1 = "This is a test";
        String asciiString2 = "              ";
        System.out.println("ascii1: " + asciiString1);
        System.out.println("ascii2: " + asciiString2);
        
        String hex1 = Util.plainTextToHex(asciiString1);
        String hex2 = Util.plainTextToHex(asciiString2);
        System.out.println("hex1: " + hex1);
        System.out.println("hex2: " + hex2);
        
        asciiString1 = Util.hexToPlainText(hex1);
        System.out.println("ascii: " + asciiString1);

                       
        String res = week1.xorHexString(hex1, hex2);
        System.out.println("res: " + res);
        
        
        String[] ciphers = {
            "315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e",
            "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f",
            "32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb",
            "32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa",
            "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba43722130f042f8ec85b7c2070",
            "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d11902aebc66f2b2e436434eacc0aba938220b084800c2ca4e693522643573b2c4ce35050b0cf774201f0fe52ac9f26d71b6cf61a711cc229f77ace7aa88a2f19983122b11be87a59c355d25f8e4",
            "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83414aaaf261bda2e97b170fb5cce2a53e675c154c0d9681596934777e2275b381ce2e40582afe67650b13e72287ff2270abcf73bb028932836fbdecfecee0a3b894473c1bbeb6b4913a536ce4f9b13f1efff71ea313c8661dd9a4ce",
            "315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada43d6712150441c2e04f6565517f317da9d3",
            "271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a52222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027",
            "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83",
            "32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904"
        };
                
        int cipherLength = ciphers.length;
        int maxAsciiChars = 0;
        for (int i=0;i<cipherLength;i++)
        {
            if (maxAsciiChars < (ciphers[i].length()/2) )
            {
                maxAsciiChars=(ciphers[i].length()/2);
            }
        }
        List<Map<String,Integer>> keyList = new java.util.ArrayList<>();
        
        for (int i=0;i<cipherLength;i++)
        {
            String xor = week1.xorHexString(ciphers[i],ciphers[i]);
            System.out.println("XOR two ciphers: " + xor);
            System.out.println("cipher1: " + ciphers[i]);
            String hex = Util.plainTextToHex(xor);
            System.out.println("clear1 : " + hex);
            int l = xor.length();
            for (int k=0; k<l;k++){
                char c = xor.charAt(k);
                if(week1.isValidChar(c)){
                    int xorInt=week1.xorCharHex(
                            Util.getHexPairAt(ciphers[i],k*2),
                            Util.plainTextToHex(""+c)
                    );
                    week1.addToKey(keyList, k, Integer.toHexString(xorInt));
                }
            }
        }

        for (int i=0;i<cipherLength;i++)
        {
            for (int j=0;j<cipherLength;j++)
            {
                if (i==j){}
                else{
                    String xor = week1.xorHexString(ciphers[i],ciphers[j]);
                    System.out.println("XOR two ciphers: " + xor);
                    System.out.println("cipher1: " + ciphers[i]);
                    String hex = Util.plainTextToHex(xor);
                    System.out.println("clear1 : " + hex);
                    int l = xor.length();
                    for (int k=0; k<l;k++){
                        char c = week1.normalize(xor.charAt(k));
                        if(week1.isValidChar(c)){
                            int xorInt=week1.xorCharHex(
                                    week1.getSubHex(ciphers[i],k*2),
                                    Util.plainTextToHex(""+c)
                            );
                            week1.addToKey(keyList, k, Integer.toHexString(xorInt));
                        }
                    }
                }
            }
        }

        String key = week1.normalizeKey(keyList);
        
        for(int i=0;i<cipherLength;i++)
        {
            System.out.println(
                    "Decode: " +
                            week1.xorHexString(ciphers[i],key)
            );
        }
    }
    
    private boolean isValidChar(char c)
    {
//        int a = c;
//        int b = ' ';
        return (c>='a' && c<='z') || (c>='A' && c<='Z') 
                || (c == ' ')
                || (c == ':')   
                || (c == ',');
    }
    
    private String normalizeKey(List<Map<String,Integer>> keyList)
    {
        int size = keyList.size();
        
        StringBuilder sb = new StringBuilder(size*2);

        Map<String,Integer> map = null;
        
        for(int i=0;i<size;i++)
        {
            map = keyList.get(i);
            if (map.isEmpty())
            {
                sb.append("00");
            }
            else
            {
                String max = null;
                int maxCount = 0;
                for (String key: map.keySet())
                {
                    Integer keyCount = map.get(key);
                    if (max == null)
                    {
                        max = key;
                        maxCount = keyCount;
                    }
                    else
                    if (maxCount < keyCount)
                    {
                        max = key;
                        maxCount = keyCount;
                    }
                }
                sb.append(max);
            }
        }
        return sb.toString();
    }
    
    private void addToKey(List<Map<String,Integer>> keyList, int k, String hexString)
    {
        if (hexString.length() < 2)
        {
            hexString = "0" + hexString;
        }
        
        while (keyList.size() <= k)
        {
            keyList.add(new java.util.HashMap<String, Integer>());
        }
        Map<String,Integer> map = keyList.get(k);
        if (map.containsKey(hexString))
        {
            Integer count = map.get(hexString);
            count = count + 1;
            map.put(hexString, count);
        }
        else
        {
            map.put(hexString, 1);
        }
    }
    
    private String asciifyHex(String hex, int hexPtefix)
    {
        int size = hex.length();
        StringBuilder sb = new StringBuilder(size);
        String prefix = Integer.toHexString(hexPtefix);
        for (int i=0; i<size;i=i+2)
        {
            sb.append(prefix).append(hex.charAt(i+1));
        }
        return sb.toString();
    }
    
    private String hexToAscii(String hexString)
    {
        int size = hexString.length();
        int i = 0;
        StringBuilder sb = new StringBuilder(size/2);
        while (i < size)
        {
            String subHex = getSubHex(hexString, i);
            int h = Integer.parseInt(subHex,16);
            sb.append((char)h);
            i+=2;
        }
        return sb.toString();
    }
    
    private String asciiToHex(String asciiString)
    {
        int size = asciiString.length();
        
        StringBuilder sb = new StringBuilder(size*2);
        
        for (int i=0; i<size; i++)
        {
            char c = asciiString.charAt(i);
        
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 8)
            {
                bin = "0" + bin;
            }
        
            int h1 = Integer.parseInt(bin.substring(0,4),2);
            int h2 = Integer.parseInt(bin.substring(4),2);
        
            sb.append(Integer.toHexString(h1)).append(Integer.toHexString(h2));
        }
        
        return sb.toString();
    }
        
    /**
     * Gets a hex value for a char in a hex string
     * @param hexString The hex string.
     * @param index The 0-based index of the hex value for a char.
     * @return The hex value for a char in a hex string
     */
    private String getSubHex(String hexString, int index)
    {
        String hex = hexString.substring(index, index+2);
        return hex;
    }
    
    /**
     * XOR two hex strings.
     * @param hexString1
     * @param hexString2
     * @return The hex XOR of two hex string.
     */
    private String xorHexString(String hexString1, String hexString2)
    {
        int limit = hexString1.length() > hexString2.length()? 
                hexString2.length(): hexString1.length();
        
        StringBuilder sb = new StringBuilder(limit/2);
        for (int n=0; n < limit;n=n+2)
        {
            int i = Integer.parseInt(getSubHex(hexString1,n), 16);
            int j = Integer.parseInt(getSubHex(hexString2,n), 16);
            int k = i ^ j;
            char ch = normalize((char)k);
            
            if (isValidChar(ch))
            {
                sb.append(ch);
            }
            else
            {
                sb.append('.');
            }
        }
        
        return sb.toString();
    }

    private char normalize(char c)
    {
//        if ( ' ' == (c + 32))
//            c = ' ';
//
        return c;
    }
    
    private int xorCharHex(String hexString1, String hexString2)
    {
        int i = Integer.parseInt(hexString1, 16);
        int j = Integer.parseInt(hexString2, 16);
        int k = i ^ j;
        return k;
    }

    private char binaryToString(String binary)
    {
        char c = (char)Integer.parseInt(binary,2);
        return c;
    }
    
    private String hexToBinary(String string)
    {
        int i = Integer.parseInt(string, 16);
        String bin = Integer.toBinaryString(i);
        int size = bin.length();
        while (size < 8){
            bin = '0' + bin;
            size++;
        }
        return bin;
    }
    
}
