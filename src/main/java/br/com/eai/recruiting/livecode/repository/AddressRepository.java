package br.com.eai.recruiting.livecode.repository;

import br.com.eai.recruiting.livecode.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByZipCode(String zipCode, Pageable page);
}
