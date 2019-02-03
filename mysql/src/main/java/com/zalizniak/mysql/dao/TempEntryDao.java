package com.zalizniak.mysql.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author aillusions
 */
@Transactional
public interface TempEntryDao extends PagingAndSortingRepository<TempEntry, Long> {

    Optional<TempEntry> findById(Long id);

    Page<TempEntry> findAll(Pageable pageable);

    @Query("select o from TempEntry o where o.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    TempEntry getOneForUpdate(@Param("id") Long id);
}