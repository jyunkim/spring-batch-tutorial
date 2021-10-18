package com.example.springbatch.writer

import com.example.springbatch.entity.User
import com.example.springbatch.repository.UserRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class InactiveUserWriter(
    private val userRepository: UserRepository
) : ItemWriter<User> {

    // chunk size 만큼 데이터를 받고 bulk insert
    override fun write(users: MutableList<out User>) {
        userRepository.saveAll(users)
    }
}