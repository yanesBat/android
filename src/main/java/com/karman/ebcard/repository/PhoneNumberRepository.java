package com.karman.ebcard.repository;

import com.karman.ebcard.domain.PhoneNumber;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PhoneNumber entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

}
