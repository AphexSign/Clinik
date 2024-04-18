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
@Table(name = "branch")
public class Branch {

    private static final String SEQ_NAME = "branch_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Название филиала не может быть пустым")
    @Column(name = "title")
    private String title;


    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;


    @ManyToMany
    @JoinTable(name = "structure",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> assignedDepartments;


}
