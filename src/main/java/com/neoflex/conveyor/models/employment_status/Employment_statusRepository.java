package com.neoflex.conveyor.models.employment_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Employment_statusRepository extends JpaRepository<Employment_status, Long> {
}
