package com.bangsil.bangsil.user.infrastructure;

import com.bangsil.bangsil.user.domain.UnAuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnAuthorizedUserRepository extends JpaRepository<UnAuthorizedUser, Long> {

    UnAuthorizedUser findByEmail(String email);

    boolean existsByEmail(String email);
}
