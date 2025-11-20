package ru.avantys.sportClubAttendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Column(name = "entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(name = "zone", nullable = false)
    private String zone;

    public Visit() {}

    public Visit(Membership membership, LocalDateTime entryTime, String zone) {
        this.membership = membership;
        this.entryTime = entryTime;
        this.zone = zone;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
}