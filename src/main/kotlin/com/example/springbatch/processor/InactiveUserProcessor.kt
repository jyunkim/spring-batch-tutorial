package com.example.springbatch.processor

import com.example.springbatch.entity.User
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class InactiveUserProcessor : ItemProcessor<User, User> {

    override fun process(user: User): User? {
        user.setInactive()
        return user
    }
}