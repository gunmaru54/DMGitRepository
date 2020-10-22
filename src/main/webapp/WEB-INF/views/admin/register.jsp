<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/css/ex10.css" rel="stylesheet">
    <link href="/resources/css/style.css" rel="stylesheet">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap theme -->
<link href="/resources/css/bootstrap-theme.css" rel="stylesheet">
<!--external css-->
<!-- font icon -->
<!-- Custom styles -->

<link href="/resources/css/style-responsive.css" rel="stylesheet" />
</head>
<body class="regbody">
	
	<div class="container">
		<form class="login-form" action="/admin/register" method="post" role="form">
		<h1  style="color: black; margin-left: 20px; padding-top: 20px;">영화 등록</h1>
			<div class="login-wrap">
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">영화 제목<span
						class="required">*</span></label> <input class=" form-control" id="mov_title"
						name="mov_title" type="text" readonly="readonly" placeholder="**파일첨부시 추가됨" >
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">영화 썸네일<span
						class="required">*</span></label> <input class=" form-control" id="mov_thumbnail"
						name="mov_thumbnail" type="text" placeholder="**파일첨부시 추가됨" readonly="readonly"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">장르<span
						class="required">*</span></label> <input class=" form-control" id="mov_genre"
						name="mov_genre" type="text" placeholder="GENRE"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">감독<span
						class="required">*</span></label> <input class=" form-control" id="mov_director"
						name="mov_director" type="text" placeholder="DIRECTOR"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">배우<span
						class="required">*</span></label> <input class=" form-control" id="mov_actor"
						name="mov_actor" type="text" placeholder="ACTOR"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">출시국<span
						class="required">*</span></label> <input class=" form-control" id="mov_nation"
						name="mov_nation" type="text" placeholder="NATION"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">개봉일<span
						class="required">*</span></label> <input class=" form-control" id="mov_opday"
						name="mov_opday" type="text" placeholder="RELEASE DATE"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">상영 시간<span
						class="required">*</span></label> <input class=" form-control" id="mov_running_time"
						name="mov_running_time" type="text" placeholder="RUNNING TIME"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">평점<span
						class="required">*</span></label> <input class=" form-control" id="mov_point"
						name="mov_point" type="text" placeholder="RATINGS"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_gender" class="control-label">영화 플랫폼<span
						class="required">*</span>
						<div class="radio">
							<label><input type="radio" name="mov_platform" value="왓챠" checked>왓챠</label>
						</div>
						<div class="radio">
							<label><input type="radio" name="mov_platform" value="넷플릭스">넷플릭스</label>
						</div>
					</label>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">영화 메인 이미지<span
						class="required">*</span></label> <input class=" form-control" id="upload_mov_detailImg"
						name="upload_mov_detailImg" type="file" />
					<div class="eheck_font" id="id_check"></div>
					<div class='uploadResult'>
						<ul></ul>
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">영화 썸네일 이미지<span
						class="required">*</span></label> 
						<input class=" form-control" id="upload_mov_thumbnail" name="upload_mov_thumbnail" type="file" />
					<div class="eheck_font" id="id_check"></div>
					<div class='uploadResult_Thumbnail'>
						<ul></ul>
					</div>
				</div>
				<div class="form-group col-lg-12">
					<label for="mem_id" class="control-label">비디오<span
						class="required">*</span></label> <input class=" form-control" id="mov_clip"
						name="mov_clip" type="text" placeholder="VIDEO CLIP"/>
					<div class="eheck_font" id="id_check"></div>
				</div>
				
				<div class="form-group">
					<button class="btn btn-primary btn-lg btn-block" type="submit" id="mov_infoRegBtn">등록</button>
					<button class="btn btn-info btn-lg btn-block" id="adminCancelBtn" type="button">취소</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" id="mov_detailImg" name="mov_detailImg" /> <!-- 상세 이미지 값을 보내줌 -->
			</div>	
		</form>
	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>
	
<script type="text/javascript">
$(document).ready(function() {
	var formObj = $("form[role='form']");
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5232880; //5MB
	
	//파일 업로드 유효성 검사 함수
	function checkExtension(fileName, fileSize) {
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	$("#adminCancelBtn").on("click", function() {
		self.location = "/cplay/mlist";
	});
	
	//파일 선택 버튼이 눌리면 영화명을 채울 수 있다.
	$("#upload_mov_detailImg").on("change", function() {
		var upload_mov_detailImg = $(this).val().replace(/c:\\fakepath\\/i,'');
		upload_mov_detailImg = upload_mov_detailImg.substring(0, upload_mov_detailImg.lastIndexOf('.')); // 확장자 제거
		$("#mov_title").val(upload_mov_detailImg);
		$("#mov_detailImg").val(upload_mov_detailImg);
    });
	
	$("#upload_mov_thumbnail").on("change", function() {
		var upload_mov_thumbnail = $(this).val().replace(/c:\\fakepath\\/i,'');
		$("#mov_thumbnail").val(upload_mov_thumbnail);
    });
	
/* 상세이미지 파일만 업로드를 한다. */
	$("#upload_mov_detailImg").change(function(e) {
		// FormData는 페이지 전환 없이 폼 데이터를 제출하고 싶을 때 사용한다, **유의점: formData는 객체를 넣으면 무시된다.
		var formData = new FormData();
		
		// $("요소 선택[속성=값]") :: $("li[title='리스트']")  :: <li> 요소 중 title 속성값이 '리스트'인 요소만 선택
		// 즉, <input> 요소 중 name이 'upload_mov_detailImg'인 요소를 선택 
		var inputFile = $("input[name = 'upload_mov_detailImg']");
		
		// input으로 업로드된 파일들을 배열로 지정
		var files = inputFile[0].files;
		
		// 파일 유효성 검사 (파일 사이즈, 확장자)
		for(var i = 0; i < files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			// append 메소드로 키-값 형식을 지정
			formData.append("upload_mov_detailImg", files[i]);
		}
		
		// 파일 데이터 Ajax로 비동기 처리 (비동기 처리: 특정 로직의 실행이 끝날 때까지 기다려주지 않고 나머지 코드를 먼저 실행하는 것)
		$.ajax({
			// 해당 컨트롤러로 이동
			url: '/admin/uploadAjaxAction',
			// ajax 통신을 통해 데이터를 전송할때, 기본적으로 key와 value값을 Query String으로 변환해서 보낸다. 
			// 데이터 값에 따라 (key=value&key=value) 또는 (key:value, key:value) 이런 식으로 보내게 되는데 , false는  Query String 형식으로 보내지 않는다.
			processData: false,
			// 서버에 데이터를 보낼 때 사용하는데, content type 헤더의 값입니다. ==> 여기선 "false"값으로 헤더설정을 하지 않음.
			contentType: false,
			// 전송할 데이터
			data: formData,
			
			/* 스프링 시큐리티가 적용되어 특정 URL로 POST방식으로 403에러가 난다. 이유는 csrf.token값이 누락되어서 발생하는 문제이다. 
			그렇기 때문에 아래와 같이 데이터를 전송하기 전에 헤더에 csrf값을 설정한다. */
			beforeSend: function(xhr) {
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); 
			},
			// POST 방식으로 데이터 전송
			type: 'POST',
			// 전송할 데이터 타입
			dataType: 'json',
			// 데이터 전송이 성공적으로 이루어졌을 때, 작동하는 부분
			success: function(result) {
				console.log("result :" + result);
				//업로드 된 결과(=이미지)를 화면상에 띄움
				showUploadResult(result);
			}
		}); //$.ajax
	});
	
	/* 썸네일 이미지 등록 */
	$("#upload_mov_thumbnail").change(function(e) {
		
		var formData = new FormData();
		
		var inputFile = $("input[name = 'upload_mov_thumbnail']");
		
		var files = inputFile[0].files;
		
		for(var i = 0; i < files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			formData.append("upload_mov_thumbnail", files[i]);
		}
		
		$.ajax({
			url: '/admin/uploadAjaxAction_Thumbnail',
			processData: false,
			contentType: false,
			data: formData,
			beforeSend: function(xhr) {
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); 
			},
			type: 'POST',
			dataType: 'json',
			success: function(result) {
				console.log("result :" + result);
				showUploadResult(result); //업로드 결과 이미지를 띄움
			}
		}); //$.ajax
	});
	
});

//썸네일 화면
var uploadResult = $(".uploadResult ul");
var uploadResult_Thumbnail = $(".uploadResult_Thumbnail ul");

function showUploadResult(uploadResultArr) {

var str = "";

$(uploadResultArr).each(function(i, obj) {
	//$('#mov_title').val(obj.mov_title); //컨트롤러에 전달된 이미지 파일명만 추출해와서 input 태그에 넣어준다.
	
	console.log("obj.mov_title: " + obj.mov_title);
	console.log("obj.mov_thumbnail: " + obj.mov_thumbnail);
	
	//영화 제목이 있다면  영화 디테일 이미지를 첨부한다.
	if(obj.mov_title != null){ 
		str = "";
		// 썸네일 이미지를 가지고 온다.
		var fileCallPath = encodeURIComponent("s_" + obj.mov_title);
		console.log("fileCallPath: " + fileCallPath);
		
		// 화면상에 이미지를 띄우기 위한 html 코드 작성
		str += "<li><img src='/admin/display?upload_mov_detailImg="
				+ fileCallPath
				+ "'></a>"
				+ "<span data-file=\'" + fileCallPath + "\' data-type='image'> X </span>"
				+ "</div></li>";
		
		$(".uploadResult").on("click", "span" ,function(e) {
				var targetFile = $(this).data("file");
				console.log("targetFile: " + targetFile);
				var type = $(this).data("type");
				console.log("type: " + type);
				var targetLi = $(this).closest("li");
				
				$.ajax({
					url : '/admin/deleteFile',
					data : {fileName : targetFile, type : type},
					dataType : 'text',
					beforeSend: function(xhr) {
						xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); 
					},
					type : 'POST',
					success : function(result) {
						alert(result);
						targetLi.remove();
					}
				});
			});
		uploadResult.append(str);
		}else{ // 영화제목이 없다면(썸네일)
		str = "";	
		var fileCallPath = encodeURIComponent("s_" + obj.mov_thumbnail); // 썸네일 이미지를 가지고 온다.
			console.log("fileCallPath: " + fileCallPath);
			
			str += "<li><img src='/admin/display_Thumbnail?upload_mov_thumbnail=" // upload_mov_detailImg는 컨트롤러의 변수값과 같아야 한다, 컨트롤러에 접근을 하여 이미지를 불러온다.  p.526에 참고하면 된다.
					+ fileCallPath
					+ "'></a>"
					+ "<span data-file=\'" + fileCallPath + "\' data-type='image'> X </span>"
					+ "</div></li>";
		
			$(".uploadResult_Thumbnail").on("click", "span" ,function(e) {
						var targetFile = $(this).data("file");
						var type = $(this).data("type");
						
						var targetLi = $(this).closest("li");
						
						console.log(targetFile);
						$.ajax({
							url : '/admin/deleteFile_Thumbnail',
							data : {fileName : targetFile, type : type},
							dataType : 'text',
							beforeSend: function(xhr) {
								xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}"); 
							},
							type : 'POST',
							success : function(result) {
								alert(result);
								targetLi.remove();
							}
						});
					});
			uploadResult_Thumbnail.append(str);
			}
	});
}
</script>
</body>
</html>