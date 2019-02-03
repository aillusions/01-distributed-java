package com.zalizniak.postgres;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author aillusions
 */
@Transactional
public interface TempEntryDao extends PagingAndSortingRepository<TempEntry, Long> {

    Optional<TempEntry> findById(Long id);

    Page<TempEntry> findAll(Pageable pageable);
}