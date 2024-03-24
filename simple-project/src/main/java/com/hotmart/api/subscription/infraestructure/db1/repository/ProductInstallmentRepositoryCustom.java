package com.hotmart.api.subscription.infraestructure.db1.repository;

import com.hotmart.api.subscription.treta.vo.OfferValueVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class ProductInstallmentRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private static final String GET_TOTAL_VALUE =
            "select (t.valor_parcela * t.num_parcela) as totalValue, t.product_offer_currency_code as offerCurrencyCode " +
            "from transacoes t " +
            "where t.chave = :offerKey " +
            "and t.num_parcela = :installmentNumber " +
            "and t.hub_transaction_datetime <= :creationDate " +
            "order by t.hub_transaction_datetime desc limit 1;";
    
    public OfferValueVO getTotalValue(String offerKey,
                                      Integer installmentNumber,
                                      LocalDateTime creationDate) {
        
        try {
            Query query = entityManager.createNativeQuery(GET_TOTAL_VALUE);
            query.setParameter("offerKey", offerKey);
            query.setParameter("installmentNumber", installmentNumber);
            query.setParameter("creationDate", creationDate);
            Object[] result = (Object[]) query.getSingleResult();
            
            BigDecimal totalValue = result[0] != null ? new BigDecimal(result[0].toString()) : null;
            String productOfferCurrencyCode = result[1] != null ? result[1].toString() : null;
            return new OfferValueVO(totalValue, productOfferCurrencyCode);
        } catch (NoResultException ex) {
            return null;
        }
    }
}
