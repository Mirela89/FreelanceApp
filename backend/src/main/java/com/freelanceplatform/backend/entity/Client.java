package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.ClientType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="client")
@PrimaryKeyJoinColumn(name = "id_user") // This column will be the primary key and also a foreign key to User
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Client extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type", nullable = false, length = 20)
    private ClientType clientType;

    @Column(name = "company_name", length = 150)
    private String companyName;

    @Column(name = "tax_id", length = 20)
    private String taxId;
}