package com.controller;

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
import com.util.RESTfulUtil;
import com.util.Util;

@RestController
public class RESTfulController {
	public static final String TAG = "RESTfulController";
	
    @Autowired
    DataSource dataSource;

    @Autowired
    MemRepository memRepository;
	
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
}
