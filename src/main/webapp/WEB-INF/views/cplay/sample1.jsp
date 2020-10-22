<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

  <style>
        div.item:nth-child(1) { background-image: url(/resources/img/m0.png); }
        div.item:nth-child(2) { background-image: url(/resources/img/mm1.png);}
        div.item:nth-child(3) { background-color: burlywood; }
    </style>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script>
        $(document).ready(function () {
            // 변수를 선언합니다.
            var width = $('[data-role="slider"]').attr('data-width');
            var height = $('[data-role="slider"]').attr('data-height');
            var count = $('[data-role="slider"] div.item').length;
            
            // 스타일을 적용합니다.
            $('[data-role="slider"]').css({
                position: 'relative',
                overflow: 'hidden',
                width: width,
                height: height
            }).find('.container').css({
                position: 'absolute',
                width: count * width,
                overflow: 'hidden'
            }).find('.item').css({
                width: width,
                height: height,
                float: 'left'
            });
            // 변수를 선언합니다.
            var currentPage = 0;
            var changePage = function () {
                $('[data-role="slider"] > .container').animate({
                    left: -currentPage * width
                }, 500);
            };
            // 이벤트를 연결합니다.
            $('#left-button').click(function () {
                if (currentPage > 0) {
                    // 왼쪽으로 이동
                    currentPage = currentPage - 1;
                    changePage();
                }
            });
            $('#right-button').click(function () {
                if (currentPage < count - 1) {
                    // 오른쪽으로 이동
                    currentPage = currentPage + 1;
                    changePage();
                }
            });
        });
    </script>
<%@include file="../includes/mheader.jsp"%>



<div data-role="slider" data-width="2120" data-height="500">
        <div class="container">
            <div class="item">
            </div>
            <div class="item">
            </div>
            <div class="item">
            </div>
        </div>
    </div>
    <div align="right">
    <button id="left-button">이전</button>
    <button id="right-button">다음</button>  
    </div>
    <div class="td-padding">   
           <h1> 영화 목록</h1>
    </div>
 
  <table>
  <tr>
  <td><img src="/resources/img/logogo.png"></td>
  </tr>
  <tr id="list1">
		<c:forEach items="${list}" var="board">
		<c:if test="${board.nw eq '넷플릭스'}">
 			<td class="td-padding"><a class='move' href='<c:out value="${board.title}"/>'> 
			<img src="/resources/img/<c:out value="${board.poster}" />"> 
			</a></td>
			</c:if>
		</c:forEach>
	</tr>
	<tr><td><button id="btn_r1">next</button></td></tr>
  </table>
 
  <table>
  <tr>
  <td><img src="/resources/img/what.png"></td>
  </tr>
  <tr id="list">
		<c:forEach items="${list}" var="board">
		<c:if test="${board.nw eq '왓챠'}">
 			<td class="td-padding"><a class='move' href='<c:out value="${board.title}"/>'> 
			<img src="/resources/img/<c:out value="${board.poster}" />"> 
			</a></td>
			</c:if>
		</c:forEach>
	</tr>
	<tr><td><button id="btn_r">next</button></td></tr>
  </table>
  <style>
    *{margin:0;padding:0;}
    ul,li{list-style:none;}
    .slide{height:300px;overflow:hidden;}
    .slide ul{width:calc(100% * 4);display:flex;animation:slide 10s infinite;} /* slide를 8초동안 진행하며 무한반복 함 */
    .slide li{width:calc(100% / 4);height:300px;}
    .slide li:nth-child(1){ background-image: url(/resources/img/m0.png);}
    .slide li:nth-child(2){ background-image: url(/resources/img/mm1.png);}
    .slide li:nth-child(3){background:#afa;}
    .slide li:nth-child(4){background:#aaf;}
    @keyframes slide {
      10% {margin-left:0;} /* 0 ~ 10  : 정지 */
      30% {margin-left:0;} /* 10 ~ 25 : 변이 */
      50% {margin-left:-100%;} /* 25 ~ 35 : 정지 */
      70% {margin-left:-100%;} /* 35 ~ 50 : 변이 */
      90% {margin-left:-200%;}
      110% {margin-left:-200%;}
      130% {margin-left:-300%;}
      150% {margin-left:-300%;}
      170% {margin-left:0;}
    }
  </style>
</head>
<body>
  <div class="slide">
    <ul>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
 <%@include file="../includes/mfooter.jsp"%>