package api.goorm.map.application.search.dao;

import api.goorm.map.application.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long> {
}
