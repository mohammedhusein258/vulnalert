package dev.vulnalert.repo;
import dev.vulnalert.domain.AppUser; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByExternalId(String id);}
