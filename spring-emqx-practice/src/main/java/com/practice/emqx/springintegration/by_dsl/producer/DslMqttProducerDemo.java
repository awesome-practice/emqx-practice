package com.practice.emqx.springintegration.by_dsl.producer;

import com.practice.emqx.springintegration.SprMqttConstants;
import com.practice.emqx.springintegration.by_usual.PahoUtil;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

/**
 * <p>see: https://github.com/spring-projects/spring-integration-samples/blob/master/dsl/cafe-dsl/src/main/java/org/springframework/integration/samples/dsl/cafe/lambda/Application.java</p>
 *
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
@SpringBootApplication
public class DslMqttProducerDemo {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(DslMqttProducerDemo.class).web(WebApplicationType.NONE)
                .run(args);
        Sender sender = ctx.getBean(Sender.class);
        int i = 0;
        while (true) {
            String message = "hello" + (i++);
            sender.send(message);
            System.out.println("sent " + message);
            Thread.sleep(1000);
        }

    }

    @Bean
    public IntegrationFlow mqttOutbound() {
        return flow -> flow.handle(messageHandler());

    }

    private MessageHandler messageHandler() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(SprMqttConstants.PUBLISHER_ID, PahoUtil.mqttClientFactory());

        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(SprMqttConstants.TOPIC);
        return messageHandler;
    }

    @MessagingGateway
    public interface Sender {
        @Gateway(requestChannel = "mqttOutbound.input")
        void send(String message);
    }


}
