package com.neoflex.conveyor.models.credit_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Credit_statusRepository extends JpaRepository<Credit_status, Long> {
}
