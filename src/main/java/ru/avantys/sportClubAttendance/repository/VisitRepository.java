package ru.avantys.sportClubAttendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avantys.sportClubAttendance.model.Visit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {
    List<Visit> findByMembershipId(UUID membershipId);

    @Query("SELECT COUNT(v) FROM Visit v WHERE v.membership.id = :membershipId")
    long countByMembershipId(@Param("membershipId") UUID membershipId);

    @Query("SELECT v FROM Visit v WHERE v.membership.id = :membershipId ORDER BY v.entryTime DESC LIMIT 1")
    Optional<Visit> findLastVisitByMembershipId(@Param("membershipId") UUID membershipId);
}
