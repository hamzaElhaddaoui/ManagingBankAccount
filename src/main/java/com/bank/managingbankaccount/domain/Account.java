package com.bank.managingbankaccount.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "account")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "balance")
    private int balance;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private List<Operation> operations;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}