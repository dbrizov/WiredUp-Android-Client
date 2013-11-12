package wiredup.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
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
	
	public static short[] byteArrayToSignedByteArray(byte[] array) {
		int offset = 128;
		
		short[] result = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (short) (array[i] + offset);
		}
		
		return result;
	}
	
	public static byte[] signedByteArrayToByteArray(short[] array) {
		int offset = 128;
		
		byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (byte) (array[i] - offset);
		}
		
		return result;
	}
}
