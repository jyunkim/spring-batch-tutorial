package com.example.springbatch.repository

import com.example.springbatch.entity.User
import com.example.springbatch.entity.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface UserRepository : JpaRepository<User, Long> {

    fun findByUpdatedAtBeforeAndUserStatusEquals(updatedAt: LocalDateTime, userStatus: UserStatus): List<User>
}