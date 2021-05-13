
//자바스크립트 객체

//Member클래스 생성(Member 변수)
Member = function(id,name,addr) {//자바로 하면 클래스 만들고, 인스턴스(변수) 만들고, 생성자 만듦
	
	this.id = id;
	this.name = name;
	this.addr = addr;
	
};

//prototype은 자바스크립트의 메소드 만들 때 문법
//클래스 안에 함수 정의(setter)
Member.prototype.setValue = function(id,name,addr) {//세터 만듦
	
	this.id = id;
	this.name = name;
	this.addr = addr;
	
};

//클래스 안에 함수 정의(getter)
Member.prototype.getValue = function() {//게터 만듦
	return "[" + this.id + "]" + this.name + "(" + this.addr + ")";
};
//;도 문법이다. 생략해도 에러 안난다.