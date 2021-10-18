package com.example.springbatch.schedulers

import com.example.springbatch.jobs.InactiveUserJobConfig
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecutionException
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class InactiveUserScheduler(
    private val jobLauncher: JobLauncher,
    private val inactiveUserJobConfig: InactiveUserJobConfig
) {

    private val logger = LoggerFactory.getLogger(InactiveUserJobConfig::class.java)

    // Crontab 사용 가능
//    @Scheduled("0 10 * * * *")  // 매 시간 10분에 실행
//    @Scheduled("0 0 8 10 * *")  // 매월 10일 오전 8시 실행
//    @Scheduled("0 0/10 * * *")  // 10분마다 실행
    @Scheduled(fixedDelay = 5 * 1000)
    fun runJob() {
        // JobParameter를 설정해주지 않으면 해당 step은 한번만 실행되고 다시 실행되지 않음
        // Job 실행 단위는 JobParameter와 1:1 매칭
        // 배치 애플리케이션이 여러번 실행될 수 있게 고유한 JobParameter 부여
        val jobConfig = hashMapOf<String, JobParameter>()
        jobConfig["time"] = JobParameter(LocalDateTime.now().toString())
        val jobParameters = JobParameters(jobConfig)

        try {
            jobLauncher.run(inactiveUserJobConfig.inactiveUserJob(), jobParameters)
        } catch (e: JobExecutionException) {
            logger.error(e.message)
        }
    }
}