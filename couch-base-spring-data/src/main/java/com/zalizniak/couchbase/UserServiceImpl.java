package com.zalizniak.couchbase;

import java.util.List;
import java.util.Optional;

import com.zalizniak.couchbase.dao.UserCounterRepository;
import com.zalizniak.couchbase.dao.UserRepository;
import com.zalizniak.couchbase.dao.doc.UserBasicDoc;
import com.zalizniak.couchbase.dao.doc.UserDoc;
import com.zalizniak.couchbase.exception.ApiException;
import com.zalizniak.couchbase.exception.ErrorType;
import com.zalizniak.couchbase.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that manages the valid operations over the user repository.
 *
 * @author Charz++
 */

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private UserCounterRepository userCounterRepo;

  @Override
  public UserDoc findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

  @Override
  public UserDoc findById(Long id) {
    final Optional<UserDoc> userObj = userRepo.findById(UserDoc.getKeyFor(id));
    return userObj.orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND));
  }

  @Override
  public UserDoc create(UserDoc user) {
    // We first search by email, the user should not exist
    final Optional<UserDoc> userObj = Optional.ofNullable(this.findByEmail(user.getEmail()));
    if (userObj.isPresent()) {
      throw new ApiException(ErrorType.USER_ALREADY_EXISTS);
    }
    user.setId(userCounterRepo.counter()); // internally we set the key with that id
    return userRepo.save(user);
  }

  @Override
  public void update(Long id, UserDoc user) {
    // The user should exist
    final UserDoc existingUser = this.findById(id);
    if (!Util.isNullOrEmpty(user.getName())) {
      existingUser.setName(user.getName());
    }
    if (user.getNicknames() != null) {
      existingUser.setNicknames(user.getNicknames());
    }
    if (user.getAge() != null) {
      existingUser.setAge(user.getAge());
    }
    // Save
    userRepo.save(existingUser);
  }

  @Override
  public void delete(Long id) {
    // The user should exist
    this.findById(id);
    userRepo.deleteById(UserDoc.getKeyFor(id));
  }

  @Override
  public Boolean exists(Long id) {
    return userRepo.existsById(UserDoc.getKeyFor(id));
  }

  @Override
  public List<UserDoc> findUsersByNickname(String nickname) {
    return userRepo.findUsersWithNickname(nickname);
  }

  @Override
  public List<UserBasicDoc> findUsersByName(String name) {
    final String cleanName = name.toLowerCase().trim();
    return userRepo.findUsersWithName(cleanName);
  }

  @Override
  public List<UserDoc> findAll() {
    return userRepo.findAllUsers();
  }

  @Override
  public Integer countAll() {
    return userRepo.countAll();
  }
}
