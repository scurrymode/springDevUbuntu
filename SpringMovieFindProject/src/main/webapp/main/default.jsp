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
				<div class="facet-title">장르</div>
				<div id="genres">
					<ul style="list-style-type: none">
						<c:forEach var="genre" items="${gList }">
							<li>${genre }</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

		<div data-reactroot="" class="ais-hits">
			<div class="ais-hits--item">
				<c:forEach var="vo" items="${mList }">
					<article class="movie"> 
					<a href="detail.do?mno=${vo.mno }&page=${curpage}"><img class="movie-image" src="${vo.poster}" /></a>
					<div class="movie-meta">
						<div class="movie-title">
							${vo.title} <span class="movie-year"> ${vo.director } </span>
						</div>
						<div class="movie-rating">${vo.star }</div>

						<div class="movie-genres">${vo.genre }</div>
					</div>
					</article>
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