package org.dev.paymentnotificationhubs.infraestructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class NotificationQueueProps {
    private List<Queue> notificationQueues;

    public static class Queue {
        private String name;
        private String type;
        private boolean enabled;

        public void setName(String name) {
            this.name = name;
        }
        public void setType(String type) {
            this.type = type;
        }
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    public List<Queue> getNotificationQueues() {
        return notificationQueues;
    }
    public void setNotificationQueues(List<Queue> notificationQueues) {
        this.notificationQueues = notificationQueues;
    }
}
