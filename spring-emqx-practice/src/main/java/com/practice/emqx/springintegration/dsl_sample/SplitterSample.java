package com.practice.emqx.springintegration.dsl_sample;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
public class SplitterSample {

    @Bean
    public IntegrationFlow splitFlow() {
        return IntegrationFlows.from("splitInput")
                .split(s ->
                        s.applySequence(false).get().getT2().setDelimiters(","))
                .channel(MessageChannels.executor(this.taskExecutor()))
                .get();
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newCachedThreadPool();
    }


}
