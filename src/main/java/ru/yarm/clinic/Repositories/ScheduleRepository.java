package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Schedule;
import ru.yarm.clinic.Models.Team;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTeam(Team team);
    List<Schedule> findByTeamAndOccupyIsFalse(Team team);
    void deleteAllByTeam(Team team);


}
