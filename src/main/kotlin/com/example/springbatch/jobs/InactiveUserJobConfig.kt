package com.example.springbatch.jobs

import com.example.springbatch.entity.User
import com.example.springbatch.processor.InactiveUserProcessor
import com.example.springbatch.reader.InactiveUserReader
import com.example.springbatch.writer.InactiveUserWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 회원 정보가 업데이트된지 1분이 지난 회원을 휴면 처리
 */
@Configuration
class InactiveUserJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val inactiveUserReader: InactiveUserReader,
    private val inactiveUserProcessor: InactiveUserProcessor,
    private val inactiveUserWriter: InactiveUserWriter
) {

    @Bean
    fun inactiveUserJob(): Job {
        return jobBuilderFactory.get("inactiveUserJob")
            .start(inactiveUserStep())
            .build()
    }

    @Bean
    fun inactiveUserStep(): Step {
        return stepBuilderFactory.get("inactiveUserStep")
            .chunk<User, User>(10) // 쓰기 시에 청크 단위로 writer 메서드를 실행시킬 단위를 지정 -> 커밋단위가 10 record
            .reader(inactiveUserReader)
            .processor(inactiveUserProcessor)
            .writer(inactiveUserWriter)
            .build()
    }
}