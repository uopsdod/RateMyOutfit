package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.JsonObject;
import com.util.Util;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;

@Controller
public class MessageBrokerController {
	
	private MessageBrokerUtil utilWebOSocketMsgBroker;
	
    // use db decouple this later on
    public static List<String> ratingHistoryList = new ArrayList<>();	
	
    @Autowired
    public MessageBrokerController(MessageBrokerUtil utilWebOSocketMsgBroker) {
    	System.out.println("MessageBrokerController() called");
        this.utilWebOSocketMsgBroker = utilWebOSocketMsgBroker;
    }
    
    @MessageMapping("/triggerRatingHistoryBroadcast")
    @SendTo(MessageBrokerUtil.TOPIC + MessageBrokerUtil.CHANNEL_ratingHistory)
    public String ratingHistory(String aMsg) throws Exception {
    	Util.getConsoleLogger().info("ratingHistory starts");
    	Util.getConsoleLogger().info("ratingHistory input aMsg: " + aMsg);
    	
    	JsonObject msgJsonObj = Util.getGJsonObject(aMsg);
    	String ratingResult = Util.getGString(msgJsonObj, "ratingResult");
    	Util.getConsoleLogger().info("ratingHistory ratingResult: " + ratingResult);
    	
    	/** 更新list **/
    	ratingHistoryList.add(ratingResult);
    	
    	/** 新增/更新DB資料 **/
    	
        /** 加上評分歷史紀錄,並讓最新的評論在最上面 **/
    	String RatingHistoryListResult = getRatingHistoryListOutput();
        
        return RatingHistoryListResult;
    }
    
    
    @MessageMapping("/triggerInit")
    @SendTo(MessageBrokerUtil.TOPIC + MessageBrokerUtil.CHANNEL_init)
    public String init(String aMsg) throws Exception {
    	Util.getConsoleLogger().info("triggerInit starts");
    	Util.getConsoleLogger().info("triggerInit input aMsg: " + aMsg);
    	
        // 通知圖片紀錄
        this.utilWebOSocketMsgBroker.sendJsonToTopicSubcriber(MessageBrokerUtil.CHANNEL_fileUploaded, FileUploadUtil.lastPic);
        
        // 通知評論紀錄
        String RatingHistoryListResult = getRatingHistoryListOutput();
        this.utilWebOSocketMsgBroker.sendMsgToTopicSubcriber(MessageBrokerUtil.CHANNEL_ratingHistory, RatingHistoryListResult);
        
        Util.getConsoleLogger().info("triggerInit ends");
        return "";
    }
    
    
    public static String getRatingHistoryListOutput(){
        List<String> tmpRatingHistoryList = new ArrayList(ratingHistoryList);
        Collections.reverse(tmpRatingHistoryList);
        String RatingHistoryListResult = tmpRatingHistoryList.toString().substring(1, tmpRatingHistoryList.toString().length()-1);
        Util.getConsoleLogger().info("ratingHistory() input RatingHistoryListResult: " + RatingHistoryListResult);
        return RatingHistoryListResult;
    }
}
