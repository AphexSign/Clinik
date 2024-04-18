package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Role;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    public User getUserByName(String name) {
        return userRepository.findByName(name).get();
    }


    @Transactional
    public void banUser(Long id) {
        User user = userRepository.getReferenceById(id);
        user.setArchive(true);
        user.setRole(Role.BANNED);
        userRepository.save(user);
    }


    @Transactional
    public void unbanUser(Long id) {
        User user = userRepository.getReferenceById(id);
        user.setArchive(false);
        user.setRole(Role.PATIENT);
        userRepository.save(user);
    }

    @Transactional
    public void mk_manager(Long id) {
        User user = userRepository.getReferenceById(id);
        user.setArchive(false);
        user.setRole(Role.MANAGER);
        userRepository.save(user);
    }

    public List<User> getAllByOrderByIdAsc() {
        return userRepository.findAllByOrderByIdAsc();
    }

    public List<User> getAllByOrganizationOrderByIdAsc(Branch branch) {
        return userRepository.findAllByBranchOrderByIdAsc(branch);
    }

    public List<User> getAllByEmployeeOrderByIdAsc() {
        List<User> users = userRepository.findAllByOrderByIdAsc();
        return users.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        //Удалить все OrderDetails, orders
        userRepository.delete(userRepository.getById(id));
    }

    public boolean activateUser(String code) {

        if (userRepository.findByActivation(code).isPresent()) {
            User user = userRepository.findByActivation(code).get();
            user.setActivation(null);
            user.setRole(Role.PATIENT);
            user.setArchive(false);
            userRepository.save(user);
            return true;
        }
        return false;

    }


}
