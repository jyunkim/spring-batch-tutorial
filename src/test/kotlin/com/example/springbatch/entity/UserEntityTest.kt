package com.example.springbatch.entity

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserEntityTest {

    @Autowired
    lateinit var em: EntityManager

    @Test
    @Commit
    fun insertUser() {
        for (i in 1..10) {
            em.persist(User())
            Thread.sleep(10000)
        }
    }
}