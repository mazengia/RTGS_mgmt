package com.rtgs.rtgsapi.rtgs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface RtgsRepository extends JpaRepository<Rtgs, Long>, JpaSpecificationExecutor<Rtgs> {

    }
