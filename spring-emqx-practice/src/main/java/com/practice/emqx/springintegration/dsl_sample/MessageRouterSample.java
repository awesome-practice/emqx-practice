package com.practice.emqx.springintegration.dsl_sample;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

/**
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
public class MessageRouterSample {

    @Bean
    public IntegrationFlow routeFlow() {
        return IntegrationFlows.from("routerInput")
                .<Integer, Boolean>route(p -> p % 2 == 0,
                        m -> m.suffix("Channel")
                                .channelMapping(true, "even")
                                .channelMapping(false, "odd")
                )
                .get();

    }

    @Bean
    public IntegrationFlow routeFlow2() {
        return IntegrationFlows.from("routerInput")
                .route("headers['destChannel']")
                .get();
    }

}
