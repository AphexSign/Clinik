package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Structure;
import ru.yarm.clinic.Models.Team;
import ru.yarm.clinic.Models.User;

import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {


    Team findByStructureAndUser(Structure structure, User user);

    List<Team> findByStructure(Structure structure);


}
