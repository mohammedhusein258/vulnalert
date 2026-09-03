package dev.vulnalert.repo;
import dev.vulnalert.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface PreferenceRepository extends JpaRepository<NotificationPreference,Long>{Optional<NotificationPreference> findByUser(AppUser user);}
