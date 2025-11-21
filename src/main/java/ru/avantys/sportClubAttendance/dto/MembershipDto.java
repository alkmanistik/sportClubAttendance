package ru.avantys.sportClubAttendance.dto;

import ru.avantys.sportClubAttendance.model.Client;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.MembershipType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MembershipDto(
        UUID id,
        UUID clientId,
        String clientName,
        MembershipType type,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer remainingVisits
) {
    public static MembershipDto from(Membership membership) {
        return new MembershipDto(
                membership.getId(),
                membership.getClient().getId(),
                membership.getClient().getFullName(),
                membership.getType(),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getRemainingVisits()
        );
    }

    public static Membership toMembership(MembershipDto membershipDto, Client client) {
        var membership = new Membership();
        membership.setId(membershipDto.id);
        membership.setClient(client);
        membership.setType(membershipDto.type);
        membership.setStartDate(membershipDto.startDate);
        membership.setEndDate(membershipDto.endDate);
        membership.setRemainingVisits(membershipDto.remainingVisits);
        return membership;
    }

}