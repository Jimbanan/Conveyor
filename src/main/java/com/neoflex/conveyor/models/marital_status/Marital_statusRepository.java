package com.neoflex.conveyor.models.marital_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Marital_statusRepository extends JpaRepository<Marital_status, Long> {
}
