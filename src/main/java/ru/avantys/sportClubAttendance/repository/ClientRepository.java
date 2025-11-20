package ru.avantys.sportClubAttendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avantys.sportClubAttendance.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByEmail(String email);

    List<Client> findByFullNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    List<Client> findAllClient();

    long count();
}