package com.zalizniak.cassandradao;


import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@ToString
@Getter
@Setter
@Table("customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @PrimaryKey
    private UUID id;
    private String firstName;
    private String lastName;
}