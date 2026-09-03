package dev.vulnalert.service;
import dev.vulnalert.domain.*; import dev.vulnalert.repo.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.Locale;
@Service
public class MatchingService {
 private final WatchItemRepository watches; private final PreferenceRepository prefs; private final AlertRepository alerts; private final AlertPublisher publisher;
 public MatchingService(WatchItemRepository w,PreferenceRepository p,AlertRepository a,AlertPublisher pub){watches=w;prefs=p;alerts=a;publisher=pub;}
 @Transactional public int match(Vulnerability vulnerability){int count=0;String affected=vulnerability.affectedProducts.toLowerCase(Locale.ROOT);for(var watch:watches.findAll()){var pref=prefs.findByUser(watch.user).orElse(null);if(pref==null||!vulnerability.severity.meets(pref.minimumSeverity)||!affected.contains(watch.product.toLowerCase(Locale.ROOT)))continue;if(alerts.findByUserAndVulnerabilityAndWatchItem(watch.user,vulnerability,watch).isPresent())continue;var alert=alerts.save(new Alert(watch.user,vulnerability,watch));publisher.publish(alert.id);count++;}return count;}
}
