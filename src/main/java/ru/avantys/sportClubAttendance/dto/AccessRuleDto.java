package ru.avantys.sportClubAttendance.dto;

import ru.avantys.sportClubAttendance.model.AccessRule;
import ru.avantys.sportClubAttendance.model.Membership;

import java.time.LocalTime;
import java.util.UUID;

public record AccessRuleDto(
        UUID id,
        UUID membershipId,
        String zone,
        LocalTime validFromTime,
        LocalTime validToTime,
        String allowedDays,
        Integer priority
) {
    public static AccessRuleDto from(AccessRule accessRule) {
        return new AccessRuleDto(
                accessRule.getId(),
                accessRule.getMembership().getId(),
                accessRule.getZone(),
                accessRule.getValidFromTime(),
                accessRule.getValidToTime(),
                accessRule.getAllowedDays(),
                accessRule.getPriority()
        );
    }

    public static AccessRule toAccessRule(AccessRuleDto accessRuleDto, Membership membership) {
        var accessRule = new AccessRule();
        accessRule.setId(accessRuleDto.id());
        accessRule.setMembership(membership);
        accessRule.setZone(accessRuleDto.zone());
        accessRule.setValidFromTime(accessRuleDto.validFromTime());
        accessRule.setValidToTime(accessRuleDto.validToTime());
        accessRule.setAllowedDays(accessRuleDto.allowedDays());
        accessRule.setPriority(accessRuleDto.priority());
        return accessRule;
    }
}