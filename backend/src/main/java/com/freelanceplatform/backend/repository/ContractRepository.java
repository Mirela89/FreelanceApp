package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {
    @Query("SELECT c FROM Contract c WHERE c.project.idProject = :projectId")
    Optional<Contract> findByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT c FROM Contract c WHERE c.freelancer.idUser = :freelancerId")
    List<Contract> findByFreelancerId(@Param("freelancerId") Long freelancerId);

    @Query("SELECT c FROM Contract c WHERE c.freelancer.email = :email")
    List<Contract> findByFreelancerEmail(@Param("email") String email);
}
