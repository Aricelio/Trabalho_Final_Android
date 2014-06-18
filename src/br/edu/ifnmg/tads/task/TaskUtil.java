package br.edu.ifnmg.tads.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TaskUtil {
	public static String converterInputStreamToString(InputStream is)
			throws IOException {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray(), "UTF-8");
	}		
}
