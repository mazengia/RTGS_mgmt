package com.rtgs.rtgsapi.otherBranch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface OtherBranchRepository extends JpaRepository<OtherBank, Long>, JpaSpecificationExecutor<OtherBank> {

    }
