package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findAllByOrderByIdAsc();

    List<User> findAllByBranchOrderByIdAsc(Branch branch);

    Optional<User> findByActivation(String code);


}
