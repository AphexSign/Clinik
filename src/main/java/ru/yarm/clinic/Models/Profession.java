package ru.yarm.clinic.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profession")
public class Profession {

    private static final String SEQ_NAME = "profession_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Название профессии не может быть пустым")
    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(name = "portfolio",
            joinColumns = @JoinColumn(name = "profession_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> graduatedUsers;



}
