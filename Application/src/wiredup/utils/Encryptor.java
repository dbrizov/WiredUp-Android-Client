package wiredup.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
	/**
	 * Hashes a string to sha1 hash string
	 * @param toHash - the string that will be hashed
	 * @return the sha1 hash code
	 */
	public static String sha1Hash(String toHash) {
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			
			byte[] bytes = toHash.getBytes("UTF-8");
			digest.update(bytes, 0, bytes.length);
			bytes = digest.digest();
			
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02X", b));
			}
			
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return hash;
	}
	
	/**
	 * I am using MSSQL Server. In MSSQL server the images are
	 * represented with byte[] array (but a C# byte array, not java byte array).
	 * The Difference between them is that a single C# byte is unsigned [0, 255]
	 * and in Java it is signed [-128, 127]. In order to send bytes with values in range
	 * [0, 255] I have to send short array. (C# byte) = (Java byte) + 128. When I send
	 * the array I must first add 128 to all bytes in the array.
	 * When I get the array from the server
	 * I actually get C# byte array I need to convert it back to Java byte array.
	 * @param array - array of bytes
	 * @return short array
	 */
	public static short[] byteArrayToUnsignedByteArray(byte[] array) {
		int offset = 128;
		
		short[] result = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (short) (array[i] + offset);
		}
		
		return result;
	}
	
	/**
	 * I am using MSSQL Server. In MSSQL server the images are
	 * represented with byte[] array (but a C# byte array, not java byte array).
	 * The Difference between them is that a single C# byte is unsigned [0, 255]
	 * and in Java it is signed [-128, 127]. In order to send bytes with values in range
	 * [0, 255] I have to send short array. (C# byte) = (Java byte) + 128. When I send
	 * the array I must first add 128 to all bytes in the array.
	 * When I get the array from the server
	 * I actually get C# byte array I need to convert it back to Java byte array.
	 * @param array - array of bytes
	 * @return short array
	 */
	public static byte[] unsignedByteArrayToByteArray(short[] array) {
		int offset = 128;
		
		byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (byte) (array[i] - offset);
		}
		
		return result;
	}
	
	/**
	 * I am using MSSQL server.
	 * The thing is that the server sends a byte array (C# byte array)
	 * in the form of Base64 encoded string. I can decode the string easy but the thing is that
	 * I get unsigned bytes [0, 255]. When converting the Base64 string I break these bytes
	 * because they overflow. For example: if the server gives me a byte with value 128, that is
	 * actually a byte with value -127 in java (127 + 1 = -127) because of the overflow. To unbreak
	 * the bytes and with that whole image I need to overflow the bytes in the other direction (left direction).
	 * I need to subtract 128 from the java bytes.
	 * @param base64String - the Base64 encoded string
	 * @return byte array
	 */
	public static byte[] Base64StringToByteArray(String base64String) {
		byte[] byteArray = Base64.decodeBase64(base64String.getBytes());
		
		int offset = 128;
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] -= offset;
		}
		
		return byteArray;
	}
}
