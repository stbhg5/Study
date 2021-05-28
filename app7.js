
//http://localhost:3000/public/file.html
//파일업로드

//익스프레스 모듈
var express = require("express");
var http = require("http");
var path = require("path");

//var bodyParser = require("body-parser"); //POST방식 시 반드시 필요 -> express에 내장됨!!
var serveStatic = require("serve-static"); //특정 폴더로 접근 가능하게 함
var expressErrorHandler = require("express-error-handler");
var cookieParser = require("cookie-parser");
var expressSession = require("express-session");

//파일 업로드 모듈
var multer = require("multer");

//익스프레스 객체 생성
var app = express(); //app가 익스프레스 서버다.

//기본 포트를 app객체에 속성으로 설정
//express 객체의 메소드 (set,get,use)
app.set("port",process.env.PORT || 3000); //환경설정에 포트번호 있으면 쓰고 없으면 3000써라.

//미들웨어 추가
//bodyParser가 express로 들어감!!
app.use(express.urlencoded({extended: true})); //false : <form enctype="application/x-www-form-urlencoded"> 쓰겠다.

app.use(express.json()); //JSON형태 데이터 읽을 수 있게 하겠다. : JSON데이터 파싱

//사용할 주소 /public
app.use("/public",serveStatic(path.join(__dirname,"public"))); //주소합치기, 폴더명과 폴더명 앞의 주소명 합쳐서 하나로 처리
app.use("/upload",serveStatic(path.join(__dirname,"uploads"))); //파일 업로드 할 위치, 폴더명 일치

//storage의 저장 기준
var storage = multer.diskStorage({
	
	destination: function(req,file,callback) {
		
		callback(null,"uploads"); //uploads 폴더
		
	},
	filename: function(req,file,callback) {
		
		var extension = path.extname(file.originalname); //파일 originalname에서 확장자 빼내라
		var basename = path.basename(file.originalname,extension); //파일이름
		
		callback(null,basename + extension); //abc.txt
		//callback(null,originalname + extension); //abc.txt
		//callback(null,basename + Date.now() + extension); //abc210527055311.txt
		//callback(null,Date.now().toString() + path.extname(file.originalname)); //abc210527055311.txt
		
	}
	
});

//storage 기준으로 upload
var upload = multer({
	
	storage : storage, //위 storage 저장소 방법으로 저장
	limits : {
		files:10, //파일 업로드 개수 10개
		fileSize:1024*1024*1024 //1GB
	}
	
});

app.use(cookieParser());

app.use(expressSession({
	secret: "my key", //아무거나 써도 됨 - 가져가서 암호화 시킴
	resave: true, //일반적으로 true
	saveUninitialized: true //일반적으로 true - 세션 공간 초기화
}));

//라우터 객체 - 만든 이유는 이러한 주소형태 쓰기 위해서 - 지우면 안됨
var router = express.Router();

//추가 (파일2개 업로드)
router.route("/process/file").post(upload.array("upload",2),function(req,res) {//업로드 위치<input type="file" name="upload"/> 이름동일, 파일 2개
	
	console.log("file 호출..");
	
	try {
		
		var files = req.files; //파일정보를 배열로 받음
		
		console.dir(req.files[0]); //파일 정보, 문자와 섞어쓰지 말기
		console.dir(req.files[1]); //추가 (파일2개 업로드)
		
		//파일정보를 저장할 변수 선언
		var originalName = "";
		var fileName = "";
		var mimeType = ""; //파일 타입
		var size = 0;
		
		//자리옮김 (파일2개 업로드)
		res.writeHead("200",{"Content-Type":"text/html;charset=utf-8"});
		res.write("<h3>파일 업로드 성공</h3>");
		
		if(Array.isArray(files)) {//배열에 파일이 있는 경우
			
			for(var i=0;i<files.length;i++) {//반복문으로 파일 읽음
				
				originalName = files[i].originalname;
				fileName = files[i].filename;
				mimeType = files[i].mimetype;
				size = files[i].size;
				
				//자리옮김 (파일2개 업로드)
				res.write("<hr/>");
				res.write("<div>원본파일명: " + originalName + "</div>");
				res.write("<div>저장파일명: " + fileName + "</div>");
				res.write("<div>MIMEType: " + mimeType + "</div>");
				res.write("<div>파일크기: " + size + "</div>");
				
			}
			
		}
		
		/*
		res.writeHead("200",{"Content-Type":"text/html;charset=utf-8"});
		res.write("<h3>파일 업로드 성공</h3>");
		res.write("<hr/>");
		res.write("<div>원본파일명: " + originalName + "</div>");
		res.write("<div>저장파일명: " + fileName + "</div>");
		res.write("<div>MIMEType: " + mimeType + "</div>");
		res.write("<div>파일크기: " + size + "</div>");
		*/
		res.end();
		
	} catch (e) {
		console.dir(e.stack);
	}
	
});


//미들웨어가 하는 것을 라우터가 하지만 미들웨어 없어선 안된다.
//라우터 객체를 app에 등록
app.use("/",router);

//404에러 페이지
var errorHandler = expressErrorHandler({
	
	static:{//404에러나면 html 찾아가라
		'404' : "./public/404.html"
	}
	
});

app.use(expressErrorHandler.httpError(404));
app.use(errorHandler);

//Express서버 시작
http.createServer(app).listen(app.get("port"),function() {
	console.log("Express 서버를 시작했습니다 : " + app.get("port"));
});
