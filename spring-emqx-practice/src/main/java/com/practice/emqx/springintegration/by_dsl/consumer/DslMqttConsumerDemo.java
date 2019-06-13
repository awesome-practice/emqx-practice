package com.practice.emqx.springintegration.by_dsl.consumer;

import com.practice.emqx.springintegration.SprMqttConstants;
import com.practice.emqx.springintegration.by_usual.PahoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageHandler;

/**
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
@SpringBootApplication
public class DslMqttConsumerDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DslMqttConsumerDemo.class)
                .web(WebApplicationType.NONE).run(args);
    }

    @Bean
    @Qualifier("mqttPahoMessageDrivenChannelAdapter")
    public MqttPahoMessageDrivenChannelAdapter channelAdapter() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(SprMqttConstants.SUBSCRIBER_ID
                        , PahoUtil.mqttClientFactory(),
                        SprMqttConstants.TOPIC);

        adapter.setCompletionTimeout(4000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(new DirectChannel());
        adapter.setRecoveryInterval(2000);
        return adapter;
    }

    @Bean
    public MessageHandler messageHandler() {
        return message -> System.out.println(message.getPayload());
    }

    @Bean
    public IntegrationFlow mqttInbound() {
        return IntegrationFlows.from(channelAdapter())
                .handle(messageHandler())
                .get();

    }
}
