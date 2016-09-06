/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptography;

import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author JShaw
 */
public class Util {

    public static boolean isValidChar(char c)
    {
//        int a = c;
//        int b = ' ';
        return (c>='a' && c<='z') || (c>='A' && c<='Z') 
                || (c == ' ')
                || (c == ':')   
                || (c == ',');
    }
    
    /**
     * Normalizes a hex string by padding the string with the hexPrefix
     * if the length of the hex String is < 2
     * @param hex A hex string
     * @param hexPtefix Pads the hex String
     * @return 
     */
    public static String normalizeHexString(String hex, int hexPtefix)
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
    
    /**
     * Converts a hex string to an ascii string
     * @param hexString The hex string to convert.
     * @return The ascii version of the hex string
     */
    public static String hexToPlainText(String hexString)
    {
        int size = hexString.length();
        int i = 0;
        StringBuilder sb = new StringBuilder(size/2);
        while (i < size)
        {
            String subHex = getHexPairAt(hexString, i);
            int h = Integer.parseInt(subHex,16);
            sb.append((char)h);
            i+=2;
        }
        return sb.toString();
    }
    
    /**
     * Converts ascii to hex
     * <li>Converts a char to 8 bit binary
     * <li>Converts 8 bit binary to 2 char hex
     * @param asciiString
     * @return 
     */
    public static String plainTextToHex(String asciiString)
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
     * Extract a hex value for a char in a hex string
     * @param hexString The hex string.
     * @param index The 0-based index of the hex value for a char.
     * @return The hex value for a char in a hex string
     */
    public static String getHexPairAt(String hexString, int index)
    {
        String hex = hexString.substring(index, index+2);
        return hex;
    }
    
    public static void xorByteArrays(byte[] one, byte[] two){
        int size = one.length;
        if (two.length < size)
        {
            size = two.length;
        }
        byte[] res = new byte[size];
        for (int i=0;i<size;i++)
        {
            one[i] ^= two[i];
        }
    }
    /**
     * XOR two hex strings.
     * @param hexString1
     * @param hexString2
     * @return The hex XOR of two hex string.
     */
    public static String xorHexString(String hexString1, String hexString2)
    {
        int limit = hexString1.length() > hexString2.length()? 
                hexString2.length(): hexString1.length();
        
        StringBuilder sb = new StringBuilder(limit/2);
        for (int n=0; n < limit;n=n+2)
        {
            int i = Integer.parseInt(getHexPairAt(hexString1,n), 16);
            int j = Integer.parseInt(getHexPairAt(hexString2,n), 16);
            int k = i ^ j;
            char ch = (char)k;
            
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
    
    /**
     * XOR two hex values for chars
     * @param hexString1
     * @param hexString2
     * @return 
     */
    public static int xorHexPair(String hexString1, String hexString2)
    {
        int i = Integer.parseInt(hexString1, 16);
        int j = Integer.parseInt(hexString2, 16);
        int k = i ^ j;
        return k;
    }

    /**
     * Creates a integer string from a binary string
     * @param binary The binary string
     * @return The integer string.
     */
    public static char binaryStringToIntegerString(String binary)
    {
        char c = (char)Integer.parseInt(binary,2);
        return c;
    }
    
    public static String hexPairTo8BitBinary(String string)
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
    
    /** 
     * Converts plain text to bytes
     * @param plainText
     * @return 
     */
    public static byte[] getBytesForPlainText(String plainText)
    {
        return plainText.getBytes();
//        String string = plainTextToHex(plainText);
//        return getBytesForHexString(string);
    }

    public static byte[] getBytesForHexString(String hexString)
    {//            
        int size = hexString.length();
        byte[] bytes = new byte[size/2];        
        
        for (int n = 0; n<size; n=n+2)
        {
            bytes[n/2] = (byte)((Character.digit(hexString.charAt(n),16) << 4) +
            Character.digit(hexString.charAt(n+1),16));
        }
        return bytes;
    }
    
    public static String getHexStringFromBytes(byte[] bytes)
    {
        
        int size = bytes.length;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<size;i++)
        {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2)
            {
                hex = "0" + hex;
            }
//            System.out.print(hex);
            sb.append(hex);
        }
        return sb.toString();
        //return hexToPlainText(sb.toString());
    }
}
