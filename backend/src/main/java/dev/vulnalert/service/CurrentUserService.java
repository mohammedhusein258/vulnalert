package dev.vulnalert.service;
import dev.vulnalert.domain.*; import dev.vulnalert.repo.*; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service
public class CurrentUserService {
 private final UserRepository users; private final PreferenceRepository preferences;
 public CurrentUserService(UserRepository u,PreferenceRepository p){users=u;preferences=p;}
 @Transactional public AppUser resolve(Authentication auth){String id=auth.getName();return users.findByExternalId(id).orElseGet(()->{var u=users.save(new AppUser(id,id,id.split("@")[0]));preferences.save(new NotificationPreference(u));return u;});}
 public NotificationPreference preferences(AppUser user){return preferences.findByUser(user).orElseThrow();}
}
