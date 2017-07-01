//package com.spring;
//
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.google.gson.JsonObject;
//import com.model.customer.CustomerRepository;
//import com.util.Util;
//import com.util.MessageBrokerUtil;
//
//@Component
//public class TestSender implements CommandLineRunner{
//	@Autowired
//	private MessageBrokerUtil utilWebOSocketMsgBroker;
//	
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    CustomerRepository customerRepository;
//    
//    @Transactional(readOnly = true)
//	public void run(String... arg0) throws Exception {
//		Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 開始測試");
//		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 開始測試");
//		
//        
//        Util.getConsoleLogger().info("TestSender - CommandLineRunner() called - 結束測試");
//		Util.getFileLogger().info("TestSender - CommandLineRunner() called - 結束測試");
//	}
//
//}