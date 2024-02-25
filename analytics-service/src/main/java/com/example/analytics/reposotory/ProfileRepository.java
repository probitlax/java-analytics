package com.example.analytics.reposotory;

import com.example.analytics.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Override
    <S extends Profile> S save(S profile);

    @Override
    Optional<Profile> findById(Integer id);

}
