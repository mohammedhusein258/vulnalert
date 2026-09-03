package dev.vulnalert.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="notification_preference")
public class NotificationPreference {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @JsonIgnore @OneToOne(optional=false) @JoinColumn(name="user_id", unique=true) public AppUser user;
    @Enumerated(EnumType.STRING) @Column(name="minimum_severity", nullable=false) public Severity minimumSeverity = Severity.HIGH;
    @Column(name="email_enabled", nullable=false) public boolean emailEnabled = true;
    @Column(name="in_app_enabled", nullable=false) public boolean inAppEnabled = true;
    @Column(name="email_verified", nullable=false) public boolean emailVerified;
    protected NotificationPreference() {}
    public NotificationPreference(AppUser user) { this.user=user; }
}
