package ru.avantys.sportClubAttendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avantys.sportClubAttendance.model.AccessRule;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessRuleRepository extends JpaRepository<AccessRule, UUID> {
    List<AccessRule> findByMembershipId(UUID membershipId);
}
