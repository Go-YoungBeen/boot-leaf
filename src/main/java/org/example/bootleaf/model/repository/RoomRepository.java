package org.example.bootleaf.model.repository;

import org.example.bootleaf.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByTitle(String title);  // 방 중복 방지용
}
