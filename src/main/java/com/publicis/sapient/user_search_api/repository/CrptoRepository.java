package com.publicis.sapient.user_search_api.repository;

import com.publicis.sapient.user_search_api.model.Crypto;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrptoRepository extends JpaRepository<Crypto, Integer> {
}
