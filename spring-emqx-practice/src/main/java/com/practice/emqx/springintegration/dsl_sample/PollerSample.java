package com.practice.emqx.springintegration.dsl_sample;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

/**
 * @author Luo Bao Ding
 * @since 2019/3/28
 */
public class PollerSample {
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500)
                .errorChannel("myErrors");
    }
}
