package com.example.springbatch.reader

import com.example.springbatch.entity.User
import com.example.springbatch.entity.UserStatus
import com.example.springbatch.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@StepScope // 각 step 마다 새로운 빈 생성
class InactiveUserReader(
    private val userRepository: UserRepository
) : ItemReader<User> {

    private val logger = LoggerFactory.getLogger(InactiveUserReader::class.java)
    private val queue: MutableList<User>

    init {
        val oldUsers = userRepository.findByUpdatedAtBeforeAndUserStatusEquals(
            LocalDateTime.now().minusMinutes(1),
            UserStatus.ACTIVE
        )
        queue = ArrayDeque(oldUsers)
    }

    override fun read(): User? {
        logger.debug("InactiveUserReader executed")
        return queue.removeFirstOrNull()
    }
}