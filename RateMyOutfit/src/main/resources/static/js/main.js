
var stompClient = null;



function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/ratingHistory', function (data) {
        	console.log("connect() ratingHistory - data: " + data);
        	console.log("connect() ratingHistory - data: " , data);
        	console.log("connect() ratingHistory - data.body: " + data.body);
        	console.log("connect() ratingHistory - data.body: " , data.body);
        	
        	updateRatingHistoryPage(data.body);
        	
//            showGreeting(JSON.parse(data.body).content);
        });
    });
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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    
    // 直接連上即可
    connect();
    
    // 一次設定全部onClick事件
    $("[name=triggerRatingHistoryBroadcast]").click(function() { 
    	console.log("triggerRatingHistoryBroadcast" + this.value);
//    	console.log($( "#triggerRatingHistoryBroadcast" )[0].value);
//    	console.log($( "#triggerRatingHistoryBroadcast" ).val());
//    	var contents = $('#contents')[0];
    	triggerRatingHistoryBroadcast(this.value); 
    });
    
//    $( "#connect" ).click(function() { connect(); });
//    $( "#disconnect" ).click(function() { disconnect(); });
//    $( "#send" ).click(function() { sendName(); });
});