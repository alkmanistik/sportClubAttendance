package ru.avantys.sportClubAttendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.MembershipType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByClientId(UUID clientId);

    List<Membership> findByType(MembershipType type);

    long count();

    @Query("SELECT COUNT(m) FROM Membership m WHERE m.endDate > CURRENT_TIMESTAMP")
    long countActiveMemberships();
}
