package com.zalizniak.awsdynamodbdao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @EnableScan
    List<User> findByUserAge(Integer userAge, Pageable pageable);

    List<User> findByLastName(String lastName, Pageable pageable);

    Page<User> findByFirstName(String firstName, Pageable pageable);

    Page<User> findByConstantField(String constantField, Pageable pageable);

    @Query(fields = "firstName")
    List<User> findByUserId(String userId);
}