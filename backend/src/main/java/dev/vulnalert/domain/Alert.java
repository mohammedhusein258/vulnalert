package dev.vulnalert.domain;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
@Entity @Table(name="alert")
public class Alert {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @JsonIgnore @ManyToOne(optional=false) @JoinColumn(name="user_id") public AppUser user;
 @ManyToOne(optional=false) @JoinColumn(name="vulnerability_id") public Vulnerability vulnerability;
 @ManyToOne(optional=false) @JoinColumn(name="watch_item_id") public WatchItem watchItem;
 @Column(nullable=false) public String status="UNREAD";
 @Column(name="created_at",nullable=false) public Instant createdAt=Instant.now();
 @Column(name="delivered_at") public Instant deliveredAt;
 protected Alert(){} public Alert(AppUser u,Vulnerability v,WatchItem w){user=u;vulnerability=v;watchItem=w;}
}
