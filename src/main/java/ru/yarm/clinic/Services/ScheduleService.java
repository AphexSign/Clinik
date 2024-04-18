package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Schedule;
import ru.yarm.clinic.Models.Team;
import ru.yarm.clinic.Models.Times;
import ru.yarm.clinic.Repositories.ScheduleRepository;
import ru.yarm.clinic.Repositories.TeamRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeamRepository teamRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, TeamRepository teamRepository) {
        this.scheduleRepository = scheduleRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void addTime(Long id_team) {

        Team team = teamRepository.getReferenceById(id_team);
        LocalDateTime localDateTime = LocalDateTime.of(2023, Month.JULY, 29, 19, 30, 40);
        Schedule schedule = Schedule.builder().team(team).Slot(localDateTime).build();
        scheduleRepository.save(schedule);
    }


    // Сделать форму выбора конкретной даты для формирования расписания
    // ДАТА - 01.01.2020
    // Начало времени заполнения - 8:00
    // Конечное время заполнения - 20:00
    // Промежуток: 10/20/30/40/50/60

    //Предусмотреть ручное удаление времени - наличие мусорной корзины
    //Предусмотреть ручное добавление времени


    @Transactional
    public void addTimeOneDay(Long id_team, Times times) {

        Team team = teamRepository.getReferenceById(id_team);
        List<Schedule> scheduleList = new ArrayList<>();

        LocalDate date_start = times.getDate_start();
        Long hourBegin = times.getHourBegin();
        Long hourEnd = times.getHourEnd();
        Long interval = times.getInterval();

        LocalDateTime localDateTime = date_start.atStartOfDay();
        localDateTime = localDateTime.plusHours(hourBegin);
        localDateTime = localDateTime.minusMinutes(interval);

        Long quant_long = (hourEnd * 60L - hourBegin * 60L) / interval;
        int quantity = quant_long.intValue();

        for (int i = 0; i <= quantity; i++) {
            localDateTime = localDateTime.plusMinutes(interval);
            Schedule schedule = Schedule.builder().team(team).Slot(localDateTime).occupy(false).build();
            scheduleList.add(schedule);
        }
        scheduleRepository.saveAll(scheduleList);
    }


    //Удаляет все слоты на все дни
    //Предусмотреть удаление только на конкретный день
    //Предусмотреть удаление с даты на дату
    //Сделать систему удаления как в Excel - 1,3,5-10 (ДНИ)


    @Transactional
    public void deleteAllSlots(Long id_team) {
        Team team = teamRepository.getReferenceById(id_team);
        scheduleRepository.deleteAllByTeam(team);

    }

    @Transactional
    public void deleteOneSlotBySchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);

    }

    @Transactional
    public void makeOccupySchedule(Schedule schedule) {
        schedule.setOccupy(Boolean.TRUE);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void makeOccupyFreeSchedule(Schedule schedule) {
        schedule.setOccupy(Boolean.FALSE);
        scheduleRepository.save(schedule);
    }


    public List<Schedule> getScheduleByTeam(Long id_team) {
        Team team = teamRepository.getReferenceById(id_team);
        return scheduleRepository.findByTeam(team);

    }


    public Schedule getSingleScheduleById(Long id_schedule) {
        return scheduleRepository.getReferenceById(id_schedule);

    }


    public List<Schedule> getScheduleByTeamAsc(Long id_team) {
        Team team = teamRepository.getReferenceById(id_team);
        List<Schedule> schedules = scheduleRepository.findByTeam(team);
        schedules.sort(Comparator.comparing(Schedule::getSlot));
        return schedules;
    }

    public List<Schedule> getFreeScheduleByTeamAsc(Long id_team) {
        Team team = teamRepository.getReferenceById(id_team);
        List<Schedule> schedules = scheduleRepository.findByTeamAndOccupyIsFalse(team);
        schedules.sort(Comparator.comparing(Schedule::getSlot));
        return schedules;
    }


}
