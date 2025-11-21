package ru.avantys.sportClubAttendance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.avantys.sportClubAttendance.service.VisitService;

import java.util.UUID;

@RestController
@RequestMapping("/api/turnstile")
public class TurnstileController {
    private final VisitService visitService;

    public TurnstileController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/{zoneId}/{membershipId}/entry")
    public ResponseEntity<String> recordEntry(@PathVariable UUID membershipId, @PathVariable String zoneId) {
        try {
            visitService.createVisit(membershipId, zoneId);
            return ResponseEntity.ok("Entry recorded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{membershipId}/exit")
    public ResponseEntity<String> recordExit(@PathVariable UUID membershipId) {
        try {
            visitService.recordExit(membershipId);
            return ResponseEntity.ok("Exit recorded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}