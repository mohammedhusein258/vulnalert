package dev.vulnalert.repo;
import dev.vulnalert.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AlertRepository extends JpaRepository<Alert,Long>{List<Alert> findTop20ByUserOrderByCreatedAtDesc(AppUser user); long countByUserAndStatus(AppUser user,String status); Optional<Alert> findByUserAndVulnerabilityAndWatchItem(AppUser user,Vulnerability vulnerability,WatchItem item);}
