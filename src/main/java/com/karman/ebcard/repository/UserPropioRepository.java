package com.karman.ebcard.repository;

import com.karman.ebcard.domain.UserPropio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserPropio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPropioRepository extends JpaRepository<UserPropio, Long> {

    @Query(value = "select distinct user_propio from UserPropio user_propio left join fetch user_propio.wallets",
        countQuery = "select count(distinct user_propio) from UserPropio user_propio")
    Page<UserPropio> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user_propio from UserPropio user_propio left join fetch user_propio.wallets")
    List<UserPropio> findAllWithEagerRelationships();

    @Query("select user_propio from UserPropio user_propio left join fetch user_propio.wallets where user_propio.id =:id")
    Optional<UserPropio> findOneWithEagerRelationships(@Param("id") Long id);

}
