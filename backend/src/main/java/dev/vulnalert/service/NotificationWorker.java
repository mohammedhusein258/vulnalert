package dev.vulnalert.service;
import dev.vulnalert.repo.AlertRepository; import io.awspring.cloud.sqs.annotation.SqsListener; import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.time.Instant;
@Service @ConditionalOnExpression("!'${app.aws.queue-url:}'.isBlank()")
public class NotificationWorker {
 private final AlertRepository alerts; public NotificationWorker(AlertRepository alerts){this.alerts=alerts;}
 @SqsListener("${app.aws.queue-url}") @Transactional public void deliver(String id){var alert=alerts.findById(Long.valueOf(id)).orElseThrow();alert.status="DELIVERED";alert.deliveredAt=Instant.now();alerts.save(alert);}
}
