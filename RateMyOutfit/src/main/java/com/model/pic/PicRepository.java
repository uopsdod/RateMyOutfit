package com.model.pic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface PicRepository extends CrudRepository<Pic, Long> {
	
//	Pic	findOne(Long primaryKey);
//	
//	Pic save(Pic entity); 
	
//    List<Pic> findByMemName(String memName);
//    List<Pic> findByMemAccount(String memAccount);

//    @Query("select m from Pic m where m.memName = :memName")
//    Stream<Pic> findByEmailReturnStream(@Param("memName") String memName);

//    List<Pic> findByDate(Date date);

    //@Query("select c from Customer c")
    //Stream<Customer> findAllAndStream();

    //List<Customer> findByDateBetween(Date from, Date to);

}
