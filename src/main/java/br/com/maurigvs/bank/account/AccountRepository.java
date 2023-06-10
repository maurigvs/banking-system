package br.com.maurigvs.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Long, Account> {

    Optional<Account> findByNumber(Long number);
}