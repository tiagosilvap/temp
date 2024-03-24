package com.hotmart.api.subscription.infraestructure.db1.repository;

import com.hotmart.api.subscription.infraestructure.db1.entity.ProductInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInstallmentRepository extends JpaRepository<ProductInstallment, Long> {
    @Query(value = "select t.product_offer_currency_code " +
            "from transacoes t " +
            "where t.chave = :offerKey " +
            "and t.num_parcela = :installmentNumber " +
            "order by t.hub_transaction_datetime desc limit 1;", nativeQuery = true)
    String findCurrency(@Param("offerKey") String offerKey,
                        @Param("installmentNumber") Integer installmentNumber);
}
