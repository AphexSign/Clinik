package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Portfolio;
import ru.yarm.clinic.Models.Profession;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Repositories.PortfolioRepository;

import javax.transaction.Transactional;

@Service
public class PortfolioService {

    private final ProfessionService professionService;
    private final UserService userService;
    private final PortfolioRepository portfolioRepository;

    public PortfolioService(ProfessionService professionService, UserService userService, PortfolioRepository portfolioRepository) {
        this.professionService = professionService;
        this.userService = userService;
        this.portfolioRepository = portfolioRepository;
    }

    @Transactional
    public void addPortfolio(Long id_user, Long id_profession) {
        User user = userService.getUserById(id_user);
        Profession profession = professionService.getProfessionById(id_profession);

        Portfolio duplicate = portfolioRepository.findByUserAndProfession(user, profession);

        if (duplicate == null) {
            Portfolio portfolio = Portfolio.builder().user(user).profession(profession).build();
            portfolioRepository.save(portfolio);
        }

    }

    @Transactional
    public void removePortfolio(Long id_user, Long id_profession) {

        User user = userService.getUserById(id_user);
        Profession profession = professionService.getProfessionById(id_profession);

        Portfolio portfolio = portfolioRepository.findByUserAndProfession(user, profession);

        if (portfolio != null) {
            portfolioRepository.delete(portfolio);
        }
    }


}
