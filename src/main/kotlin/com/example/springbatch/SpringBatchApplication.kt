package com.example.springbatch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling // 스케줄러 기능 활성화
@EnableBatchProcessing // 배치 기능 활성화
@EnableJpaAuditing
@SpringBootApplication
class SpringBatchApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchApplication>(*args)
}
