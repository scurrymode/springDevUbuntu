<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div data-reactroot="" class="ais-hits">
			<div class="ais-hits--item">
					<article class="movie"> <img class="movie-image"
						src="${vo.poster}" />
					<div class="movie-meta">
						<div class="movie-title">
							${vo.title} <span class="movie-year"> ${vo.director } </span>
						</div>
						<div class="movie-rating">${vo.star }</div>

						<div class="movie-genres">${vo.genre }</div>
					</div>
					</article>
				<div id="pagination">
					<a href="#">추천영화</a>&nbsp;
					<a href="main.do?page=${page }">목록</a>
				</div>
			</div>
		</div>

	</div>
</body>
</html>