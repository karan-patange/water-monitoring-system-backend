package com.watermeter.usage.repository;

import com.watermeter.usage.entity.Login;
import com.watermeter.usage.entity.WaterUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRespository extends JpaRepository<Login, Long> {

    Optional<Login> findByUserNameAndPassWordAndRole(String userName, String passWord, String role);

    Optional<Login> findByUserName(String userName);
}
