package com.example.springbatch.jobs

import com.example.springbatch.tasklets.TutorialTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TutorialJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun tutorialJob(): Job {
        return jobBuilderFactory.get("tutorialJob")
            .start(tutorialStep())
            .build()
    }

    @Bean
    fun tutorialStep(): Step {
        return stepBuilderFactory.get("tutorialStep")
            .tasklet(TutorialTasklet())
            .build()
    }
}
