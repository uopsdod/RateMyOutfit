package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

public class MessageBrokerUtil {
	
    private SimpMessagingTemplate template;

    @Autowired
    public MessageBrokerUtil(SimpMessagingTemplate aTemplate) {
        this.template = aTemplate;
    }
    
    public void sendMsgToTopicSubcriber(String aTopicName, String aText){
    	String dst = MessageBrokerUtil.TOPIC + "/" + aTopicName;
    	System.out.println("sendMsgToTopicSubcriber dst: " + dst);
    	this.template.convertAndSend(dst, aText);
    }
	
	public static final String TOPIC = "/topic";
	public static final String DST_PREFIX = "/app";
	public static final String ENDPOINT = "/gs-guide-websocket";
	
	public static final String CHANNEL_ratingHistory = "/ratingHistory";
	public static final String CHANNEL_fileUploaded = "/fileUploaded";
	public static final String CHANNEL_init= "/init";
}
