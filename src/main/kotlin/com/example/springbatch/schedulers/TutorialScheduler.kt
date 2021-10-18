package com.example.springbatch.schedulers

import com.example.springbatch.jobs.InactiveUserJobConfig
import com.example.springbatch.jobs.TutorialJobConfig
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecutionException
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TutorialScheduler(
    private val tutorialJobConfig: TutorialJobConfig,
    private val jobLauncher: JobLauncher
) {

    private val logger = LoggerFactory.getLogger(tutorialJobConfig::class.java)

    // 5초마다 실행
    @Scheduled(fixedDelay = 5 * 1000)
    fun runJob() {
        try {
            jobLauncher.run(
                tutorialJobConfig.tutorialJob(),
                JobParametersBuilder() // 반복해서 실행되는 job의 유일한 id(동일한 값이 세팅되면 step이 실행되지 않음)
                    .addString("datetime", LocalDateTime.now().toString())
                    .toJobParameters()
            )
        } catch (e: JobExecutionException) {
            logger.error(e.message)
        }
    }
}