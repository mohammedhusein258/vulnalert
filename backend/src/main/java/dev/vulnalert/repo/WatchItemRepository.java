package dev.vulnalert.repo;
import dev.vulnalert.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface WatchItemRepository extends JpaRepository<WatchItem,Long>{List<WatchItem> findByUserOrderByCreatedAtDesc(AppUser user); long countByUser(AppUser user);}
