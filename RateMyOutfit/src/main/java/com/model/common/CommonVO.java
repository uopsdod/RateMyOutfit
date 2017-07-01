package com.model.common;

import java.util.ArrayList;
import java.util.List;

public class CommonVO {
	private List<String> errorMsgs;
	private String jwtStr;
	public List<String> getErrorMsgs() {
		return errorMsgs;
	}
	protected CommonVO(){
		this.errorMsgs = new ArrayList<>();
	}
	
	public void addError(String error){
		this.errorMsgs.add(error);
	}
	public String getJwtStr() {
		return jwtStr;
	}
	public void setJwtStr(String jwtStr) {
		this.jwtStr = jwtStr;
	}
	public void setErrorMsgs(List<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	

}
