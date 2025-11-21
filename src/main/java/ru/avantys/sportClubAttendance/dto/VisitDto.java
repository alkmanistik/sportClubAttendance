package ru.avantys.sportClubAttendance.dto;

import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.Visit;

import java.time.LocalDateTime;
import java.util.UUID;

public record VisitDto(
        UUID id,
        UUID membershipId,
        LocalDateTime entryTime,
        LocalDateTime exitTime,
        String zone
) {
    public static VisitDto from(Visit visit) {
        return new VisitDto(
                visit.getId(),
                visit.getMembership().getId(),
                visit.getEntryTime(),
                visit.getExitTime(),
                visit.getZone()
        );
    }

    public static Visit toVisit(VisitDto visitDto, Membership membership) {
        var visit = new Visit();
        visit.setId(visitDto.id());
        visit.setMembership(membership);
        visit.setEntryTime(visitDto.entryTime());
        visit.setExitTime(visitDto.exitTime());
        visit.setZone(visitDto.zone());
        return visit;
    }
}