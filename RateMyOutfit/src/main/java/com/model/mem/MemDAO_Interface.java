package com.model.mem;

import java.util.List;

public interface MemDAO_Interface {
	public void insert(MemVO aMemVO);
	public void update(MemVO aMemVO);
	public void delete(String aMemId);
	public MemVO findByPrimaryKey(String aMemId);
	public List<MemVO>getAll();
}
