package com.publicis.sapient.user_search_api.repository;

import com.publicis.sapient.user_search_api.model.Address;
import com.publicis.sapient.user_search_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
