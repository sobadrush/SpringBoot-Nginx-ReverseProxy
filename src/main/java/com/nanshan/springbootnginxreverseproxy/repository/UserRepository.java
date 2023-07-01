package com.nanshan.springbootnginxreverseproxy.repository;

import com.nanshan.springbootnginxreverseproxy.model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserVO, String> {

    Optional<UserVO> findByEmail(String email);

}
