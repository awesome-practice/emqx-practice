package com.practice.emqx.springintegration.dsl_sample;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
public class MessageChannelSample {
    private Executor taskExecutor = Executors.newCachedThreadPool();


    @Bean
    public MessageChannel queueChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public IntegrationFlow channelFlow() {
        return IntegrationFlows.from("input")
                .fixedSubscriberChannel()
                .channel("queueChannel")
                .channel(publishSubscribe())
                .channel(MessageChannels.executor("executorChannel", this.taskExecutor))
                .channel("output")
                .get();
    }

    @Bean
    public MessageChannel publishSubscribe() {
        return MessageChannels.publishSubscribe().get();
    }
}
