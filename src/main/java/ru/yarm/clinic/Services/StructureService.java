package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Structure;
import ru.yarm.clinic.Repositories.StructureRepository;

import java.util.List;

@Service
public class StructureService {

    private final StructureRepository structureRepository;
    private final BranchService branchService;

    public StructureService(StructureRepository structureRepository, BranchService branchService) {
        this.structureRepository = structureRepository;
        this.branchService = branchService;
    }

    public List<Structure> getStructuresByBranch(Long id_branch) {

        Branch branch = branchService.getBranchById(id_branch);

        return structureRepository.findByBranch(branch);

    }


}
