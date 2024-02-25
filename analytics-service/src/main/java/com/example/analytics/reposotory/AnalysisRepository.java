package com.example.analytics.reposotory;

import com.example.analytics.entity.Analysis;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {

    @Override
    <S extends Analysis> S save(S analysis);

    List<Analysis> findByOwner_IdOrderByIdDesc(Integer profileId);

    @Query("SELECT a FROM Analysis a INNER JOIN a.viewers c WHERE c.id = (:profileId) ORDER BY a.id DESC")
    List<Analysis> findByViewerOrderByIdDesc(@Param("profileId")Integer profileId);
}
