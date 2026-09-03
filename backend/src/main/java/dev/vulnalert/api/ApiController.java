package dev.vulnalert.api;
import dev.vulnalert.domain.*; import dev.vulnalert.repo.*; import dev.vulnalert.service.*; import jakarta.validation.Valid; import jakarta.validation.constraints.NotBlank; import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.time.*; import java.util.*;
@RestController @RequestMapping("/api")
public class ApiController {
 private final CurrentUserService current; private final WatchItemRepository watches; private final VulnerabilityRepository vulnerabilities; private final AlertRepository alerts; private final PreferenceRepository preferences; private final NvdIngestionService ingestion;
 public ApiController(CurrentUserService c,WatchItemRepository w,VulnerabilityRepository v,AlertRepository a,PreferenceRepository p,NvdIngestionService i){current=c;watches=w;vulnerabilities=v;alerts=a;preferences=p;ingestion=i;}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(Authentication auth){var user=current.resolve(auth);return Map.of("watchedProducts",watches.countByUser(user),"unreadAlerts",alerts.countByUserAndStatus(user,"UNREAD"),"newCvesThisWeek",vulnerabilities.countByPublishedAtAfter(Instant.now().minus(Duration.ofDays(7))),"alerts",alerts.findTop20ByUserOrderByCreatedAtDesc(user));}
 @GetMapping("/watchlist") public List<WatchItem> watchlist(Authentication auth){return watches.findByUserOrderByCreatedAtDesc(current.resolve(auth));}
 public record WatchRequest(@NotBlank String vendor,@NotBlank String product,String version,String cpePrefix){}
 @PostMapping("/watchlist") @ResponseStatus(HttpStatus.CREATED) public WatchItem add(@Valid @RequestBody WatchRequest r,Authentication auth){return watches.save(new WatchItem(current.resolve(auth),r.vendor(),r.product(),r.version(),r.cpePrefix()));}
 @DeleteMapping("/watchlist/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id,Authentication auth){var user=current.resolve(auth);var item=watches.findById(id).filter(w->w.user.id.equals(user.id)).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));watches.delete(item);}
 @GetMapping("/vulnerabilities") public List<Vulnerability> vulnerabilities(){return vulnerabilities.findTop50ByOrderByPublishedAtDesc();}
 @GetMapping("/preferences") public NotificationPreference preference(Authentication auth){return current.preferences(current.resolve(auth));}
 public record PreferenceRequest(Severity minimumSeverity,boolean emailEnabled,boolean inAppEnabled){}
 @PutMapping("/preferences") public NotificationPreference update(@RequestBody PreferenceRequest r,Authentication auth){var p=current.preferences(current.resolve(auth));p.minimumSeverity=r.minimumSeverity();p.emailEnabled=r.emailEnabled()&&p.emailVerified;p.inAppEnabled=r.inAppEnabled();return preferences.save(p);}
 @PostMapping("/admin/ingest") public Map<String,Integer> ingest(){return ingestion.ingest(Duration.ofHours(24));}
}
