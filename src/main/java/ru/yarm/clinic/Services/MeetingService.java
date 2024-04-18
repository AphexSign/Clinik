package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Meeting;
import ru.yarm.clinic.Models.Schedule;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Repositories.MeetingRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ScheduleService scheduleService;
    private final UserService userService;

    public MeetingService(MeetingRepository meetingRepository, ScheduleService scheduleService, UserService userService) {
        this.meetingRepository = meetingRepository;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }


    @Transactional
    public void saveMeeting(Long id_schedule, Long id_patient) {

        User patient = userService.getUserById(id_patient);
        Schedule schedule = scheduleService.getSingleScheduleById(id_schedule);
        User doctor = schedule.getTeam().getUser();
        LocalDateTime created = LocalDateTime.now();

        Meeting meeting = Meeting.builder()
                .patient(patient)
                .doctor(doctor)
                .schedule(schedule)
                .created(created)
                .updated(created)
                .paided_id(1L)
                .status_id(1L)
                .build();

        meetingRepository.save(meeting);
        scheduleService.makeOccupySchedule(schedule);
    }


    public List<Meeting> findAllMeetingByUser(User patient) {
        return meetingRepository.findByPatient(patient);
    }


    public Meeting findOneMeetingByUser(User user, Long id_meeting) {
        Meeting meeting = meetingRepository.getReferenceById(id_meeting);
        if (meeting.getPatient().getName().equals(user.getName())) {
            return meeting;
        } else return null;

    }

    @Transactional
    public void deleteMeeting(Meeting meeting) {
        meetingRepository.delete(meeting);
    }
}
