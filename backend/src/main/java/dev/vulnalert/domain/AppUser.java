package dev.vulnalert.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(name="external_id", nullable=false, unique=true) public String externalId;
    @Column(nullable=false, unique=true) public String email;
    @Column(name="display_name", nullable=false) public String displayName;
    @Column(name="created_at", nullable=false) public Instant createdAt = Instant.now();
    protected AppUser() {}
    public AppUser(String externalId, String email, String displayName) { this.externalId=externalId; this.email=email; this.displayName=displayName; }
}

