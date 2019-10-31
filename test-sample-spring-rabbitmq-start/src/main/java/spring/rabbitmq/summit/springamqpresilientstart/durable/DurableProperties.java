package spring.rabbitmq.summit.springamqpresilientstart.durable;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix= "durable",  ignoreUnknownFields = true)
public class DurableProperties {
    boolean enabled;
    String queueName = "durable-q";
	String exchangeName = "durable-e";
    String routingKey = queueName;
    boolean possibleAuthenticationFailureFatal = false;
    boolean missingQueuesFatal = false;

    public DurableProperties() {
    }
    
    public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	public boolean isPossibleAuthenticationFailureFatal() {
		return possibleAuthenticationFailureFatal;
	}
	public void setPossibleAuthenticationFailureFatal(boolean possibleAuthenticationFailureFatal) {
		this.possibleAuthenticationFailureFatal = possibleAuthenticationFailureFatal;
	}
	public boolean isMissingQueuesFatal() {
		return missingQueuesFatal;
	}
	public void setMissingQueuesFatal(boolean missingQueuesFatal) {
		this.missingQueuesFatal = missingQueuesFatal;
	}

}
