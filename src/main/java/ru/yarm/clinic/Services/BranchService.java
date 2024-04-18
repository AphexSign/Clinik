package ru.yarm.clinic.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Structure;
import ru.yarm.clinic.Models.Department;
import ru.yarm.clinic.Models.Team;
import ru.yarm.clinic.Repositories.StructureRepository;
import ru.yarm.clinic.Repositories.BranchRepository;
import ru.yarm.clinic.Repositories.DepartmentRepository;
import ru.yarm.clinic.Repositories.TeamRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final StructureRepository structureRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository, DepartmentRepository departmentRepository, StructureRepository structureRepository, TeamRepository teamRepository) {
        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
        this.structureRepository = structureRepository;
        this.teamRepository = teamRepository;
    }

    public List<Branch> getAllBranchSortedById() {
        return branchRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void addBranchToDB(Branch branch) {
        branchRepository.save(branch);
    }

    public Branch getBranchById(Long id) {
        return branchRepository.findById(id).get();
    }

    @Transactional
    public void updateBranchToDB(Branch branch) {
        branchRepository.save(branch);
    }


    @Transactional
    public void deleteBranch(Long id) {
        Branch branch = getBranchById(id);
        List<Structure> structureList = structureRepository.findByBranch(branch);

        if (structureList.size() == 0) {
            branchRepository.delete(branch);
        }
    }


    @Transactional
    public void assign_departmentBranch(Long id_branch, Long id_department) throws Exception {

        Branch branch = branchRepository.findById(id_branch).get();
        Department department = departmentRepository.findById(id_department).get();

        Structure duplicate = structureRepository.findByBranchAndDepartment(branch, department);

        if (duplicate == null) {
            Structure structure = Structure.builder().branch(branch).department(department).build();
            structureRepository.save(structure);
        }
    }

    @Transactional
    public void unassign_departmentBranch(Long id_branch, Long id_department) throws Exception {
        Branch branch = branchRepository.findById(id_branch).get();
        Department department = departmentRepository.findById(id_department).get();

        Structure structure = structureRepository.findByBranchAndDepartment(branch, department);
        List<Team> team = teamRepository.findByStructure(structure);

        if (structure != null && team.size() == 0) {
            structureRepository.delete(structure);
        }

    }


}
