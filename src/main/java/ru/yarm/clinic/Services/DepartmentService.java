package ru.yarm.clinic.Services;

import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Department;
import ru.yarm.clinic.Repositories.DepartmentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public List<Department> getAllDepartmentSortedById() {
        return departmentRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void addDepartmentToDB(Department department) {
        departmentRepository.save(department);
    }


    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).get();
    }

    @Transactional
    public void updateDepartmentToDB(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.delete(getDepartmentById(id));

    }


}
