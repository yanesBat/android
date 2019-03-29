package com.karman.ebcard.repository;

import com.karman.ebcard.domain.EBCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EBCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EBCardRepository extends JpaRepository<EBCard, Long> {

}
