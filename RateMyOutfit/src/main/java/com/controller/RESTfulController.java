package com.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.common.CommonVO;
import com.model.customer.CustomerRepository;
import com.model.mem.Mem;
import com.model.mem.MemRepository;
import com.model.pic.Pic;
import com.model.pic.PicRepository;
import com.util.MessageBrokerUtil;
import com.util.RESTfulUtil;
import com.util.Util;

@RestController
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
    @Autowired
    DataSource dataSource;

    @Autowired
    MemRepository memRepository;

    @Autowired
    PicRepository picRepository;
    
    @Autowired
    MessageBrokerUtil utilWebOSocketMsgBroker;
    
 // use db decouple this later on
    public static List<String> ratingHistoryList = new ArrayList<>();
	
    @PostMapping("/login")
    public Mem login(@RequestParam(value="account", required=true) String account
    						 ,@RequestParam(value="password", required=true) String password) {
    	Util.getConsoleLogger().info(TAG + "/login starts");
    	Util.getConsoleLogger().info(TAG + "/login input account: " + account);
    	Util.getConsoleLogger().info(TAG + "/login input password: " + password);
    	
    	/** 搜尋DB是否已有此帳號 **/
    	Mem mem = new Mem();
    	List<Mem> memList = memRepository.findByMemAccount(account);
    	
    	if (memList.size() > 1){
    		Util.getConsoleLogger().error(TAG + " find more than one rows by account: " + account);
    		mem.addError("multiple accounts exist");
    	}else if (memList.size() == 0){
    		Util.getConsoleLogger().info(TAG + " no row found by account: " + account);
    		mem.addError("account does not exist");
        /** 驗證帳號密碼是否符合 **/
    	}else if (memList.size() == 1){
    		Mem currMem = memList.get(0);
    		if (currMem != null){
    			if (!currMem.getMemPwd().equals(password)){
    				Util.getConsoleLogger().info(TAG + " wrong password ");
    				mem.addError("wrong password");
    			/** 帳號密碼皆正確,給予使用者完整資訊 **/
    			}else{
    				mem = currMem;
    				mem.setJwtStr(RESTfulUtil.createJWT());
    			}
    		}
    	}
    	
    	Util.getConsoleLogger().info(TAG + "/login output ");
    	Util.getConsoleLogger().info(TAG + "/login ends");
        return mem;
    }
    
    @PostMapping("/updatePic")
    public Pic giveRating(@RequestParam(value="picId", required=true) long picId
    					,@RequestParam(value="score", required=true) long score
    					,@RequestParam(value="wordsToShow", required=true) String wordsToShow) {
    	Util.getConsoleLogger().info(TAG + "/updatePic starts");
    	Util.getConsoleLogger().info(TAG + "/updatePic input picId: " + picId);
    	Util.getConsoleLogger().info(TAG + "/updatePic input score: " + score);
    	Util.getConsoleLogger().info(TAG + "/updatePic input wordsToShow: " + wordsToShow);
    	
    	/** 更新list **/
    	ratingHistoryList.add(wordsToShow);
    	
    	/** 先抓取舊資料 **/
    	Pic pic = picRepository.findOne(picId);
    	if (pic == null){
    		Util.getFileLogger().info("/updatePic - picId: " + picId + " not found in Pic table");
    	}
    	
    	/** 更新bean **/
    	long rateNum = pic.getRateNum() + 1;
    	long rateResult = (pic.getRateResult()*pic.getRateNum() + score)/(pic.getRateNum() + 1);
    	pic.setRateNum(rateNum);
    	pic.setRateResult(rateResult);
    	
    	/** 進行DB更新 **/
    	Pic newPic = picRepository.save(pic); // update
    	
    	Util.getConsoleLogger().info(TAG + "/updatePic output ");
    	Util.getConsoleLogger().info(TAG + "/updatePic ends");
    	
    	/** 加上評分歷史紀錄,並讓最新的評論在最上面 **/
    	String RatingHistoryListResult = getRatingHistoryListOutput();
    	
    	/** 告知訂閱client們 **/
    	utilWebOSocketMsgBroker.sendMsgToTopicSubcriber(MessageBrokerUtil.CHANNEL_ratingHistory, RatingHistoryListResult);
    	
    	return newPic;
    }
    
    public static String getRatingHistoryListOutput(){
        List<String> tmpRatingHistoryList = new ArrayList(ratingHistoryList);
        Collections.reverse(tmpRatingHistoryList);
        String RatingHistoryListResult = tmpRatingHistoryList.toString().substring(1, tmpRatingHistoryList.toString().length()-1);
        Util.getConsoleLogger().info("ratingHistory() - getRatingHistoryListOutput() input RatingHistoryListResult: " + RatingHistoryListResult);
        return RatingHistoryListResult;
    }
    
}
