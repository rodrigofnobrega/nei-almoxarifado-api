package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;

import java.sql.Timestamp;

public interface ItemProjection {
    Long getId();
    String getName();
    String getType();
    Long getSipacCode();
    int getQuantity();
    Boolean getAvailable();
    @JsonIgnore
    Timestamp getCreatedAt();
    @JsonProperty("createdBy")
    UserEntity getCreatedBy();
    @JsonProperty("lastRecord")
    RecordEntity getLastRecord();

    @JsonProperty("createdAt")
    default String getFormattedDate() {
        return getCreatedAt().toString();
    }

    interface UserEntity extends UserProjection {}
    interface RecordEntity extends RecordProjection {}
}
