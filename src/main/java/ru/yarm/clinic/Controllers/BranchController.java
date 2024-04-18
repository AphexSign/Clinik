package ru.yarm.clinic.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Department;
import ru.yarm.clinic.Services.BranchService;
import ru.yarm.clinic.Services.DepartmentService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BranchController {

    private final BranchService branchService;
    private final DepartmentService departmentService;

    public BranchController(BranchService branchService, DepartmentService departmentService) {
        this.branchService = branchService;
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/branch_admin")
    public String showBranch(@ModelAttribute("branch") Branch branch, Model model) {
        List<Branch> branches = branchService.getAllBranchSortedById();
        model.addAttribute("branches", branches);
        return "branch_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/branch_admin")
    public String RegisterBranch(@ModelAttribute("branch") @Valid Branch branch, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("branches", branchService.getAllBranchSortedById());
            return "branch_admin";
        }

        branchService.addBranchToDB(branch);
        return "redirect:/branch_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("branch_admin/{id}/edit")
    public String editBranch(@PathVariable Long id, @ModelAttribute("branch") Branch branch, Model model) {
        Branch branch1 = branchService.getBranchById(id);
        model.addAttribute("branch", branch1);
        model.addAttribute("departments", departmentService.getAllDepartmentSortedById());
        return "branch_info";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/branch_info")
    public String editBranchSubmit(@ModelAttribute("branch") @Valid Branch branch,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "branch_info";
        }

        branchService.updateBranchToDB(branch);
        return "redirect:/branch_admin";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("branch_admin/{id}/delete")
    public String deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return "redirect:/branch_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("department/{id_department}/assigment/branch/{id_branch}/assign")
    public String assignBranchDepartment(@PathVariable Long id_department, @PathVariable Long id_branch, @ModelAttribute("department") Department department, Model model) {
        try {
            branchService.assign_departmentBranch(id_branch, id_department);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/branch_admin/" + id_branch + "/edit";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("department/{id_department}/assigment/branch/{id_branch}/unassign")
    public String unAssignBranchDepartment(@PathVariable Long id_department, @PathVariable Long id_branch, @ModelAttribute("department") Department department, Model model) {

        try {
            branchService.unassign_departmentBranch(id_branch, id_department);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/branch_admin/" + id_branch + "/edit";
    }


}
