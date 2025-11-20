package ru.avantys.sportClubAttendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MembershipType type;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "remaining_visits")
    private Integer remainingVisits;

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccessRule> accessRules;

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visits;

    public Membership() {}

    public Membership(Client client, MembershipType type, LocalDateTime startDate,
                      LocalDateTime endDate, Integer remainingVisits) {
        this.client = client;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingVisits = remainingVisits;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public MembershipType getType() { return type; }
    public void setType(MembershipType type) { this.type = type; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Integer getRemainingVisits() { return remainingVisits; }
    public void setRemainingVisits(Integer remainingVisits) { this.remainingVisits = remainingVisits; }

    public List<AccessRule> getAccessRules() { return accessRules; }
    public void setAccessRules(List<AccessRule> accessRules) { this.accessRules = accessRules; }

    public List<Visit> getVisits() { return visits; }
    public void setVisits(List<Visit> visits) { this.visits = visits; }
}