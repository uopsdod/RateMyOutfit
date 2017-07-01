package com.model.mem;


public class MemVO {
	private String memId;
	private String memName;
	private String memAccount;
	private String memPwd;
	private byte[] memProfile;
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemAccount() {
		return memAccount;
	}
	public void setMemAccount(String memAccount) {
		this.memAccount = memAccount;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public byte[] getMemProfile() {
		return memProfile;
	}
	public void setMemProfile(byte[] memProfile) {
		this.memProfile = memProfile;
	}
	
}
