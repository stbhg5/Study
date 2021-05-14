package com.util;

import org.springframework.stereotype.Service;

//페이징 처리 클래스
@Service("myUtil")
public class MyUtil {
	
	//전체 페이지 수 구하기
	//numPerPage : 한 화면에 표시할 데이터의 갯수 (페이지수)
	//dataCount : 전체데이터 갯수
	public int getPageCount(int numPerPage, int dataCount) {//(3,34)
		
		int pageCount = 0; //페이지 수 (나눠진 전체 페이지 수 : 12페이지)
		
		//11.xx = 34/3
		pageCount = dataCount / numPerPage;
		
		//나머지가 있으면 페이지를 만들어라 (나머지 없으면 페이지 추가할 필요 없다.)
		if(dataCount % numPerPage != 0) {
			pageCount++;
		}
		//12
		return pageCount;
		
	}
	
	
	//페이징 처리 메소드
	//currentPage : 현재 표시할 페이지
	//totalPage : 전체 페이지 수
	//listUrl : 보여줄 링크를 설정할 url
	//매개변수(현재 보고자하는 페이지, 전체페이지, list.jsp)
	public String pageIndexList(int currentPage,int totalPage,String listUrl) {
		
		int numPerBlock = 5; //뿌려지는 페이지 목록 개수 (◀이전 6 7 8 9 10 ▶다음)
		int currentPageSetup; //◀이전,▶다음 눌렀을 때 값 (표시할 첫 페이지 - 1 한 값)
		int page; //포문 페이지 설정, 페이지로 뿌려질 숫자
		
		StringBuffer sb = new StringBuffer(); //스트링 반환값 쓰려고 버퍼에 누적
		
		if(currentPage==0||totalPage==0) {//검증 : 현재 페이지 또는 전체 페이지 없으면 멈춰
			return "";
		}
		
		//list.jsp
		//list.jsp?searchKey=subject&searchValue=1
		//?가 없으면 검색X, ?있다면 검색O
		if(listUrl.indexOf("?")!=-1) {//?가 있다면
			listUrl += "&"; //&붙여라
		}else {
			listUrl += "?"; //?붙여라
		}
		//값을 넘길 준비가 됨
		
		//이전페이지 번호 구하기
		//예시:◀이전 6 7 8 9 10 ▶다음 여기서 이전 눌르면 5페이지 나오도록 하는 공식!!
		//◀이전 눌렀을 때 = (현재 페이지 / 한 화면에 표시할 데이터의 갯수)*뿌려지는 페이지 목록 개수
		//currentPageSetup = 0아니면 5,10 ... 즉 이전페이지 눌렀을 때 가야할 페이지
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock; //컴퓨터 연산으론 3/5가 0이니 0*5는 0이다.
		
		//5로 나누어 떨어질뗀 - 5해서 0만드는 수식!!
		if(currentPage % numPerBlock == 0) { //currentPage가 10페이지 자리 수 일 때[(현재 페이지/뿌려지는 페이지 목록 개수) = 0] 나눈 나머지 0일때
			currentPageSetup = currentPageSetup - numPerBlock; //5=5-5
		}
		
		//◀이전 나타내기 위한 if문
		//1 2 3 4 5 페이지일 땐 이전 안 만들기 이것 빼고는 다 만든다.
		//12 > 5 && 0 > 0 - 이전 글자 안나옴 currentPageSetup=0 (이전페이지 눌렀을 때 가야할 페이지)
		//12 > 5 && 5 > 0 - 이전 글자 나옴 currentPageSetup=5
		if(totalPage > numPerBlock && currentPageSetup > 0) {//전체페이지 > 뿌려지는 페이지 목록 개수 &그리고& 이전 페이지 번호 > 0 (1 2 3 4 5는 이전 없기 때문)
			//\" 들어가는 값 \" 는 <a href=> 안의 ""를 문자로 표시한 것 //listUrl에 ?붙어있다.
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">◀이전</a>&nbsp;");
		
			//<a href="list.jsp?pageNum=5">◀이전</a>&nbsp; //6에서 이전했을 때
		}
		
		//바로가기 페이지
		//페이지 = 이전 페이지 + 1;
		//이전값은 정했으니 첫값 정하기 (1,6,...)
		page = currentPageSetup + 1; //★ 이전 바로 다음에 오는 숫자
		//여기서 무조건 currentPageSetup은 5의 배수
		
		//◀이전 6 7 8 9 10 ▶
		//5에서 6눌렀다고 가정
		//6 <= 12(전체 페이지) && 6(누른 페이지) <= (5(이전페이지 눌렀을 때 가야할 페이지)+5(페이지블럭))) 즉 페이지 6부터 시작해서 10까지 찍어라
		//페이지가 전체 데이터보다 작거나 같을때까지
		//찍은페이지 <= 전체페이지(12) && 찍은페이지 <= 이전페이지 눌렀을 때 가야할 페이지(5의 배수) + 페이지블럭(5)
		//최종페이지가 전체페이지만큼만 출력되도록 잡아줌.
		while(page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
		//(지금찍은 페이지6 <= 12 (여기까진 7개 출력되야 하는데)&& 6 <= (5+5)=10까지만 보게 설정
			//현재 내가본 페이지 색깔 바꾸기
			if(page==currentPage) {
				
				//누적
				sb.append("<font color=\"Fuchsia\">" + page + "</font>&nbsp;"); //내가보는 페이지는 색깔만 입히고 링크 안걸기
				//<font color="Fuchsia">2</font>&nbsp;
				
			}else {
				
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;"); //그 외는 링크 걸기 -> get방식으로 다이렉트로 전달하여 페이지 이동
				//<a href="list.jsp?pageNum=9">9</a>&nbsp;
			}
			
			//계속 증가
			page++;
			
		}//빠져나오면 숫자는 1~5,6~10,.. 찍음
		
		//▶다음 찍어줄지 말지, 최종페이지인지 체크
		//예시:◀이전 6 7 8 9 10 ▶다음
		//◀이전 11 12 는 더 만들어지면 안되니
		//(전체 페이지 - 이전페이지 눌렀을 때 가야할 페이지 > 페이지블럭)
		//(12-5) > 5 - 다음O
		//(12-10) >5 - 다음X
		if(totalPage - currentPageSetup > numPerBlock) {
			
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">▶다음</a>&nbsp;");
			//<a href="list.jsp?pageNum=11">▶다음</a>&nbsp;
		}
		//5,10,... 이상이면 빠져나와서 ▶다음 링크 건다
		//즉 11,12 이후 ▶다음 나올지 유무를 적용하는 곳
		return sb.toString(); //스트링 버퍼를 스트링으로 변환!!
		
	}
	
	
	//자바스크립트로 페이징 처리
	public String pageIndexList(int currentPage,int totalPage) {
		
		int numPerBlock = 5;
		int currentPageSetup;
		
		//int n;
		int page;
		String strList = "";
		
		if(currentPage==0) {
			return "";
		}
		
		//표시할 첫 페이지
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock; //공식
		
		if(currentPage%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}
		
		//◀이전
		//n = currentPage - numPerBlock;
		//n = currentPageSetup;
		if(totalPage>numPerBlock && currentPageSetup>0) {
			strList += "<a onclick='listPage(" + currentPageSetup + ");'>◀이전</a>&nbsp;"; //자바스크립트 함수실행
		}
		
		//바로가기 페이지
		page = currentPageSetup + 1;
		
		while((page<=totalPage) && (page<=currentPageSetup+numPerBlock)) {
			
			if(page==currentPage) {
				strList += "<font color='Fuchsia'>" + page + "</font>&nbsp;";
			}else {
				strList += "<a onclick='listPage(" + page + ");'>" + page + "</a>&nbsp;";
			}
			
			page++;
			
		}
		
		//다음▶
		//n = currentPage + numPerBlock + 1;
		//n = currentPageSetup + numPerBlock + 1;
		if(totalPage-currentPageSetup > numPerBlock) {
			strList += "<a onclick='listPage(" + page + ");'>다음▶</a>&nbsp;";
		}
		
		return strList;
		
	}

}
