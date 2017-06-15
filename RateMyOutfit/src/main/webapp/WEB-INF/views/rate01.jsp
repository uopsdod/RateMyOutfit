<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<html lang="en">
<head>

<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" /> -->

<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

<!-- 	<nav class="navbar navbar-inverse"> -->
<!-- 		<div class="container"> -->
<!-- 			<div class="navbar-header"> -->
<!-- 				<a class="navbar-brand" href="#">Spring Boot</a> -->
<!-- 			</div> -->
<!-- 			<div id="navbar" class="collapse navbar-collapse"> -->
<!-- 				<ul class="nav navbar-nav"> -->
<!-- 					<li class="active"><a href="#">Home</a></li> -->
<!-- 					<li><a href="#about">About</a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</nav> -->

	<div class="container">

		<div class="starter-template">
			<FORM action="uploadFile" method=post enctype="multipart/form-data">
				<input type="file" name="upfile1" id="upfile1"> <br> 
				<input type="file" name="upfile1" id="upfile2"> 
				<input type="submit" value="上傳">
			</FORM>
			<div id="imageContainer">
				<!-- <img src="images/blue.gif" id="img01">  -->
			</div>
			
      <h3>File Upload:</h3>
      Select a file to upload: <br />
      <form action = "uploadFile" method = "post"
         enctype = "multipart/form-data">
         <input type = "file" name = "file" size = "50" />
         <br />
         <input type = "submit" value = "Upload File" />
      </form>
			
			
		</div>

	</div>
	<!-- /.container -->


</body>

</html>
