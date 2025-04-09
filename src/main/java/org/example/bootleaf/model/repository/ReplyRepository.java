package org.example.bootleaf.model.repository;

import org.example.bootleaf.model.entity.Message;
import org.example.bootleaf.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByMessageOrderByCreatedAtAsc(Message message);
}
