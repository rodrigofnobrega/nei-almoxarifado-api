package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import com.ufrn.nei.almoxarifadoapi.repository.projection.component.FormatDateComponent;

import java.sql.Timestamp;

public interface RecordProjection {
    Long getId();
    Integer getQuantity();
    @JsonProperty("operation")
    RecordOperationEnum getOperationEnum();
    @JsonIgnore
    Timestamp getData();
    ItemEntity getItem();
    UserEntity getUser();

    @JsonProperty("creationDate")
    default String getFormattedDate() {
        return FormatDateComponent.formatDate(getData());
    }

    interface UserEntity extends UserProjection {}

    interface ItemEntity {
        Long getId();
        String getName();
        String getType();
        Long getSipacCode();
    }
}
