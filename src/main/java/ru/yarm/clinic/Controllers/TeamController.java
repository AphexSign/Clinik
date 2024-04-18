package ru.yarm.clinic.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Structure;
import ru.yarm.clinic.Models.Department;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Repositories.StructureRepository;
import ru.yarm.clinic.Services.BranchService;
import ru.yarm.clinic.Services.TeamService;
import ru.yarm.clinic.Services.DepartmentService;
import ru.yarm.clinic.Services.UserService;

import java.util.List;

@Controller
public class TeamController {
    private final StructureRepository structureRepository;
    private final BranchService branchService;
    private final DepartmentService departmentService;
    private final TeamService teamService;
    private final UserService userService;

    public TeamController(StructureRepository structureRepository, BranchService branchService, DepartmentService departmentService, TeamService teamService, UserService userService) {
        this.structureRepository = structureRepository;
        this.branchService = branchService;
        this.departmentService = departmentService;
        this.teamService = teamService;
        this.userService = userService;
    }


    //Открываем новую страницу подразделения
    //Находим по числам Branch и Department - конкретный Branch_Detail
    //И формируем весь список Юзеров
    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("department/{id_department}/assigment/branch/{id_branch}/team")
    public String showDepartmentDetail(@PathVariable Long id_department, @PathVariable Long id_branch, @ModelAttribute("department") Department department, Model model) {


        //Находим вначале Branch_detail по индексам id_depart Id_branch
        Branch branch = branchService.getBranchById(id_branch);
        Department department1 = departmentService.getDepartmentById(id_department);
        List<User> users = userService.getAllByEmployeeOrderByIdAsc();
        //Нашли нашу деталь. На ней могут быть юзеры, а могут и не быть юзеры
        Structure structure = structureRepository.findByBranchAndDepartment(branch, department1);

        //Разместим названия нашего филиала и нашего отдела
        model.addAttribute("branch", branch);
        model.addAttribute("department", department1);

        model.addAttribute("structure", structure);
        model.addAttribute("users", users);
        // Нужно прицепить всех юзеров, какие у нас есть, в дальнейшем должен быть еще и фильтр
        // Чтобы докторов можно было выбирать по филиалу. Доктор же работает в конкретном филиале
        return "team_admin";


    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("structure/{id_structure}/assigment/user/{id_user}/assign")
    public String assignBranchDepartment(@PathVariable Long id_structure, @PathVariable Long id_user, @ModelAttribute("user") User user, Model model) {
        try {
            teamService.assign_doctor(id_structure, id_user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Найти id_department
        //Найти id_branch
        Structure structure = structureRepository.getReferenceById(id_structure);
        Long id_department = structure.getDepartment().getId();
        Long id_branch = structure.getBranch().getId();

        return "redirect:/department/" + id_department + "/assigment/branch/" + id_branch + "/team";

    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("structure/{id_structure}/assigment/user/{id_user}/unassign")
    public String unAssignBranchDepartment(@PathVariable Long id_structure, @PathVariable Long id_user, @ModelAttribute("user") User user, Model model) {
        try {
            teamService.unassign_doctor(id_structure, id_user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Structure structure = structureRepository.getReferenceById(id_structure);
        Long id_department = structure.getDepartment().getId();
        Long id_branch = structure.getBranch().getId();


        return "redirect:/department/" + id_department + "/assigment/branch/" + id_branch + "/team";
    }



}
