package com.model.common;

import java.util.ArrayList;
import java.util.List;

public class ErrorVO {
	private List<String> errorMsgs;
	public List<String> getErrorMsgs() {
		return errorMsgs;
	}
	protected ErrorVO(){
		this.errorMsgs = new ArrayList<>();
	}
	
	public void addError(String error){
		this.errorMsgs.add(error);
	}
	

}
