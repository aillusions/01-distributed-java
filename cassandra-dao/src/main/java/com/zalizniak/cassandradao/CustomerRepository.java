package com.zalizniak.cassandradao;


import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Query("Select * from customer where firstname=?0")
    Customer findByFirstName(String firstName);

    @Query("Select * from customer where lastname=?0")
    List<Customer> findByLastName(String lastName);

}