package ru.yarm.clinic.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.yarm.clinic.Models.Branch;
import ru.yarm.clinic.Models.Role;
import ru.yarm.clinic.Models.User;
import ru.yarm.clinic.Repositories.UserRepository;


@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final ProfessionService professionService;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Autowired
    public RegistrationService(UserRepository userRepository, ProfessionService professionService, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepository = userRepository;
        this.professionService = professionService;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }


    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.PATIENT);
        user.setArchive(false);


//        user.setActivation(UUID.randomUUID().toString());
//        mailSender.send(user.getEmail(),"Ваша учетная запись требует активации",user.getActivation());
        userRepository.save(user);
    }

    //Специальный метод для сохранения Хозяином - админов, у них будет роль admin
    //Они будут активны по-умолчанию
    //Им не нужна активация!
    @Transactional
    public void save_admin(User user) {
        // По-умолчанию должность с индексом 2 - будет админ! или тимлид
        // Нужно инициализировать изначальный список должностей
        //  Profession tmpProfession = professionService.getProfessionById(2L);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.EMPLOYEE);
        user.setArchive(false);
//        user.setProfession(tmpProfession);
//        user.setActivation(UUID.randomUUID().toString());
//        mailSender.send(user.getEmail(),"Ваша учетная запись требует активации",user.getActivation());
        userRepository.save(user);
    }


    @Transactional
    public void save_employee(User user, Branch branch) {
        //  По-умолчанию должность с индексом 2 - будет подчиненный! или тимлид
        //  Нужно инициализировать изначальный список должностей
        //  Profession tmpProfession = professionService.getPositionById(3L);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Роль - сотрудник. Это может быть и доктор и медсестра. Не пациент
        // У пациента-своя роль и свои меню
        user.setRole(Role.EMPLOYEE);
        user.setArchive(false);
        user.setBranch(branch);
        //        user.setProfession(tmpProfession);

        //        user.setActivation(UUID.randomUUID().toString());
        //        mailSender.send(user.getEmail(),"Ваша учетная запись требует активации",user.getActivation());
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(User user) {

        User userToUpdate = userRepository.getReferenceById(user.getId());
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        // user.setRole(Role.CLIENT);
        userToUpdate.setFio(user.getFio());
        //  userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAddress(user.getAddress());
        userToUpdate.setTelephone(user.getTelephone());

    }

    @Transactional
    public void updateUser_login(User user) {
        User userToUpdate = userRepository.getReferenceById(user.getId());
        userToUpdate.setName(user.getName());

    }

    @Transactional
    public void updateUser_email(User user) {
        User userToUpdate = userRepository.getReferenceById(user.getId());
        userToUpdate.setEmail(user.getEmail());
    }

    @Transactional
    public void updateUser_password(User user) {
        User userToUpdate = userRepository.getReferenceById(user.getId());
        // String new_password=user.getNew_password();
        userToUpdate.setPassword(passwordEncoder.encode(user.getNew_password()));
    }


}
