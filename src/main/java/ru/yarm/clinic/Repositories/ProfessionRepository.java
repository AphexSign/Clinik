package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Profession;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    List<Profession> findAllByOrderByIdAsc();
}
