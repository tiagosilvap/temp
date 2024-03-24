package com.hotmart.api.subscription.treta.builder;

import java.util.Map;
import java.util.StringJoiner;

public class MySQLUpdateQueryBuilder {
    
    private String tableName;
    private String tableAlias;
    private Map<String, Object> columnValues;
    private String whereClause;
    
    public MySQLUpdateQueryBuilder(String tableName, String tableAlias, Map<String, Object> columnValues, String whereClause) {
        this.tableName = tableName;
        this.tableAlias = tableAlias;
        this.columnValues = columnValues;
        this.whereClause = whereClause;
    }
    
    public String buildUpdateQuery() {
        if (tableName == null || columnValues == null || columnValues.isEmpty() || whereClause == null) {
            throw new IllegalArgumentException("Parâmetros inválidos para construir a query de UPDATE.");
        }
        
        StringJoiner setClause = new StringJoiner(", ");
        
        String prefix = tableAlias != null && !tableAlias.isEmpty() ? tableAlias + "." : "";
        
        // Itera sobre o mapa de colunas e valores para construir a parte do SET
        for (Map.Entry<String, Object> entry : columnValues.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();
            
            // Checa o tipo de dado e formata de acordo (coloca aspas em strings)
            if (value instanceof String) {
                setClause.add(prefix + column + " = '" + value + "'");
            } else {
                setClause.add(prefix + column + " = " + value);
            }
        }
        
        // Constrói a query final com alias, se disponível
        String query = "UPDATE " + tableName;
        if (tableAlias != null && !tableAlias.isEmpty()) {
            query += " " + tableAlias;
        }
        query += " SET " + setClause.toString() + " WHERE " + prefix + whereClause + ";";
        
        return query;
    }
}
