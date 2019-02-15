package com.zalizniak.awsdynamodbdao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


@EnableScan
@EnableScanCount
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    Page<User> findByLastName(String lastName, Pageable pageable);

    Page<User> findByFirstName(String firstName, Pageable pageable);

    @EnableScan
    @EnableScanCount
    Page<User> findAll(Pageable pageable);
}