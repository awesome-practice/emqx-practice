package com.practice.emqx.springintegration.by_usual.producer;

import com.practice.emqx.springintegration.SprMqttConstants;
import com.practice.emqx.springintegration.by_usual.PahoUtil;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
@IntegrationComponentScan
public class ProducerMqttJavaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(ProducerMqttJavaApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);
        MyGateway gateway = context.getBean(MyGateway.class);
        gateway.sendToMqtt("foo");
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000);
                String msg = "foo" + i;
                gateway.sendToMqtt(msg);
                System.out.println("produce: " + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Bean
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public MessageHandler messageHandler() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(SprMqttConstants.PUBLISHER_ID, PahoUtil.mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(SprMqttConstants.TOPIC);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "messageChannel")
    public interface MyGateway {

        void sendToMqtt(String data);

    }

}