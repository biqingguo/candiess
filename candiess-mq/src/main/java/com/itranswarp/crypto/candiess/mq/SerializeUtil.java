package com.itranswarp.crypto.candiess.mq;

import java.io.*;

/**
 * Created by wanggen on 15/10/22.
 */
public class SerializeUtil {
	/**
	 * 序列化
	 * 
	 * @param object
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T extends Serializable> byte[] serialize(T object) throws IOException {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T deserialize(byte[] bytes, Class<?> clazz) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			if (null == obj) {
				return null;
			}
			if (clazz.isAssignableFrom(obj.getClass())) {
				return (T) obj;
			}
			return null;

		} finally {
			if (null != ois) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ois = null;
			}
			if (null != bais) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bais = null;
			}
		}
	}
}