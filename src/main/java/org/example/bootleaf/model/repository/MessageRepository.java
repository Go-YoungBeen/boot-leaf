package org.example.bootleaf.model.repository;

import org.example.bootleaf.model.entity.Message;
import org.example.bootleaf.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomOrderByCreatedAtDesc(Room room);
}
