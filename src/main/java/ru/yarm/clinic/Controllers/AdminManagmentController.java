package ru.yarm.clinic.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Services.BranchService;
import ru.yarm.clinic.Services.RegistrationService;
import ru.yarm.clinic.Services.UserService;
import ru.yarm.clinic.Services.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminManagmentController {

    private final UserService userService;
    private final BranchService branchService;
    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AdminManagmentController(UserService userService, BranchService branchService, UserValidator userValidator, RegistrationService registrationService) {
        this.userService = userService;
        this.branchService = branchService;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/admin_managment")
    public String showUsers(Model model, @ModelAttribute("user") User user) {
        List<User> users = userService.getAllByEmployeeOrderByIdAsc();
        model.addAttribute("users", users);
        model.addAttribute("branches", branchService.getAllBranchSortedById());
        return "admin_managment";
    }


    @PostMapping("/admin_managment/registration")
    public String performEmployeeRegistration(@ModelAttribute("user") @Valid User user,
                                              BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            List<User> users = userService.getAllByEmployeeOrderByIdAsc();
            model.addAttribute("users", users);
            model.addAttribute("branches", branchService.getAllBranchSortedById());
            return "/admin_managment";
        }

        //Просто сохраняем юзера в базу данных - без всякой активации
        registrationService.save_admin(user);
        return "redirect:/admin_managment";
    }


}
