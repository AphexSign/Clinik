package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
