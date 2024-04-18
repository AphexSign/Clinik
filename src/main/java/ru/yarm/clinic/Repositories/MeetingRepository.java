package ru.yarm.clinic.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yarm.clinic.Models.Meeting;

import ru.yarm.clinic.Models.User;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByPatient(User patient);


}
