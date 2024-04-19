package dev.vk.jfc.app.storage.appstorage.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.vk.jfc.app.storage.appstorage.dto.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    List<Customer> findByLastName(String lastName);

    Optional<Customer> findById(UUID id);
}
