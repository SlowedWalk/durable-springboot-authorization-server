package tech.hidetora.authserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.hidetora.authserver.entity.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findOneWithAuthoritiesByEmailIgnoreCase(@Param("email") String email);

    Optional<AppUser> findOneWithAuthoritiesByUsername(String username);
}
