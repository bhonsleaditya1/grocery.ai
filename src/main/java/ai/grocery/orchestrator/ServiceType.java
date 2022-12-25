package ai.grocery.orchestrator;

import lombok.Getter;

public enum ServiceType {
    KYC("KYC_EVENT","KYC_RESPONSE"),
    CHAT("CHAT_EVENT","CHAT_RESPONSE"),
    NOTIFICATION("NOTIFICATION_EVENT","NOTIFICATION_RESPONSE"),
    PAYMENTS("PAYMENT_EVENT","PAYMENT_RESPONSE"),
    REVIEW("REVIEW_EVENT","REVIEW_RESPONSE"),
    REWARDS("REWARDS_EVENT","REWARDS_RESPONSE"),
    DELIVERY("DELIVERY_EVENT","DELIVERY_RESPONSE");

    @Getter
    private final String event;
    @Getter
    private final String response;

    ServiceType(String event, String response) {
        this.event =event;
        this.response = response;
    }
}
