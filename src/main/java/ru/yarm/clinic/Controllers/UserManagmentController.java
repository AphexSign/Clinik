package ru.yarm.clinic.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Services.BranchService;
import ru.yarm.clinic.Services.RegistrationService;
import ru.yarm.clinic.Services.UserService;
import ru.yarm.clinic.Services.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

// Управление подчиненными - работают только админы, создают своих подчиненных, привязанных
// к одной организации

@Controller
public class UserManagmentController {

    private final UserService userService;
    private final BranchService branchService;
    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public UserManagmentController(UserService userService, BranchService branchService, UserValidator userValidator, RegistrationService registrationService) {
        this.userService = userService;
        this.branchService = branchService;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user_managment")
    public String showUsers(Model model, @ModelAttribute("user") User user, Principal principal) {
        //Вывод нужно сделать таким, чтобы выходила только организация, которая есть у админа
        //Распознаем нашего админа во время входа в меню, находим его
        User myUser = userService.getUserByName(principal.getName());
        //Опознаем его организацию
        Branch myBranch = myUser.getBranch();
        //Выводим только тех сотрудников, которые имеют ту же организацию
        List<User> users = userService.getAllByOrganizationOrderByIdAsc(myBranch);

        model.addAttribute("users", users);
        model.addAttribute("organization", myBranch);
        return "user_managment";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user_managment/registration")
    public String performManagerRegistration(@ModelAttribute("user") @Valid User user,
                                             BindingResult bindingResult, Model model, Principal principal) {
        userValidator.validate(user, bindingResult);

        //Распознаем нашего админа во время входа в меню, находим его
        User myUser = userService.getUserByName(principal.getName());
        Branch myBranch = myUser.getBranch();

        if (bindingResult.hasErrors()) {
            List<User> users = userService.getAllByOrganizationOrderByIdAsc(myBranch);
            model.addAttribute("users", users);
            model.addAttribute("organization", myBranch);
            return "/user_managment";
        }


        // Сохраняем юзера в базу данных - без всякой активации
        // Будем сохранять как обычного пользователя, временно присваивая ему должность
        registrationService.save_employee(user, myBranch);
        return "redirect:/user_managment";
    }


}
