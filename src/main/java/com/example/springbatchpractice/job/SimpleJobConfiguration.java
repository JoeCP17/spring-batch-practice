package com.example.springbatchpractice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob") // simpleJob 이란 Batch Job 생성
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")// simpleStep1 이란 Batch Step을 생성
                // Step안에서 수행될 기능들을 명시
                .tasklet((contribution, chunkContext) -> { // Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할때 사용한다.
                    log.info(">>>>>>> This is Step1"); // 수행시 log 출력
                    log.info(">>>>> requestDate = {}", requestDate);
//                    throw new IllegalArgumentException("step1에서 실패합니다.");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep2")// simpleStep2이란 Batch Step을 생성
                // Step안에서 수행될 기능들을 명시
                .tasklet((contribution, chunkContext) -> { // Tasklet은 Step안에서 단일로 수행될 커스텀한 기능들을 선언할때 사용한다.
                    log.info(">>>>>>> This is Step2"); // 수행시 log 출력
                    log.info(">>>>> requestDate = {}", requestDate);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
