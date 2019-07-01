package com.zalizniak.couchbasespringdata;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.N1qlSecondaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.List;

@N1qlPrimaryIndexed
//@ViewIndexed(designDoc = "UserRepository")
@N1qlSecondaryIndexed(indexName = "userRepositorySecondaryIndex")
public interface UserRepository extends CouchbasePagingAndSortingRepository<User, String> {

    long countByLastname(String lastname);

    /**
     * Advanced querying with N1QL derivation
     */
    @Query
    List<User> findByLastnameEqualsIgnoreCase(String lastName);

    @Query("#{#n1ql.selectEntity} WHERE firstname LIKE $1")
    List<User> findUsersWithTheirFirstnameLike(String likePattern);
}