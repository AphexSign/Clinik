package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Branch;


import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByOrderByIdAsc();
}
