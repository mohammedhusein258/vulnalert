package dev.vulnalert.service;
import io.awspring.cloud.sqs.operations.SqsTemplate; import org.springframework.beans.factory.ObjectProvider; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
@Service
public class AlertPublisher {
 private final SqsTemplate sqs; private final String queueUrl;
 public AlertPublisher(ObjectProvider<SqsTemplate> provider,@Value("${app.aws.queue-url:}")String queueUrl){this.sqs=provider.getIfAvailable();this.queueUrl=queueUrl;}
 public void publish(Long alertId){if(sqs!=null&&!queueUrl.isBlank())sqs.send(queueUrl,String.valueOf(alertId));}
}
