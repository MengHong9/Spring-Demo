package org.example.damo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1); // Min threads
        executor.setMaxPoolSize(2); // Max threads
        executor.setQueueCapacity(1); // Queue size
        executor.setThreadNamePrefix("notification-");
        executor.initialize();

        return executor;
    }
}