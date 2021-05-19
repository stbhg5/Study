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

//<bean id="fileManager" class="com.util.FileManager"> 객체생성!!
@Service("fileManager")
public class FileManager {
	
	//스트러츠2 파일 업로드
	public static String doFileUpload(InputStream is,String originalFileName,String path) throws Exception {//파일 정보, 위치 지정
		
		String newFileName = null;
		
		if(is==null) {
			return null;
		}
		
		if(originalFileName.equals("")) {
			return null;
		}
		
		//파일 확장자 - .txt
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자, .부터 끝까지 저장
		
		if(fileExt==null || fileExt.equals("")) {//확장자가 null이면 파일 이름 잘못된것
			return null;
		}
		
		//새로운 파일 이름 지정 : 년월일시분초 - 이것만으론 파일이름 중복될 확률 있다.
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()); //1$ 인수 하나가지고 6개 전부 적용!!
		
		newFileName += System.nanoTime(); //10의 -9승, 년월일시분초 뒤에 시간붙이기
		newFileName += fileExt;
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdirs();
		}
		
		//파일까지의 경로
		String fullFilePath = path + File.separator + newFileName;
		
		FileOutputStream fos = new FileOutputStream(fullFilePath);
		
		int data = 0;
		
		byte[] buffer = new byte[1024];
		
		while((data=is.read(buffer, 0, 1024))!=-1) {
			fos.write(buffer, 0, data);
		}
		
		fos.close();
		
		return newFileName; //DB의 saveFileName에 저장하기 위함
		
	}
	
	//파일 다운로드
	public static boolean doFileDownload(HttpServletResponse response,String saveFileName,String originalFileName,String path) {
		
		try {
			
			//파일 경로 (꼭 saveFileName으로)
			String filePath = path + File.separator + saveFileName;
			
			if(originalFileName==null || originalFileName.equals("")) {//검증
				originalFileName=saveFileName;
			}
			//한글 깨짐 방지
			originalFileName = new String(originalFileName.getBytes("euc-kr"),"ISO-8859-1"); //8859_1
			
			File f = new File(filePath);
			
			//파일 있을때만 실행하겠다.
			if(!f.exists()) {
				return false;
			}
			
			//파일 타입
			response.setContentType("application/octet-stream"); //어플리케이션에서 만들어짐, octet-stream : xxx.xxx 즉 파일 이름과 확장자로 구성
			//octet-unknown도 가능하지만 윈도우에선 확장자 없는 파일을 취급 안하기에 잘 안씀
			
			response.setHeader("Content-disposition", "attachment;fileName=" + originalFileName);
			
			//파일 읽고 bis에 보냄, 파일 내린다.
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			//파일 내보내는 작업
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
	
	//파일 삭제
	public static void doFileDelete(String saveFileName,String path) {//파일 위치와 이름(saveFileName)만 가져오면 됨
		
		try {
			
			String filePath = path + File.separator + saveFileName;
			
			File f = new File(filePath);
			
			//물리적 파일 삭제
			if(f.exists()) {
				f.delete();
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}

}
