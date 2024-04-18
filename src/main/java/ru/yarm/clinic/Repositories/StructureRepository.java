package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Branch;

import ru.yarm.clinic.Models.Structure;
import ru.yarm.clinic.Models.Department;

import java.util.List;


public interface StructureRepository extends JpaRepository<Structure, Long> {

    Structure findByBranchAndDepartment(Branch branch, Department department);
    List<Structure> findByBranch(Branch branch);


}
