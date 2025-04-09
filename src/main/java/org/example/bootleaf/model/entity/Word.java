package org.example.bootleaf.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    //3글자 안넘기
    @Column(nullable = false,length = 3)
    private String text;
    //시간대가 나타나면좋겠따.
    @CreationTimestamp
    private LocalDateTime createdAt;
}
