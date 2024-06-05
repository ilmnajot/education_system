package uz.ilmnajot.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News extends BaseEntity {

    private String title;

    @Lob
    private String content; //for storing large text content

    @Lob
    @Column(name = "images", columnDefinition = "BLOB")
    private byte[] images;      // for storing binary data as BLOB

    private LocalDateTime publishedDate;

    private String author;


}
