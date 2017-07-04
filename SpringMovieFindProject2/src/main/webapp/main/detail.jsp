<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script>
window.onload=function(){
Highcharts.chart('container', {
    chart: {
        type: 'pie',
        options3d: {
            enabled: true,
            alpha: 45
        }
    },
    title: {
        text: '<%=request.getAttribute("title")%>'
    },
    subtitle: {
        text: '감성현황'
    },
    plotOptions: {
        pie: {
            innerSize: 100,
            depth: 45
        }
    },
    series: [{
        name: 'Feel Count',
        data: <%= request.getAttribute("json")%>
    }]
});
}
</script>
</head>
<body>
  <div class="ais-hits1" data-reactroot="">
   <div class="ais-hits--item1">
    <article class="movie">
    <img class="movie-image" src="${vo.poster}" />
    <div class="movie-meta">
      <div class="movie-title">
          ${vo.title }
        <span class="movie-year">
           ${ vo.director }
        </span>
      </div>

      <div class="movie-rating">
         ${vo.star }
      </div>

      <div class="movie-genres">
        ${vo.genre }
      </div>
      <div class="movie-genres">
        ${vo.actor }
      </div>
      <div class="movie-genres">
        ${vo.story }
      </div>
    </div>
   </article>
<!--    	<div id="pagination">
        <img src="feel.png">
     </div> -->
     <div id="container" style="height: 400px"></div>
   
     <div id="pagination">
        <a href="main.do?page=${page }">목록</a>
     </div>
   </div>
   </div>
</body>
</html>




