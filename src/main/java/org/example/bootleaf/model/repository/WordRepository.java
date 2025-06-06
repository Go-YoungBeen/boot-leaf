package org.example.bootleaf.model.repository;

import org.example.bootleaf.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WordRepository extends JpaRepository<Word, String> {

}
