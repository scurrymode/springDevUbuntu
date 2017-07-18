<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="content">
		<div class="facets">
			<div class="facet">
				<div class="facet-title">감성/장르</div>
				<div id="genres">
					<ul style="list-style-type: none">
						<li>
						<form method="post" action="music_feel_find.do">
						<span class="genre_span">감정/상황</span>
						<select name="feel_data">
						<c:forEach var="fd" items="${feel_data }">
						<option>${fd }</option>
						</c:forEach>
						</select>
						<input type=submit value=전송>
						</form>
						</li>
						<li>
						<form method="post" action="music_genre_find.do">
						<span class="genre_span">이슈/장르</span>
						<select name="genre_data">
						<c:forEach var="gd" items="${genre_data }">
						<option>${gd }</option>
						</c:forEach>
						</select>
						<input type=submit value=전송>
						</form>
						</li>
					</ul>
				</div>
			</div>
			<div class="facet">
				<div class="facet-title">오늘의 날씨</div>
				<div id="ratings">
				<c:forEach var="vo" items="${wList }">
					${vo.location }&nbsp;<img src="${vo.image }">&nbsp;${vo.weather }<br>
				</c:forEach>
				</div>
			</div>
		</div>

		<div data-reactroot="" class="ais-hits">
			<div class="ais-hits--item">
				<c:forEach var="vo" items="${list }" varStatus="s">
				<c:if test="${s.index<10 }">
					<article class="movie"> 
					<embed class="movie-image" src="${vo.link}" />
					<div class="movie-meta">
						<div class="movie-title">
							<a href="detail.do?title=${vo.title}">${vo.title}</a> 
							<span class="movie-year"> 
							${vo.singer } </span>
						</div>
						<div class="movie-rating">${vo.rank }</div>
					</div>
					</article>
					</c:if>
				</c:forEach>
				<div id="pagination">
					<a href="main.do?page=${curpage!=1?curpage-1:1 }">이전</a>&nbsp; <a
						href="main.do?page=${curpage!=totalpage?curpage+1:totalpage }">다음</a>
					&nbsp; ${curpage } page / ${totalpage } pages
				</div>
			</div>
		</div>

	</div>
</body>
</html>