package com.ufrn.nei.almoxarifadoapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    public Long id;
    public String name;
    public String email;
    private String role;
    private Boolean existRecord;
    public boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role) && Objects.equals(existRecord, that.existRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role, existRecord, active);
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", existRecord=" + existRecord +
                ", active=" + active +
                '}';
    }
}
