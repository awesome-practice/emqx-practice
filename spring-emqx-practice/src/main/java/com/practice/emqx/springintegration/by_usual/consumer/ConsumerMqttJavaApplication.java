package com.practice.emqx.springintegration.by_usual.consumer;

import com.practice.emqx.springintegration.SprMqttConstants;
import com.practice.emqx.springintegration.by_usual.PahoUtil;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
public class ConsumerMqttJavaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerMqttJavaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public MessageProducer channelAdapter() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(SprMqttConstants.SUBSCRIBER_ID
                        , PahoUtil.mqttClientFactory(),
                        SprMqttConstants.TOPIC);

        adapter.setCompletionTimeout(4000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(messageChannel());
        adapter.setRecoveryInterval(2000);
        return adapter;
    }


    @Bean
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public MessageHandler messageHandler() {
        return message -> System.out.println(message.getPayload());
    }

}