package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;

public interface ItemProjection {
    Long getId();
    String getName();
    String getType();
    Long getSipacCode();
    int getQuantity();
    Boolean getAvailable();
    @JsonProperty("createdBy")
    UserEntity getCreatedBy();
    @JsonProperty("lastRecord")
    RecordEntity getLastRecord();

    interface UserEntity extends UserProjection {}
    interface RecordEntity extends RecordProjection {}
}
