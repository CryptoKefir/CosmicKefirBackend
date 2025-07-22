package com.evolvdefi.edefi.repository

import com.evolvdefi.edefi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
  
}