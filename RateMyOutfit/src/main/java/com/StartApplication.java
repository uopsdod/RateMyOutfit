
package com;

import static java.lang.System.exit;

import java.text.SimpleDateFormat;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.Transactional;

import com.model.customer.Customer;
import com.model.customer.CustomerRepository;
import com.util.Util;

@SpringBootApplication
@ComponentScan({"com"})
@EnableMBeanExport(defaultDomain="${projectName}")
@ImportResource("classpath:spring-config.xml")
public class StartApplication extends SpringBootServletInitializer  implements CommandLineRunner{
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	System.out.println("StartApplication configure() called ***************************** ");
        return application.sources(StartApplication.class);
        
    }	
	
	public static void main(String[] args){
		SpringApplication.run(StartApplication.class, args);
		System.out.println("StartApplication main() called ***************************** ");
//		Util.getFileLogger().info("Util.getVersion(): ");
	}
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);
        
        System.out.println("\n1.findAll()...");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }

        System.out.println("\n2.findByEmail(String email)...");
        for (Customer customer : customerRepository.findByEmail("222@yahoo.com")) {
            System.out.println(customer);
        }

        System.out.println("\n3.findByDate(Date date)...");
        for (Customer customer : customerRepository.findByDate(sdf.parse("2017-02-12"))) {
            System.out.println(customer);
        }

        // For Stream, need @Transactional
        System.out.println("\n4.findByEmailReturnStream(@Param(\"email\") String email)...");
        try (Stream<Customer> stream = customerRepository.findByEmailReturnStream("333@yahoo.com")) {
            stream.forEach(x -> System.out.println(x));
        }

        //System.out.println("....................");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Date from = sdf.parse("2017-02-15");
        //Date to = sdf.parse("2017-02-17");

        //for (Customer customer : customerRepository.findByDateBetween(from, to)) {
        //    System.out.println(customer);
        //}

        System.out.println("Done!");

        exit(0);
    }
	
}
