package com.model.mem;

import java.util.List;

public class MemDAO implements MemDAO_Interface{

	@Override
	public void insert(MemVO aMemVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MemVO aMemVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String aMemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemVO findByPrimaryKey(String aMemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemVO> getAll() {
		// TODO Auto-generated method stub
		return null;
//		
//		List<MemVO> list = null;
//		
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		try {
//			session.beginTransaction();
//			Query query = session.createQuery(GET_ALL_STMT);
//			list = query.list();	
//			session.getTransaction().commit();
//		} catch (RuntimeException ex) {
//			session.getTransaction().rollback();
//			throw ex;
//		}
//		return list;
	}

}
