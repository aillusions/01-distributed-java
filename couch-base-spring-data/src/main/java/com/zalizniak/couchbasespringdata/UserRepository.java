package com.zalizniak.couchbasespringdata;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    long countByLastname(String lastname);
}