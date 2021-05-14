<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	//http://localhost:8080/st2spr/jQuery/test6.jsp
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  
  <!-- 모니터에 따라 사이즈 바뀐다. 없으면 치우쳐짐 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <!-- 아래 3개 필수 -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

  <style>
	 .carousel-inner > .item > img,
 	 .carousel-inner > .item > a > img {
   	  	 width: 100%; 
     	 margin: auto;
  		}
  </style>

</head>
<body>

<div class="container">
  <h2>Carousel Example</h2>  
  <div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
      <li data-target="#myCarousel" data-slide-to="3"></li>
    </ol>

    <!-- Wrapper for slides -->
    <!-- 사진넣기 -->
    <!-- img태그 안 style="width:100%;" -->
    <div class="carousel-inner">
      <div class="item active">
        <img src="./image/suzi.jpg" alt="Los Angeles" >
        <div class="carousel-caption">
       		<h3>배수지</h3>
       		<p>한국 걸그룹</p>
        </div>
      </div>

      <div class="item">
        <img src="./image/sulhyun.jpg" alt="Chicago" >
        <div class="carousel-caption">
       		<h3>설현</h3>
       		<p>한국 걸그룹</p>
        </div>
      </div>
    
      <div class="item">
        <img src="./image/hyoju.jpg" alt="New york" >
        <div class="carousel-caption">
       		<h3>한효주</h3>
       		<p>연예인</p>
        </div>
      </div>
      
      <div class="item">
        <img src="./image/nakyung.jpg" alt="New york" >
        <div class="carousel-caption">
       		<h3>나경</h3>
       		<p>방송인</p>
        </div>
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</div>

</body>
</html>