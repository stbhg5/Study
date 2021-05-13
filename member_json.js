
//자바스크립트로 패키지도 만듦 - 자바스크립트는 형태가 굉장히 잘 변하고 가볍고 실행속도 빠르다.
var com = new Object();
com.util = new Object();
com.util.Member = function(id,name,addr) {//패키지 만듦
	
	this.id = id;
	this.name = name;
	this.addr = addr;
	
};

//JSON방식 - 데이터 전송 방식 : 굉장히 빠르다.
//[{KEY:VALUE,KEY:VALUE,KEY:VALUE,...}]
com.util.Member.prototype={//메소드 만듦
		
		//KEY:VALUE 형식
		setValue:function(id,name,addr) {
			
			this.id = id;
			this.name = name;
			this.addr = addr;
			
		},
		setId:function(id) {//id에만 값 초기화하는 세터
			this.id = id;
		},
		getValue:function() {
			return this.id + ":" + this.name + ":" + this.addr;
		},
		getId:function() {
			return this.id;
		},
		toString:function() {
			return this.id + ":" + this.name + ":" + this.addr;
		}
		
};
