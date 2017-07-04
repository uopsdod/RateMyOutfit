package com.model.pic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Pic {
    //http://www.oracle.com/technetwork/middleware/ias/id-generation-083058.html
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PIC_SEQ")
    @SequenceGenerator(sequenceName = "pic_seq", initialValue = 1, allocationSize = 1, name = "PIC_SEQ")
    Long picId;
    
	private long rateNum;
	private long rateResult;
	
	@JsonIgnore // 不要被轉換成json
    @Lob
    private byte[] picFile;
//	private byte[] picFile;
    //@Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    Date date;
    
    @JsonInclude() // 但仍要可以被轉換成json
    @Transient // 不成為persistence物件
    public String picUrl;

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public long getRateNum() {
		return rateNum;
	}

	public void setRateNum(long rateNum) {
		this.rateNum = rateNum;
	}

	public long getRateResult() {
		return rateResult;
	}

	public void setRateResult(long rateResult) {
		this.rateResult = rateResult;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte[] getPicFile() {
		return picFile;
	}

	public void setPicFile(byte[] picFile) {
		this.picFile = picFile;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}   
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
}
