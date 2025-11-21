package ru.avantys.sportClubAttendance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avantys.sportClubAttendance.dto.AccessRuleDto;
import ru.avantys.sportClubAttendance.dto.MembershipDto;
import ru.avantys.sportClubAttendance.model.AccessRule;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.repository.AccessRuleRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccessService {
    private final AccessRuleRepository accessRuleRepository;
    private final MembershipService membershipService;

    public AccessService(AccessRuleRepository accessRuleRepository, MembershipService membershipService) {
        this.accessRuleRepository = accessRuleRepository;
        this.membershipService = membershipService;
    }

    public AccessRule createAccessRule(AccessRuleDto accessRuleDto, UUID membershipId) {
        Membership membership = membershipService.getMembershipById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found with id: " + membershipId));

        AccessRule accessRule = AccessRuleDto.toAccessRule(accessRuleDto, membership);
        return accessRuleRepository.save(accessRule);
    }

    @Transactional(readOnly = true)
    public List<AccessRule> getAccessRulesByMembership(UUID membershipId) {
        return accessRuleRepository.findByMembershipId(membershipId);
    }

    public void deleteAccessRule(UUID id) {
        if (!accessRuleRepository.existsById(id)) {
            throw new IllegalArgumentException("AccessRule not found with id: " + id);
        }
        accessRuleRepository.deleteById(id);
    }
}