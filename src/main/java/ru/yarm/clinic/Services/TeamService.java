package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.*;
import ru.yarm.clinic.Repositories.StructureRepository;
import ru.yarm.clinic.Repositories.TeamRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final StructureRepository structureRepository;

    public TeamService(TeamRepository teamRepository, UserService userService, StructureRepository structureRepository) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.structureRepository = structureRepository;
    }

    @Transactional
    public void assign_doctor(Long id_structure, Long id_user) {
        Structure structure = structureRepository.getReferenceById(id_structure);
        User user = userService.getUserById(id_user);
        Team duplicate = teamRepository.findByStructureAndUser(structure, user);

        if (duplicate == null) {
            Team team = Team.builder().structure(structure).user(user).build();
            teamRepository.save(team);
        }
    }

    @Transactional
    public void unassign_doctor(Long id_structure, Long id_user) {
        Structure structure = structureRepository.getReferenceById(id_structure);
        User user = userService.getUserById(id_user);

        Team team = teamRepository.findByStructureAndUser(structure, user);
        if (team != null) {
            teamRepository.delete(team);
        }
    }


    public List<Team> getTeamsByStructure(Long id_structure) {

        Structure structure = structureRepository.getReferenceById(id_structure);
        return teamRepository.findByStructure(structure);

    }


}
