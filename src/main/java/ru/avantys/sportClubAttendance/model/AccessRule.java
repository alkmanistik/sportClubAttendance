package ru.avantys.sportClubAttendance.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "access_rule")
public class AccessRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", nullable = false)
    private Membership membership;

    @Column(name = "zone", nullable = false)
    private String zone;

    @Column(name = "valid_from_time", nullable = false)
    private LocalTime validFromTime;

    @Column(name = "valid_to_time", nullable = false)
    private LocalTime validToTime;

    @Column(name = "allowed_days", nullable = false)
    private String allowedDays;

    @Column(name = "priority")
    private Integer priority = 0;

    public AccessRule() {}

    public AccessRule(Membership membership, String zone, LocalTime validFromTime,
                      LocalTime validToTime, String allowedDays, Integer priority) {
        this.membership = membership;
        this.zone = zone;
        this.validFromTime = validFromTime;
        this.validToTime = validToTime;
        this.allowedDays = allowedDays;
        this.priority = priority;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Membership getMembership() { return membership; }
    public void setMembership(Membership membership) { this.membership = membership; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public LocalTime getValidFromTime() { return validFromTime; }
    public void setValidFromTime(LocalTime validFromTime) { this.validFromTime = validFromTime; }

    public LocalTime getValidToTime() { return validToTime; }
    public void setValidToTime(LocalTime validToTime) { this.validToTime = validToTime; }

    public String getAllowedDays() { return allowedDays; }
    public void setAllowedDays(String allowedDays) { this.allowedDays = allowedDays; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
}
