<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
body{
	margin:0;
	padding:0;
}
nav {
	width: 100%;
	height: 100px;
	background-size: cover;
	background-attachment: fixed;
	background-color: green;
	position: absolute;
}
.menu ul {
	list-style: none;
}

.menu ul li{
	list-style: none;
	text-decoration: none;
	font-size: 24pt;
	display: inline-block;
	margin-right: 1em;
}
.menu ul li a{
	text-decoration: none;
	color: white;
	font-style: bold;
}

.menu ul li a:hover{
	text-decoration: none;
	color: black;
	font-style: bold;
}
.conteudo{
    width: 100%;
    height: 1000px;
    float: left;
	margin-top:100px;
	padding:10px;
}
</style>
<title></title>
</head>
<body>

	<nav class="menu">
		<ul>
			<li><a href="faltas.jsp">Faltas</a></li>
			<li><a href="notas.jsp">Notas</a></li>
		</ul>
	</nav>
</body>
</html>