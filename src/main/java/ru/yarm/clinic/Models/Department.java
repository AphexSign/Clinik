package ru.yarm.clinic.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "department")
public class Department {

    private static final String SEQ_NAME = "department_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Название отдела не может быть пустым")
    @Column(name = "label")
    private String label;


    @ManyToMany(mappedBy = "assignedDepartments")
    private List<Branch> assignedBranches;


    public Boolean isAssigned(Integer id_branch) {

        List<Branch> branches = this.assignedBranches;
        List<Integer> ids = new ArrayList<>();
        for (Branch branch : branches) {
            ids.add(new Integer(branch.getId().intValue()));
        }
        return ids.contains(id_branch);
    }


}
