package com.ufrn.nei.almoxarifadoapi.repository.projection;

public interface ItemProjection {
    Long getId();
    String getName();
    String getType();
    Long getSipacCode();
    int getQuantity();
    Boolean getAvailable();
}
