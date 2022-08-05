package com.spinel.framework.repositories;



import com.spinel.framework.models.GlobalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<GlobalAccessToken, Long> {
}
