package ru.yarm.clinic.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.clinic.Models.*;
import ru.yarm.clinic.Services.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MeetingController {
    private final BranchService branchService;
    private final StructureService structureService;
    private final TeamService teamService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final MeetingService meetingService;

    public MeetingController(BranchService branchService, StructureService structureService,
                             TeamService teamService, ScheduleService scheduleService,
                             UserService userService, MeetingService meetingService) {
        this.branchService = branchService;
        this.structureService = structureService;
        this.teamService = teamService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.meetingService = meetingService;

    }


    @GetMapping("/meet_branch")
    public String showBranchPatient(Model model) {
        List<Branch> branches = branchService.getAllBranchSortedById();
        model.addAttribute("branches", branches);
        return "meet_branch";
    }


    @GetMapping("/meet_branch/{id_branch}/enter")
    public String showStructuresPatient(@PathVariable Long id_branch, Model model) {
        List<Structure> structures = structureService.getStructuresByBranch(id_branch);
        model.addAttribute("structures", structures);
        return "meet_structures";
    }


    @GetMapping("/meet_structure/{id_structure}/enter")
    public String showTeamsPatient(@PathVariable Long id_structure, Model model) {
        //Найти все команды по Номеру Строения(отделения)
        List<Team> teams = teamService.getTeamsByStructure(id_structure);
        model.addAttribute("teams", teams);
        return "meet_teams";
    }

    @GetMapping("/meet_schedules/{id_team}/enter")
    public String showSchedulesPatient(@PathVariable Long id_team, Model model) {
        //Найти все команды по Номеру Строения(отделения)
        List<Schedule> schedules = scheduleService.getFreeScheduleByTeamAsc(id_team);

        model.addAttribute("schedules", schedules);
        return "meet_schedules";
    }

    // При нажатии на время, будет проверка - Principal - аутентифицирован или нет
    // Если нет, значит направляем на страницу регистрации
    @GetMapping("/meeting/{id_schedule}/confirm")
    public String makeMeeting(@PathVariable Long id_schedule, Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            User patient = userService.getUserByName(userName);
            Schedule schedule = scheduleService.getSingleScheduleById(id_schedule);
            meetingService.saveMeeting(schedule.getId(), patient.getId());
            model.addAttribute("schedule", schedule);
        }
        return "meet_confirmed";
    }


    @GetMapping("/meeting_my")
    public String showAllMyMeeting(Model model, Principal principal) {
        if (principal != null) {

            //Выложить все записи пользователя
            String userName = principal.getName();
            User patient = userService.getUserByName(userName);

            List<Meeting> meetings = meetingService.findAllMeetingByUser(patient);
            model.addAttribute("meetings", meetings);
        }
        return "meet_my";
    }


    @GetMapping("meeting/{id_meeting}/cancel")
    public String cancelMeeting(@PathVariable Long id_meeting, Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            User patient = userService.getUserByName(userName);

            Meeting meeting = meetingService.findOneMeetingByUser(patient, id_meeting);
            //Нужно освободить у врача такую встречу!
            Schedule schedule = meeting.getSchedule();
            scheduleService.makeOccupyFreeSchedule(schedule);
            //Нужно удалить такую встречу
            meetingService.deleteMeeting(meeting);
        }
        return "redirect:/meeting_my";
    }


}
