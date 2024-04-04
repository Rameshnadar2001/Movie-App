package com.bej.authentication.repository;


import com.bej.authentication.domain.Confirmations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationsRepository extends JpaRepository<Confirmations,Long> {
    Confirmations findByToken(String token);
}
