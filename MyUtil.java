package com.util;

import org.springframework.stereotype.Service;

//����¡ ó�� Ŭ����
@Service("myUtil")
public class MyUtil {
	
	//��ü ������ �� ���ϱ�
	//numPerPage : �� ȭ�鿡 ǥ���� �������� ���� (��������)
	//dataCount : ��ü������ ����
	public int getPageCount(int numPerPage, int dataCount) {//(3,34)
		
		int pageCount = 0; //������ �� (������ ��ü ������ �� : 12������)
		
		//11.xx = 34/3
		pageCount = dataCount / numPerPage;
		
		//�������� ������ �������� ������ (������ ������ ������ �߰��� �ʿ� ����.)
		if(dataCount % numPerPage != 0) {
			pageCount++;
		}
		//12
		return pageCount;
		
	}
	
	
	//����¡ ó�� �޼ҵ�
	//currentPage : ���� ǥ���� ������
	//totalPage : ��ü ������ ��
	//listUrl : ������ ��ũ�� ������ url
	//�Ű�����(���� �������ϴ� ������, ��ü������, list.jsp)
	public String pageIndexList(int currentPage,int totalPage,String listUrl) {
		
		int numPerBlock = 5; //�ѷ����� ������ ��� ���� (������ 6 7 8 9 10 ������)
		int currentPageSetup; //������,������ ������ �� �� (ǥ���� ù ������ - 1 �� ��)
		int page; //���� ������ ����, �������� �ѷ��� ����
		
		StringBuffer sb = new StringBuffer(); //��Ʈ�� ��ȯ�� ������ ���ۿ� ����
		
		if(currentPage==0||totalPage==0) {//���� : ���� ������ �Ǵ� ��ü ������ ������ ����
			return "";
		}
		
		//list.jsp
		//list.jsp?searchKey=subject&searchValue=1
		//?�� ������ �˻�X, ?�ִٸ� �˻�O
		if(listUrl.indexOf("?")!=-1) {//?�� �ִٸ�
			listUrl += "&"; //&�ٿ���
		}else {
			listUrl += "?"; //?�ٿ���
		}
		//���� �ѱ� �غ� ��
		
		//���������� ��ȣ ���ϱ�
		//����:������ 6 7 8 9 10 ������ ���⼭ ���� ������ 5������ �������� �ϴ� ����!!
		//������ ������ �� = (���� ������ / �� ȭ�鿡 ǥ���� �������� ����)*�ѷ����� ������ ��� ����
		//currentPageSetup = 0�ƴϸ� 5,10 ... �� ���������� ������ �� ������ ������
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock; //��ǻ�� �������� 3/5�� 0�̴� 0*5�� 0�̴�.
		
		//5�� ������ �������� - 5�ؼ� 0����� ����!!
		if(currentPage % numPerBlock == 0) { //currentPage�� 10������ �ڸ� �� �� ��[(���� ������/�ѷ����� ������ ��� ����) = 0] ���� ������ 0�϶�
			currentPageSetup = currentPageSetup - numPerBlock; //5=5-5
		}
		
		//������ ��Ÿ���� ���� if��
		//1 2 3 4 5 �������� �� ���� �� ����� �̰� ����� �� �����.
		//12 > 5 && 0 > 0 - ���� ���� �ȳ��� currentPageSetup=0 (���������� ������ �� ������ ������)
		//12 > 5 && 5 > 0 - ���� ���� ���� currentPageSetup=5
		if(totalPage > numPerBlock && currentPageSetup > 0) {//��ü������ > �ѷ����� ������ ��� ���� &�׸���& ���� ������ ��ȣ > 0 (1 2 3 4 5�� ���� ���� ����)
			//\" ���� �� \" �� <a href=> ���� ""�� ���ڷ� ǥ���� �� //listUrl�� ?�پ��ִ�.
			sb.append("<a href=\"" + listUrl + "pageNum=" + currentPageSetup + "\">������</a>&nbsp;");
		
			//<a href="list.jsp?pageNum=5">������</a>&nbsp; //6���� �������� ��
		}
		
		//�ٷΰ��� ������
		//������ = ���� ������ + 1;
		//�������� �������� ù�� ���ϱ� (1,6,...)
		page = currentPageSetup + 1; //�� ���� �ٷ� ������ ���� ����
		//���⼭ ������ currentPageSetup�� 5�� ���
		
		//������ 6 7 8 9 10 ��
		//5���� 6�����ٰ� ����
		//6 <= 12(��ü ������) && 6(���� ������) <= (5(���������� ������ �� ������ ������)+5(��������))) �� ������ 6���� �����ؼ� 10���� ����
		//�������� ��ü �����ͺ��� �۰ų� ����������
		//���������� <= ��ü������(12) && ���������� <= ���������� ������ �� ������ ������(5�� ���) + ��������(5)
		//������������ ��ü��������ŭ�� ��µǵ��� �����.
		while(page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
		//(�������� ������6 <= 12 (������� 7�� ��µǾ� �ϴµ�)&& 6 <= (5+5)=10������ ���� ����
			//���� ������ ������ ���� �ٲٱ�
			if(page==currentPage) {
				
				//����
				sb.append("<font color=\"Fuchsia\">" + page + "</font>&nbsp;"); //�������� �������� ���� ������ ��ũ �Ȱɱ�
				//<font color="Fuchsia">2</font>&nbsp;
				
			}else {
				
				sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a>&nbsp;"); //�� �ܴ� ��ũ �ɱ� -> get������� ���̷�Ʈ�� �����Ͽ� ������ �̵�
				//<a href="list.jsp?pageNum=9">9</a>&nbsp;
			}
			
			//��� ����
			page++;
			
		}//���������� ���ڴ� 1~5,6~10,.. ����
		
		//������ ������� ����, �������������� üũ
		//����:������ 6 7 8 9 10 ������
		//������ 11 12 �� �� ��������� �ȵǴ�
		//(��ü ������ - ���������� ������ �� ������ ������ > ��������)
		//(12-5) > 5 - ����O
		//(12-10) >5 - ����X
		if(totalPage - currentPageSetup > numPerBlock) {
			
			sb.append("<a href=\"" + listUrl + "pageNum=" + page + "\">������</a>&nbsp;");
			//<a href="list.jsp?pageNum=11">������</a>&nbsp;
		}
		//5,10,... �̻��̸� �������ͼ� ������ ��ũ �Ǵ�
		//�� 11,12 ���� ������ ������ ������ �����ϴ� ��
		return sb.toString(); //��Ʈ�� ���۸� ��Ʈ������ ��ȯ!!
		
	}
	
	
	//�ڹٽ�ũ��Ʈ�� ����¡ ó��
	public String pageIndexList(int currentPage,int totalPage) {
		
		int numPerBlock = 5;
		int currentPageSetup;
		
		//int n;
		int page;
		String strList = "";
		
		if(currentPage==0) {
			return "";
		}
		
		//ǥ���� ù ������
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock; //����
		
		if(currentPage%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}
		
		//������
		//n = currentPage - numPerBlock;
		//n = currentPageSetup;
		if(totalPage>numPerBlock && currentPageSetup>0) {
			strList += "<a onclick='listPage(" + currentPageSetup + ");'>������</a>&nbsp;"; //�ڹٽ�ũ��Ʈ �Լ�����
		}
		
		//�ٷΰ��� ������
		page = currentPageSetup + 1;
		
		while((page<=totalPage) && (page<=currentPageSetup+numPerBlock)) {
			
			if(page==currentPage) {
				strList += "<font color='Fuchsia'>" + page + "</font>&nbsp;";
			}else {
				strList += "<a onclick='listPage(" + page + ");'>" + page + "</a>&nbsp;";
			}
			
			page++;
			
		}
		
		//������
		//n = currentPage + numPerBlock + 1;
		//n = currentPageSetup + numPerBlock + 1;
		if(totalPage-currentPageSetup > numPerBlock) {
			strList += "<a onclick='listPage(" + page + ");'>������</a>&nbsp;";
		}
		
		return strList;
		
	}

}
