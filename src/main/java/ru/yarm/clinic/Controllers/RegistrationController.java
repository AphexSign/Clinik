package ru.yarm.clinic.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Services.RegistrationService;
import ru.yarm.clinic.Services.UserValidator;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final RegistrationService registrationService;          //Спрятаны методы регистрации: Шифрование + сохранение в БД
    private final UserValidator userValidator;                      //Добавляем проверочные методы

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }


    @GetMapping("/regist")
    public String ShowRegistrationPage(@ModelAttribute("user") User user) {
        return "regist";
    }


    @PostMapping("/regist")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) return "/regist";

        registrationService.register(user);
        return "redirect:/auth/login";
    }


}
