<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>Rate My Outfit</title>
<!-- bootstrap v3.3.6 -->
<script src="js/jquery.min.js"></script>
<link href="boostrap/bootstrap.css" rel="stylesheet" />
<link href="boostrap/bootstrap-theme.css" rel="stylesheet" />
<script src="boostrap/bootstrap.js"></script>

</script>

<style>
.center {
	margin: auto;
	text-align: center;
}

.left {
	margin: auto;
	text-align: left;
}

/* .chatDialogue{ */
/* 	max-height:100px; */
/* } */

.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}

/* html, body {height: 100%;} */

.chatmaxHeight
{
    height: 400px;
}
.logmaxHeight
{
    height: 470px;
}​

.hidden
{
	visibility: hidden;
}
form, table {
     display:inline;
     margin:0px;
     padding:0px;
}

</style>
</head>
<body >
	<!-- 標題 -->
	<div class="row no-gutter" style="border-bottom: 1px solid #c0c0c0; width: 100%;"> <!-- width: 100% 讓螢幕寬度符合螢幕大小 -->
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 center">
			<h3>Rate My Outfit</h3>
		</div>
	</div>

	
	<div class="row" style="width: 100%;"> <!-- width: 100% 讓螢幕寬度符合螢幕大小 -->
		<!-- 覺得帥/美，想挑戰 -->
		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 col-xs-3 panel panel-default nopadding"
			 >
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading center nopadding">
				<h3>覺得帥/美，想挑戰</h3>
			</div>
<!-- 			<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1"></div> -->
<!-- 			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></div> -->
			<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 panel-body"
				 >
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container">
					<b>大名</b>:<br>
					<input type="text" id="UserName" placeholder="guest name"><br> 
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container">	
					<b>上傳圖片</b>:<br>
					<div>
						<form method="POST" enctype="multipart/form-data" action="/">
							<table>
								<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
								<tr><td></td><td><input type="submit" value="Upload" /></td></tr>
							</table>
						</form>
					</div>					
					
					<span id="UserID"></span><br> 
<!-- 					<b>Status:</b><br> -->
<!-- 					<span id="Status"></span><br> -->
<!-- 					<b>RoomID:</b><br>  -->
<!-- 					<span id="RoomID"></span><br>  -->
<!-- 					<b>AgentIDs</b>:<br>  -->
<!-- 					<span id="AgentIDs"></span><br>  -->
<!-- 					<b>AgentNames:</b><br>  -->
<!-- 					<span id="AgentNames"></span><br>  -->
<!-- 					<b>Event:</b><br>  -->
<!-- 					<span id="Event"></span><br> -->
<!-- 					<br> -->
				</div>		
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
					<button class="btn btn-primary btn-sm" id="openChat" onclick="Login();">openChat</button>
				</div>						
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
					<button class="btn btn-primary btn-sm" id="closeChat" onclick="Logout();">closeChat</button>
				</div>	
			</div>	
		</div>
		<!-- 鑑賞區 -->
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 center" id="chatDialogue">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container panel panel-default center nopadding ">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading nopadding">
					<h3>鑑賞區</h3>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container" style="padding-top: 20px;">
					<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
						平均分數:
						<span class="input-xlarge uneditable-input">99.1</span>
					</div>						
					<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
						總評分人數:
						<span class="input-xlarge uneditable-input">2</span>
					</div>						
<!-- 					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"> -->
<!-- 						<button class="btn btn-primary btn-sm" id="sendToRoom" onclick="sendtoRoom();">SEND</button> -->
<!-- 					</div>						 -->
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-body left chatmaxHeight" id="ratingArea">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container center" style="padding-top: 20px;">
<!-- 						<img alt="" src="pic02.png"> -->
<%-- 						<span class="input-xlarge uneditable-input">"${files}"</span> --%>
						<c:forEach var="item" items="${files}">
<%-- 							Access here item if needed <c:out value="${item}"/> --%>
							<img src="${item}">
						</c:forEach>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container center" style="padding-top: 20px;">
						<form id='formName' name='formName' action="/giveRating">
					        <input type='hidden' id='giveRatingResult01' name='giveRatingResult' value='good'>
					        <input type="image" name="submit" src="good.png" border="0" alt="Submit" width="84" height="84"/>
						</form> 						
						<form id='formName' name='formName' action="/giveRating">
					        <input type='hidden' id='giveRatingResult02' name='giveRatingResult' value='nerdy'>
					        <input type="image" name="submit" src="nerdy.png" border="0" alt="Submit" width="84" height="84"/>
						</form> 

						
<!-- 						<form method="POST" action="/giveRating"> -->
<!-- 							<table> -->
<!-- 								<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr> -->
<!-- 								<tr><td></td><td><input type="submit" value="Upload" /></td></tr> -->
<!-- 							</table> -->
<!-- 						</form> -->
					</div>
				</div>
			</div> <!-- end of container -->
		</div>
<!-- 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 center" id="chatDialogueReverse"></div> -->

		<!-- 評分紀錄 -->
		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 panel panel-default center nopadding">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading nopadding">
			<h3>評分紀錄</h3>
			</div>
			<div class="pre-scrollable col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-body left logmaxHeight" id="text">
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
<!-- 				some log ... <br> some log ... <br> some log ... <br> some log ... <br> some log ... <br> -->
			</div>
		</div>
	</div> <!-- end of row -->
</body>
</html>
