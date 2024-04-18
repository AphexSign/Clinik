package ru.yarm.clinic.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yarm.clinic.Models.Profession;
import ru.yarm.clinic.Repositories.ProfessionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProfessionService {

    private final ProfessionRepository professionRepository;

    @Autowired
    public ProfessionService(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    public List<Profession> getAllProfessionSortedById() {
        return professionRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void addOrganizationToDB(Profession profession) {
        professionRepository.save(profession);
    }

    public Profession getProfessionById(Long id) {
        return professionRepository.findById(id).get();
    }

    @Transactional
    public void updateProfessionToDB(Profession profession) {
        professionRepository.save(profession);
    }

    @Transactional
    public void deleteProfession(Long id) {
        professionRepository.delete(getProfessionById(id));
    }


}
