package com.example.springbatch.jobs

import com.example.springbatch.entity.User
import com.example.springbatch.entity.UserStatus
import com.example.springbatch.processor.InactiveUserProcessor
import com.example.springbatch.reader.InactiveUserReader
import com.example.springbatch.writer.InactiveUserWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import javax.persistence.EntityManagerFactory

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

    // 청크 지향 프로세싱: 트랜잭션 내에서 청크 단위로 데이터 처리 -> 중간에 배치 처리가 실패해도 다른 청크는 영향을 받지 않음
    @Bean
    fun inactiveUserStep(): Step {
        return stepBuilderFactory.get("inactiveUserStep")
            .chunk<User, User>(10) // 쓰기 시에 청크 단위로 writer 메서드를 실행시킬 단위를 지정 -> 커밋단위가 10 record
            .reader(inactiveUserReader)
            .processor(inactiveUserProcessor)
            .writer(inactiveUserWriter)
            .build()
    }

    // 데이터를 한번에 가져와서 메모리에 올려놓는 것은 좋지 않음 -> PagingItemReader
//    @Bean
//    @StepScope
//    fun inactiveUserJpaReader(): JpaPagingItemReader<User> {
//        val map = hashMapOf<String, Any>()
//        map["createdAt"] = LocalDateTime.now().minusMinutes(1)
//        map["status"] = UserStatus.ACTIVE
//
//        return JpaPagingItemReaderBuilder<User>()
//            // JpaPagingItemReader를 사용하면 쿼리를 직접 짜야됨
//            .queryString("select u from User as u where u.createdAt < :createdAt and u.status = :status")
//            .parameterValues(map)
//            .entityManagerFactory(entityManagerFactory)
//            .pageSize(10)
//            .build()
//    }
//
//    @Bean
//    fun inactiveUserJpaWriter(): JpaItemWriter<User> {
//        return JpaItemWriterBuilder<User>()
//            .entityManagerFactory(entityManagerFactory)
//            .build()
//    }
}