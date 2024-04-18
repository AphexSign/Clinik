package ru.yarm.clinic.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yarm.clinic.Models.Profession;
import ru.yarm.clinic.Services.PortfolioService;
import ru.yarm.clinic.Services.ProfessionService;
import ru.yarm.clinic.Services.UserService;

import java.util.List;

@Controller
public class PortfolioController {
    //Показать всех сотрудников со списком профессий в дальнейшем
    private final UserService userService;
    private final ProfessionService professionService;
    private final PortfolioService portfolioService;

    public PortfolioController(UserService userService, ProfessionService professionService, PortfolioService portfolioService) {
        this.userService = userService;
        this.professionService = professionService;
        this.portfolioService = portfolioService;
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/portfolio_admin")
    public String showUsersForPortfolio(Model model) {
        model.addAttribute("users", userService.getAllByEmployeeOrderByIdAsc());
        return "portfolio_admin";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("user/{id_user}/portfolio")
    public String showProfessionForUser(@ModelAttribute("profession") Profession profession, @PathVariable Long id_user, Model model) {


        List<Profession> professions = professionService.getAllProfessionSortedById();
        model.addAttribute("professions", professions);
        model.addAttribute("user", userService.getUserById(id_user));

        return "portfolio_info";
    }


    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("profession_user/{id_user}/add/{id_profession}")
    public String addProfessionUser(@PathVariable Long id_profession, @PathVariable Long id_user, Model model) {

        portfolioService.addPortfolio(id_user, id_profession);
        return "redirect:/user/" + id_user + "/portfolio";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("profession_user/{id_user}/remove/{id_profession}")
    public String removeProfessionUser(@PathVariable Long id_profession, @PathVariable Long id_user, Model model) {

        portfolioService.removePortfolio(id_user, id_profession);
        return "redirect:/user/" + id_user + "/portfolio";
    }


}
