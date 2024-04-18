package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Portfolio;
import ru.yarm.clinic.Models.Profession;
import ru.yarm.clinic.Models.User;


public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findByUserAndProfession(User user, Profession profession);


}
