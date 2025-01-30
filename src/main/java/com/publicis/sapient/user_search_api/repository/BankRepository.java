package com.publicis.sapient.user_search_api.repository;

import com.publicis.sapient.user_search_api.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
}
