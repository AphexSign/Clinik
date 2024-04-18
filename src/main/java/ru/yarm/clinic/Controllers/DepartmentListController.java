package ru.yarm.clinic.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.Department;
import ru.yarm.clinic.Services.DepartmentService;

import javax.validation.Valid;
import java.util.List;


@Controller
public class DepartmentListController {

    private final DepartmentService departmentService;

    public DepartmentListController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/department_admin")
    public String showDepartment(@ModelAttribute("department") Department department, Model model) {
        List<Department> departments = departmentService.getAllDepartmentSortedById();
        model.addAttribute("departments", departments);
        return "department_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/department_admin")
    public String RegisterDepartment(@ModelAttribute("department") @Valid Department department, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartmentSortedById());
            return "department_admin";
        }

        departmentService.addDepartmentToDB(department);
        return "redirect:/department_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("department_admin/{id}/edit")
    public String editDepartment(@PathVariable Long id, @ModelAttribute("department") Department department, Model model) {
        Department department1 = departmentService.getDepartmentById(id);
        model.addAttribute("department", department1);
        return "department_info";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/department_info")
    public String editDepartmentSubmit(@ModelAttribute("department") @Valid Department department,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "department_info";
        }

        departmentService.updateDepartmentToDB(department);
        return "redirect:/department_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("department_admin/{id}/delete")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);


        return "redirect:/department_admin";
    }


}
