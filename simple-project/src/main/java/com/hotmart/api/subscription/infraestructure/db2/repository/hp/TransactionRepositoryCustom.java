package com.hotmart.api.subscription.infraestructure.db2.repository.hp;

import com.hotmart.api.subscription.checkouttokens3.vo.TransactionVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class TransactionRepositoryCustom {
    
    @PersistenceContext(unitName = "db2EntityManagerFactory")
    private EntityManager entityManager;
    
    private static final String GET_DETAILS_BY_TRANSACTION =
            "select " +
                    "    replace(JSON_EXTRACT(CONVERT(from_base64(sp.payload), CHAR), '$.offerCode'), '\"', '') as offerCode, " +
                    "    t.payment_type as paymentType, " +
                    "    t.installments as installments, " +
                    "    sp.value as subscriptionValue, " +
                    "    t.value as transactionValue, " +
                    "    sp.id as paymentId " +
                    "from transaction t " +
                    "join transaction_item ti on t.id = ti.transaction " +
                    "join subscription s on ti.subscription = s.id " +
                    "join subscription_payment sp on sp.id = (select min(sp.id) from subscription_payment sp where sp.subscription = s.id) " +
                    "where t.hotpay_reference = :hotpayReference ";
    
    public TransactionVO getDetailsByTransaction(String hotpayReference) {
        
        try {
            Query query = entityManager.createNativeQuery(GET_DETAILS_BY_TRANSACTION);
            query.setParameter("hotpayReference", hotpayReference);
            Object[] result = (Object[]) query.getSingleResult();
            
            String offerCode = result[0] != null ? result[0].toString() : null;
            String paymentType = result[1] != null ? result[1].toString() : null;
            Integer installments = result[2] != null ? Integer.valueOf(result[2].toString()) : null;
            BigDecimal subscriptionValue = result[3] != null ? new BigDecimal(result[3].toString()) : null;
            BigDecimal transactionValue = result[4] != null ? new BigDecimal(result[4].toString()) : null;
            Long paymentId = result[5] != null ? Long.valueOf(result[5].toString()) : null;
            return new TransactionVO(offerCode, paymentType, installments, subscriptionValue, transactionValue,paymentId);
        } catch (NoResultException ex) {
            return null;
        }
    }
}
