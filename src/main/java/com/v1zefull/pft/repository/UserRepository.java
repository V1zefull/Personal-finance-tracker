package com.v1zefull.pft.repository;

import com.v1zefull.pft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
