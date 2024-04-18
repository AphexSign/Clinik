package ru.yarm.clinic.Controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.yarm.clinic.Models.*;
import ru.yarm.clinic.Repositories.StructureRepository;
import ru.yarm.clinic.Repositories.TeamRepository;
import ru.yarm.clinic.Services.ScheduleService;
import ru.yarm.clinic.Services.UserService;


import java.util.List;

@Controller
public class ScheduleController {


    private final TeamRepository teamRepository;
    private final StructureRepository structureRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    public ScheduleController(TeamRepository teamRepository, StructureRepository structureRepository, UserService userService, ScheduleService scheduleService) {
        this.teamRepository = teamRepository;
        this.structureRepository = structureRepository;
        this.userService = userService;
        this.scheduleService = scheduleService;
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("structure/{id_structure}/schedule/user/{id_user}/edit")
    public String showSchedule(@ModelAttribute("times") Times times, @PathVariable Long id_structure, @PathVariable Long id_user, Model model) {


        Structure structure = structureRepository.getReferenceById(id_structure);
        User user = userService.getUserById(id_user);
        Team team = teamRepository.findByStructureAndUser(structure, user);

        model.addAttribute("team", team);

        List<Schedule> schedules = scheduleService.getScheduleByTeamAsc(team.getId());
        model.addAttribute("schedules", schedules);

        return "schedule_admin";
    }

    // Назначить расписание
    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/schedule_team/{id_team}/add")
    public String addSchedule(@ModelAttribute("times") Times times, BindingResult bindingResult,
                              @PathVariable Long id_team, Model model) {


        Team team = teamRepository.getReferenceById(id_team);
        scheduleService.addTimeOneDay(id_team, times);
        Long id_structure = team.getStructure().getId();
        Long id_user = team.getUser().getId();
        model.addAttribute("team", team);

        return "redirect:/structure/" + id_structure + "/schedule/user/" + id_user + "/edit";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("schedule_team/{id_team}/remove")
    public String removeAllSchedule(@PathVariable Long id_team, Model model) {

        Team team = teamRepository.getReferenceById(id_team);
        scheduleService.deleteAllSlots(id_team);
        Long id_structure = team.getStructure().getId();
        Long id_user = team.getUser().getId();
        model.addAttribute("team", team);

        return "redirect:/structure/" + id_structure + "/schedule/user/" + id_user + "/edit";
    }




}
