package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.repository.projection.component.FormatDateComponent;

import java.sql.Timestamp;

public interface RequestProjection {
    Long getId();
    RequestStatusEnum getStatus();
    String getDescription();
    @JsonProperty("quantityRequested")
    Long getQuantity();
    UserEntity getUser();
    ItemEntity getItem();
    @JsonIgnore
    Timestamp getCreatedAt();
    @JsonIgnore
    Timestamp getUpdatedAt();

    @JsonProperty("creationDate")
    default String getFormattedDateByCreatedAt() {
        return FormatDateComponent.formatDate(getCreatedAt());
    }

    @JsonProperty("updatedDate")
    default String getFormattedDateByUpdatedAt() {
        return FormatDateComponent.formatDate(getUpdatedAt());
    }

    interface UserEntity extends UserProjection {}
    interface ItemEntity extends ItemProjection {}
}
