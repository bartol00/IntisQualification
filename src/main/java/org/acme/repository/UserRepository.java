package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.transaction.Transactional;
import org.acme.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface UserRepository extends PanacheRepository<User> {

}
