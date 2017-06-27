<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css"> -->
<title>jQuery SVG 3D Tag Cloud Plugin Example</title>
</head>

<body>
<div id="jquery-script-menu">
<div class="jquery-script-center">
<div class="jquery-script-clear"></div>
</div>
</div>
<div id='tag-cloud'></div>
<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script src="https://www.jqueryscript.net/demo/3D-Interactive-SVG-Tag-Cloud-Plugin-With-jQuery-SVG-3D-Tag-Cloud/jquery.svg3dtagcloud.min.js"></script>
<script>
    	$( document ).ready( function() {
            var entries = <%= request.getAttribute("json")%>

            var settings = {

                entries: entries,
                width: 640,
                height: 480,
                radius: '65%',
                radiusMin: 75,
                bgDraw: true,
                bgColor: '#111',
                opacityOver: 1.00,
                opacityOut: 0.05,
                opacitySpeed: 6,
                fov: 800,
                speed: 2,
                fontFamily: 'Oswald, Arial, sans-serif',
                fontSize: '15',
                fontColor: '#fff',
                fontWeight: 'normal',//bold
                fontStyle: 'normal',//italic 
                fontStretch: 'normal',//wider, narrower, ultra-condensed, extra-condensed, condensed, semi-condensed, semi-expanded, expanded, extra-expanded, ultra-expanded
                fontToUpperCase: true

            };

            //var svg3DTagCloud = new SVG3DTagCloud( document.getElementById( 'holder'  ), settings );
            $( '#tag-cloud' ).svg3DTagCloud( settings );

		} );
        
    </script>
</body>
</html>