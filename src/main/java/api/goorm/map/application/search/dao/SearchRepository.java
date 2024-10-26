package api.goorm.map.application.search.dao;

import api.goorm.map.application.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    List<Search> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);

    Long deleteByUserId(Long userId);
    Long deleteByUserIdAndLocationId(Long userId, Long locationId);
}
