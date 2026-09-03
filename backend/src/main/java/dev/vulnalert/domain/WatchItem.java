package dev.vulnalert.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;

@Entity
@Table(name="watch_item")
public class WatchItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @JsonIgnore @ManyToOne(optional=false) @JoinColumn(name="user_id") public AppUser user;
    @Column(nullable=false) public String vendor;
    @Column(nullable=false) public String product;
    public String version;
    @Column(name="cpe_prefix") public String cpePrefix;
    @Column(name="created_at", nullable=false) public Instant createdAt=Instant.now();
    protected WatchItem() {}
    public WatchItem(AppUser user,String vendor,String product,String version,String cpePrefix){this.user=user;this.vendor=vendor;this.product=product;this.version=version;this.cpePrefix=cpePrefix;}
}
