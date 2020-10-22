<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/mheader.jsp"%>

	 <div class="slide" style="top:60px;">
    <input type="radio" name="pos" id="pos1" checked>
    <input type="radio" name="pos" id="pos2">
    <input type="radio" name="pos" id="pos3">
    <input type="radio" name="pos" id="pos4">
    <input type="radio" name="pos" id="pos5">
    <input type="radio" name="pos" id="pos6">
    <ul>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
    <p class="bullet">
      <label for="pos1">1</label>
      <label for="pos2">2</label>
      <label for="pos3">3</label>
      <label for="pos4">4</label>
      <label for="pos5">5</label>
      <label for="pos6">6</label>
    </p>
  </div>
<hr>
<div class="td-padding" style="margin-top:35px;">
	<h1>영화 목록</h1>
</div>
<hr>
<section class=".nm-collection-row">
<div class="nm-content-horizontal-row">
<div class="left">
	<button id="btn_r3"><span class="nf-svg-icon "><svg viewBox="0 0 18 18" xmlns="http://www.w3.org/2000/svg">
	<path fill="currentColor" d="M11.56 5.56L10.5 4.5 6 9l4.5 4.5 1.06-1.06L8.12 9z">
	</path></svg></span></button>
	<div class="nm-content-horizontal-row-gradient"></div>
</div>
<table class="nm-content-horizontal-row-item-container">
	<tr>
		<td><img src="/resources/img/nlogo.png"></td>
	</tr>
	<tr id="list1">
		<c:forEach items="${list}" var="board">
			<c:if test="${board.nw eq '넷플릭스'}">
				<td class="td-padding"> <span><a class='move'
					href='/cplay/get?title=<c:out value="${board.title}"/>'><img src="/resources/img/<c:out value="${board.poster}" />">
					<span class="title"><c:out value="${board.title}"/></span>
				</a></span></td>
			</c:if>
		</c:forEach>
	</tr>
</table>
<!-- M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z -->

<div class="right">
	<button class="rb" id="btn_r1"><span class="nf-svg-icon "><svg viewBox="0 0 23 23" xmlns="http://www.w3.org/2000/svg">
	<path fill="currentColor" d="M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z">
	</path></svg></span></button>
	<div class="gra1"></div>
</div>
</div>
</section>
<hr class="p">
	
<section class=".nm-collection-row">
<div class="nm-content-horizontal-row">
<div class="left">
	<button id="btn_r"><span class="nf-svg-icon "><svg viewBox="0 0 18 18" xmlns="http://www.w3.org/2000/svg">
	<path fill="currentColor" d="M11.56 5.56L10.5 4.5 6 9l4.5 4.5 1.06-1.06L8.12 9z">
	</path></svg></span></button>
	<div class="gra"></div>
</div>
<table class="nm-content-horizontal-row-item-container">
	<tr>
		<td><img src="/resources/img/watcha.png"></td>
	</tr>
	<tr id="list">
		<c:forEach items="${list}" var="board">
			<c:if test="${board.nw eq '왓챠'}">
				<td class="td-padding"><a class='move'
					href='/cplay/get?title=<c:out value="${board.title}"/>'> <img
						src="/resources/img/<c:out value="${board.poster}" />">
						<span class="title"><c:out value="${board.title}"/></span>
				</a></td>
			</c:if>
		</c:forEach>
	</tr>
</table>
	<div class="right">
	<button class="rb" id="btn_r2"><span class="nf-svg-icon "><svg viewBox="0 0 23 23" xmlns="http://www.w3.org/2000/svg">
	<path fill="currentColor" d="M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z">
	</path></svg></span></button>
	<div class="gra2"></div>
</div>
</div>
</section>
	<%@include file="../includes/mfooter.jsp"%>
