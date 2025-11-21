package ru.avantys.sportClubAttendance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avantys.sportClubAttendance.dto.MembershipDto;
import ru.avantys.sportClubAttendance.dto.VisitDto;
import ru.avantys.sportClubAttendance.model.Membership;
import ru.avantys.sportClubAttendance.model.Visit;
import ru.avantys.sportClubAttendance.repository.VisitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VisitService {
    private final VisitRepository visitRepository;
    private final MembershipService membershipService;

    public VisitService(VisitRepository visitRepository, MembershipService membershipRepository) {
        this.visitRepository = visitRepository;
        this.membershipService = membershipRepository;
    }

    public Visit createVisit(MembershipDto membershipDto, VisitDto visitDto) {
        Membership membership = membershipService.getMembershipById(membershipDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Membership not found with id: " + membershipDto.id()));
        Visit oldVisit = visitRepository.findLastVisitByMembershipId(membershipDto.id()).orElse(null);
        closeOldVisit(oldVisit);

        Visit visit = new Visit();
        visit.setMembership(membership);
        visit.setEntryTime(LocalDateTime.now());
        visit.setZone(visitDto.zone());

        return visitRepository.save(visit);
    }

    @Transactional(readOnly = true)
    public List<Visit> getVisitsByMembership(UUID membershipId) {
        return visitRepository.findByMembershipId(membershipId);
    }

    @Transactional(readOnly = true)
    public long getVisitCountByMembership(UUID membershipId) {
        return visitRepository.countByMembershipId(membershipId);
    }

    @Transactional(readOnly = true)
    public Optional<Visit> getLastVisitByMembership(UUID membershipId) {
        return visitRepository.findLastVisitByMembershipId(membershipId);
    }

    public void recordExit(UUID membershipId) {
        Visit lastVisit = visitRepository.findLastVisitByMembershipId(membershipId).orElse(null);
        if (lastVisit == null) return;

        lastVisit.setExitTime(LocalDateTime.now());
        visitRepository.save(lastVisit);
    }

    private void closeOldVisit(Visit visit) {
        if (visit == null || visit.getExitTime() == null) return;

        visit.setEntryTime(LocalDateTime.now());
        visitRepository.save(visit);
    }

}