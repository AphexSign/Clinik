package ru.yarm.clinic.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.yarm.clinic.Models.Profession;
import ru.yarm.clinic.Services.ProfessionService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProfessionController {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/profession_admin")
    public String showOrganization(@ModelAttribute("profession") Profession profession, Model model) {
        List<Profession> professions = professionService.getAllProfessionSortedById();
        model.addAttribute("professions", professions);
        return "profession_admin";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/profession_admin")
    public String RegisterProfession(@ModelAttribute("profession") @Valid Profession profession, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("professions", professionService.getAllProfessionSortedById());
            return "profession_admin";
        }

        professionService.addOrganizationToDB(profession);
        return "redirect:/profession_admin";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("profession_admin/{id}/edit")
    public String editProfession(@PathVariable Long id, @ModelAttribute("profession") Profession profession, Model model) {
        Profession profession1 = professionService.getProfessionById(id);
        model.addAttribute("profession", profession1);
        return "profession_info";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/profession_info")
    public String editPositionSubmit(@ModelAttribute("profession") @Valid Profession profession,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "profession_info";
        }

        professionService.updateProfessionToDB(profession);
        return "redirect:/profession_admin";
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("profession_admin/{id}/delete")
    public String deletePosition(@PathVariable Long id) {
        professionService.deleteProfession(id);
        return "redirect:/profession_admin";
    }


}
