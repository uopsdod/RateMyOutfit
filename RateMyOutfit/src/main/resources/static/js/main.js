
var stompClient = null;
var currFileName = '';
var jwtStr = null;


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//        setConnected(true);
        console.log('Connected: ' + frame);
        // subscribe 1 - 
        stompClient.subscribe('/topic/ratingHistory', function (data) {
        	console.log("connect() ratingHistory - data: " + data);
        	console.log("connect() ratingHistory - data: " , data);
        	console.log("connect() ratingHistory - data.body: " + data.body);
        	console.log("connect() ratingHistory - data.body: " , data.body);
        	
        	updateRatingHistoryPage(data.body);
        	
//            showGreeting(JSON.parse(data.body).content);
        });
        
        // subscribe 2 - 
        stompClient.subscribe('/topic/fileUploaded', function (data) {
        	console.log("connect() fileUploaded - data: " + data);
        	console.log("connect() fileUploaded - data: " , data);
        	console.log("connect() fileUploaded - data.body: " + data.body);
        	console.log("connect() fileUploaded - data.body: " , data.body);
        	
        	updateProfilePage(data.body);
        	
//        	updateRatingHistoryPage(data.body);
        	
//            showGreeting(JSON.parse(data.body).content);
        });
        
        // 檢查是否有要更新資訊  (觸發init)
        triggerInit();
        
    });
    
    
}

function triggerInit() {
	console.log("triggerInit");
	stompClient.send("/app/triggerInit", {}, JSON.stringify({'triggerInit': 'triggerInitText'}));
}

function triggerRatingHistoryBroadcast(aRatingResult) {
//    stompClient.send("/app/triggerRatingHistoryBroadcast", "SomeText");
//    stompClient.send("/app/triggerRatingHistoryBroadcast", "SomeText");
//	stompClient.send("/app/hello", {}, JSON.stringify({'name': 'nameContent'}));
	stompClient.send("/app/triggerRatingHistoryBroadcast", {}, JSON.stringify({'ratingResult': aRatingResult}));
}

function updateRatingHistoryPage(ratingResult){
    var ratingHistoryList = ratingResult.split(",");
    var i;
    var result = "";
	for (i = 0; i < ratingHistoryList.length; i++) {
		console.log("ratingHistoryList[i]: " + ratingHistoryList[i]);
		console.log("ratingHistoryList[i].trim(): " + ratingHistoryList[i].trim());
	
		result += ratingHistoryList[i].trim() + "<br>";
// 		    document.getElementById("updateAvailable_" + a[i]).style.visibility
// 		                                                                 = "visible";
	}
	console.log("result: " + result);
	document.getElementById("historyRatingResult").innerHTML = result;	
}

function updateProfilePage(picUrl){
//    console.log("check file Data: " + data + "\nStatus: " + status);
	console.log("updateProfilePage - currFileName: " + currFileName);
	console.log("updateProfilePage - picUrl: " + picUrl);
    if (currFileName != picUrl){
    	currFileName = picUrl;
    	document.getElementById("mainPic").src = picUrl;
    	console.log("updateProfilePage - updated mainPic");
    }
}

function checkIfJWTValid(aAccount, aPassword){
	console.log("checkIfJWTValid input aAccount: " + aAccount);
	console.log("checkIfJWTValid input aPassword: " + aPassword);
    $.post("login",
            {
    		  account: aAccount,
    		  password: aPassword
            },
            function(data,status){
                console.log(data)
                console.log("data: " , data);
                console.log(status);
                if ("success" == status){
                	console.log("login success");
            	    // 直接連上server,建立stompClient
            	    connect();
                }else{
                	consoel.log("Please try login again");
                }
   });
}

$(document).ready(function(){
	console.log("document ready");
	
	// 建立測試用資料
	$("#account").val("sam");
	$("#password").val("1111");
	
	if (jwtStr == undefined){
		console.log("jwtStr is empty");
		var account = $("#account").val();
		var password = $("#password").val();
		
		checkIfJWTValid(account, password);
	}
	
//	
//	if (!checkIfJWTValid()){
//		
//	}else{
//	    // 直接連上server,建立stompClient
//	    connect();
//	}
	
    $("#formRatingHistory").on('submit', function (e) {
        e.preventDefault();
    });
    $("#formUploadFile").on('submit', function (e) {
        e.preventDefault();
		// Create an FormData object
        // Get form
        var form = $('#formUploadFile')[0];
        var data = new FormData(form);
		// If you want to add an extra field for the FormData
//        data.append("CustomField", "This is some extra data, testing");
        
        $.ajax({
//            type: "POST",
//            data: $(this).serialize(),
            type: "POST",
            enctype: 'multipart/form-data',
            url : $(this).attr('action') || window.location.pathname,
//            url: "/api/upload/multi",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
//            timeout: 600000,            
            success: function (data) {
            	console.log("formUploadFile data: " , data);
//                $("#form_output").html(data);
            },
            error: function (jXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    });
    
    // 一次設定全部onClick事件
    $("[name=triggerRatingHistoryBroadcast]").click(function() { 
    	console.log("triggerRatingHistoryBroadcast" + this.value);
//    	console.log($( "#triggerRatingHistoryBroadcast" )[0].value);
//    	console.log($( "#triggerRatingHistoryBroadcast" ).val());
//    	var contents = $('#contents')[0];
    	triggerRatingHistoryBroadcast(this.value); 
    });
    
//    $( "#initTest" ).click(function() { triggerInit(); });
    
//    $( "#connect" ).click(function() { connect(); });
//    $( "#disconnect" ).click(function() { disconnect(); });
//    $( "#send" ).click(function() { sendName(); });
});

//$(function () {
//
//});