package com.hotmart.api.subscription.infraestructure.db2.repository;

import com.hotmart.api.subscription.infraestructure.db2.entity.InstallmentProductMkt;
import com.hotmart.api.subscription.infraestructure.db2.entity.OfferMkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstallmentProductMktRepository extends JpaRepository<InstallmentProductMkt, Long> {
    @Query(value = "select count(0) > 0 " +
            "from parcelamento_produto pp " +
            "where pp.oferta = :offerId ", nativeQuery = true)
    Long existsByOfferId(@Param("offerId") Long offerId);
}

