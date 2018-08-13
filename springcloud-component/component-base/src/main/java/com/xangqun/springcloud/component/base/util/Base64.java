/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.util;


import java.io.*;

/**
 * Base64 编码和解码
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class Base64 {

	private Base64() {
		// Do nothing
	}

	/**
	 * 功能：编码字符串
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param data
	 *            源字符串
	 * @return String
	 */
	public static String encode(String data) {
		return new String(encode(data.getBytes()));
	}

	/**
	 * 功能：解码字符串
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param data
	 *            源字符串
	 * @return String
	 */
	public static String decode(String data) {
		return new String(decode(data.toCharArray()));
	}

	/**
	 * 功能：编码byte[]
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param enData
	 *            源
	 * @return char[]
	 */
	public static char[] encode(byte[] enData) {
		if (null == enData) {
			return new char[0];
		}
		char[] out = new char[((enData.length + 2) / 3) * 4];
		for (int i = 0, index = 0; i < enData.length; i += 3, index += 4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) enData[i]);
			val <<= 8;
			if ((i + 1) < enData.length) {
				val |= (0xFF & (int) enData[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < enData.length) {
				val |= (0xFF & (int) enData[i + 2]);
				quad = true;
			}
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index + 0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * 功能：解码
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param data
	 *            编码后的字符数组
	 * @return byte[]
	 */
	public static byte[] decode(char[] data) {

		int tempLen = data.length;
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || codes[data[ix]] < 0) {
				--tempLen; // ignore non-valid chars and padding
			}
		}
		// calculate required length:
		// -- 3 bytes for every 4 valid base64 chars
		// -- plus 2 bytes if there are 3 extra base64 chars,
		// or plus 1 byte if there are 2 extra.

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3) {
			len += 2;
		}
		if ((tempLen % 4) == 2) {
			len += 1;

		}
		byte[] out = new byte[len];

		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		// we now go through the entire array (NOT using the 'tempLen' value)
		for (int ix = 0; ix < data.length; ix++) {
			int value = (data[ix] > 255) ? -1 : codes[data[ix]];

			if (value >= 0) { // skip over non-code
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8) { // whenever there are 8 or more shifted in,
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
		}

		// if there is STILL something wrong we just have to throw up now!
		if (index != out.length) {
			throw new RuntimeException( "Miscalculated data length (wrote " + index
					+ " instead of " + out.length + ")");
		}

		return out;
	}

	/**
	 * 功能：编码文件
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param file
	 *            源文件
	 */
	public static void encode(File file) throws IOException {
		if (!file.exists()) {
			System.exit(0);
		} else {
			byte[] decoded = readBytes(file);
			if (null != decoded) {
				char[] encoded = encode(decoded);
				if (encoded.length > 0) {
					writeChars(file, encoded);
				}
			}
		}
	}

	/**
	 * 功能：解码文件。
	 * 
	 * @author
	 * @date 2014年07月03日
	 * @param file
	 *            源文件
	 * @throws IOException
	 */
	public static void decode(File file) throws IOException {
		if (!file.exists()) {
			System.exit(0);
		} else {
			char[] encoded = readChars(file);
			byte[] decoded = decode(encoded);
			writeBytes(file, decoded);
		}
	}

	//
	// code characters for values 0..63
	//
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
			.toCharArray();

	//
	// lookup table for converting base64 characters to value in range 0..63
	//
	private static byte[] codes = new byte[256];
	static {
		for (int i = 0; i < 256; i++) {
			codes[i] = -1;
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			codes[i] = (byte) (i - 'A');
		}

		for (int i = 'a'; i <= 'z'; i++) {
			codes[i] = (byte) (26 + i - 'a');
		}
		for (int i = '0'; i <= '9'; i++) {
			codes[i] = (byte) (52 + i - '0');
		}
		codes['+'] = 62;
		codes['/'] = 63;
	}

	private static byte[] readBytes(File file) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream fis = new FileInputStream(file);
				InputStream is = new BufferedInputStream(fis)) {
			int count = 0;
			byte[] buf = new byte[16384];
			while ((count = is.read(buf)) != -1) {
				if (count > 0) {
					baos.write(buf, 0, count);
				}
			}
			return baos.toByteArray();
		}
	}

	private static char[] readChars(File file) throws IOException {
		CharArrayWriter caw = new CharArrayWriter();
		try (Reader fr = new FileReader(file);
				Reader in = new BufferedReader(fr)) {
			int count = 0;
			char[] buf = new char[16384];
			while ((count = in.read(buf)) != -1) {
				if (count > 0) {
					caw.write(buf, 0, count);
				}
			}

		}
		return caw.toCharArray();
	}

	private static void writeBytes(File file, byte[] data) throws IOException {
		try (OutputStream fos = new FileOutputStream(file);
				OutputStream os = new BufferedOutputStream(fos)) {
			os.write(data);
		}
	}

	private static void writeChars(File file, char[] data) throws IOException {
		try (Writer fos = new FileWriter(file);
				Writer os = new BufferedWriter(fos)) {
			os.write(data);
		}
	}

}
