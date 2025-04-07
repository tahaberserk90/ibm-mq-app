
package com.ibmmq.backend.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibmmq.backend.model.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Page<Partner> findAll(Pageable pageable);
    boolean existsByAlias(String alias);
}