
//XMLHttpRequest 객체 생성
function getXMLHttpRequest() {
	
	if(window.ActiveXObject) {
		
		try {
			return new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	}else if(window.XMLHttpRequest) {
		return new XMLHttpRequest();
	}
	
}

//Ajax 요청
var httpRequest = null;

function sendRequest(url,params,callback,method) {//주소,데이터,돌아갈메소드,사용자가 넘겨준 메소드
	
	httpRequest = getXMLHttpRequest(); //객체 만들기
	
	//method 처리
	var httpMethod = method? method : "GET";
	
	if(httpMethod!="GET" && httpMethod!="POST") {
		httpMethod = "GET";
	}
	
	//data 처리
	var httpParams = (params==null || params=="")? null : params;
	//var httpParams = params ? params : null;
	
	//url 처리
	var httpUrl = url;
	
	if(httpMethod=="GET" && httpParams!=null) {
		httpUrl += "?" + httpParams;
	}
	
	httpRequest.open(httpMethod, httpUrl, true);
	httpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	httpRequest.onreadystatechange = callback;
	httpRequest.send(httpMethod=="POST"? httpParams : null);
	
}