package com.karman.ebcard.repository;

import com.karman.ebcard.domain.SocialContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SocialContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialContactRepository extends JpaRepository<SocialContact, Long> {

}
