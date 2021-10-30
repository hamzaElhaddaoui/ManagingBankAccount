package com.bank.managingbankaccount.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "client")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private List<Account> accounts;

}