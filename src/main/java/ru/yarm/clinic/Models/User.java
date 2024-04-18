package ru.yarm.clinic.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    private static final String SEQ_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;


    @NotEmpty(message = "Номер телефона не должен быть пустым")
    @Size(min = 5, max = 12, message = "Номер телефона должен содержать не менее 5 и не более 12 цифр")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 2, max = 100, message = "ФИО должно быть от 2 до 100 символов длиной")
    @Column(name = "fio")
    private String fio;


    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 3, max = 100, message = "Пароль не может быть меньше 3-х символов")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Адрес эл.почты не может быть пустым")
    @Email
    @Column(name = "email")
    private String email;

//    @NotEmpty(message = "Адрес для доставки товара не может быть пустым")
    @Column(name = "address")
    private String address;

//    @Size(min = 5, max = 12, message = "Номер телефона должен содержать не менее 5 и не более 12 цифр")
    @Column(name = "telephone")
    private String telephone;

    private boolean archive;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "activation")
    private String activation;

    @Transient
    private String new_password;


    public String getArchive() {
        if (this.archive) {
            return "Да";
        } else {
            return "Нет";
        }
    }

    public String showPassword() {
        return "*******";
    }

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

//    @ManyToOne
//    @JoinColumn(name = "profession_id")
//    private Profession profession;


    //Каждому юзеру может принадлежать множество Structures !!!
    //Это целый лист

    @ManyToMany(mappedBy = "appointedUsers")
    private List<Structure> appointedStructures;


    public Boolean isAppointed(Integer structure_id) {

        List<Structure> structures = this.appointedStructures;
        List<Integer> ids = new ArrayList<>();
        for (Structure structure : structures) {
            ids.add(new Integer(structure.getId().intValue()));
        }
        return ids.contains(structure_id);
    }

    @ManyToMany(mappedBy = "graduatedUsers")
    private List<Profession> knownProfessions;

    public Boolean isGraduated(Integer profession_id) {

        List<Profession> professions = this.knownProfessions;
        List<Integer> ids = new ArrayList<>();
        for (Profession profession : professions) {
            ids.add(new Integer(profession.getId().intValue()));
        }
        return ids.contains(profession_id);
    }

    public String getGraduations() {

        List<String> words = new ArrayList<>();
        for (Profession profession : this.knownProfessions) {
            words.add(profession.getTitle());
        }
        String joinedString = String.join(", ", words);
        String formattedString = "";

        if (joinedString.length() > 0) {
            formattedString = joinedString + ";";
        } else formattedString = "Нет специальности";

        return formattedString;

    }




}