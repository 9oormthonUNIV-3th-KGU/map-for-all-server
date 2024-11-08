package api.goorm.map.application.search.dao;

import api.goorm.map.application.search.entity.Location;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Location> findByNameAndLatitudeAndLongitude(String location, double latitude, double longitude);
    // Haversine 공식 사용
    // 1km 이내 장소
    @Query("SELECT l FROM Location l WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(l.latitude)) * cos(radians(l.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(l.latitude)))) < 1")
    List<Location> findLocationsWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
