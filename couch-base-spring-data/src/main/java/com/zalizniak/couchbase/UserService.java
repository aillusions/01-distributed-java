package com.zalizniak.couchbase;

import java.util.List;
import com.zalizniak.couchbase.dao.doc.UserBasicDoc;
import com.zalizniak.couchbase.dao.doc.UserDoc;

/**
 * Interface for the User Service Implementation
 *
 * @author Charz++
 */

public interface UserService {

  // CRUD repository interface

  UserDoc findById(Long id);

  UserDoc create(UserDoc user);

  void update(Long id, UserDoc user);

  void delete(Long id);

  Boolean exists(Long id);

  // Custom methods

  UserDoc findByEmail(String email);

  List<UserDoc> findUsersByNickname(String nickname);

  List<UserBasicDoc> findUsersByName(String name);

  List<UserDoc> findAll();

  Integer countAll();
}
