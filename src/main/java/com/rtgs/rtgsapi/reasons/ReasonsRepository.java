package com.rtgs.rtgsapi.reasons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ReasonsRepository extends JpaRepository<Reasons, Long>, JpaSpecificationExecutor<Reasons> {

    }
