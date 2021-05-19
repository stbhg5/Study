package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

//<bean id="fileManager" class="com.util.FileManager"> ��ü����!!
@Service("fileManager")
public class FileManager {
	
	//��Ʈ����2 ���� ���ε�
	public static String doFileUpload(InputStream is,String originalFileName,String path) throws Exception {//���� ����, ��ġ ����
		
		String newFileName = null;
		
		if(is==null) {
			return null;
		}
		
		if(originalFileName.equals("")) {
			return null;
		}
		
		//���� Ȯ���� - .txt
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")); //Ȯ����, .���� ������ ����
		
		if(fileExt==null || fileExt.equals("")) {//Ȯ���ڰ� null�̸� ���� �̸� �߸��Ȱ�
			return null;
		}
		
		//���ο� ���� �̸� ���� : ����Ͻú��� - �̰͸����� �����̸� �ߺ��� Ȯ�� �ִ�.
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()); //1$ �μ� �ϳ������� 6�� ���� ����!!
		
		newFileName += System.nanoTime(); //10�� -9��, ����Ͻú��� �ڿ� �ð����̱�
		newFileName += fileExt;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdirs();
		}
		
		//���ϱ����� ���
		String fullFilePath = path + File.separator + newFileName;
		
		FileOutputStream fos = new FileOutputStream(fullFilePath);
		
		int data = 0;
		
		byte[] buffer = new byte[1024];
		
		while((data=is.read(buffer, 0, 1024))!=-1) {
			fos.write(buffer, 0, data);
		}
		
		fos.close();
		
		return newFileName; //DB�� saveFileName�� �����ϱ� ����
		
	}
	
	//���� �ٿ�ε�
	public static boolean doFileDownload(HttpServletResponse response,String saveFileName,String originalFileName,String path) {
		
		try {
			
			//���� ��� (�� saveFileName����)
			String filePath = path + File.separator + saveFileName;
			
			if(originalFileName==null || originalFileName.equals("")) {//����
				originalFileName=saveFileName;
			}
			//�ѱ� ���� ����
			originalFileName = new String(originalFileName.getBytes("euc-kr"),"ISO-8859-1"); //8859_1
			
			File f = new File(filePath);
			
			//���� �������� �����ϰڴ�.
			if(!f.exists()) {
				return false;
			}
			
			//���� Ÿ��
			response.setContentType("application/octet-stream"); //���ø����̼ǿ��� �������, octet-stream : xxx.xxx �� ���� �̸��� Ȯ���ڷ� ����
			//octet-unknown�� ���������� �����쿡�� Ȯ���� ���� ������ ��� ���ϱ⿡ �� �Ⱦ�
			
			response.setHeader("Content-disposition", "attachment;fileName=" + originalFileName);
			
			//���� �а� bis�� ����, ���� ������.
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			//���� �������� �۾�
			OutputStream out = response.getOutputStream();
			
			int data;
			byte[] buffer = new byte[4096];
			
			while((data=bis.read(buffer, 0, 4096))!=-1) {
				out.write(buffer,0,data);
			}
			
			out.flush();
			out.close();
			bis.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		
		return true;
		
	}
	
	//���� ����
	public static void doFileDelete(String saveFileName,String path) {//���� ��ġ�� �̸�(saveFileName)�� �������� ��
		
		try {
			
			String filePath = path + File.separator + saveFileName;
			
			File f = new File(filePath);
			
			//������ ���� ����
			if(f.exists()) {
				f.delete();
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}
