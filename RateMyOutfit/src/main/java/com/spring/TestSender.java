package com.spring;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.util.Util;
import com.util.UtilWebOSocketMsgBroker;

@Component
public class TestSender implements CommandLineRunner{
	@Autowired
	private UtilWebOSocketMsgBroker utilWebOSocketMsgBroker;

	public void run(String... arg0) throws Exception {
		Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 開始測試");
		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 開始測試");
		
        
        Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 結束測試");
		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 結束測試");
	}

}