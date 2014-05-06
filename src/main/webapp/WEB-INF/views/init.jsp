<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>whatsAround</title>
<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/bootstrap/css/docs.css" rel="stylesheet">
<link href="resources/bootstrap/css/pygments-manni.css" rel="stylesheet">
<style type="text/css">
em {
	background-color: yellow;
	color: maroon;
	font-weight: bold;
}

p {
	font-size: 11px;
	margin-bottom: 10px;
}

h4 {
	margin-bottom: 1px;
	margin-top: 20px;
}

select {
	margin-bottom: 0px;
	width: 150px;
	font-size: 12px;
	padding-top: 1px;
	padding-bottom: 1px;
	height: 20px;
}
</style>

<script src="resources/jquery.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>


</head>
<body>
	<div class="container">
		<h2>Objets autour de moi</h2>
		<div id="map" ></div>
	</div>
</body>
</html>
