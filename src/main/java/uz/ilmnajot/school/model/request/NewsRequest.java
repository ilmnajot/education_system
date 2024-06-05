package uz.ilmnajot.school.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.school.entity.News;

import java.time.LocalDateTime;
@Data
public class NewsRequest {

    private String title;

    private String content; //for storing large text content

    private MultipartFile images;      // for storing binary data as BLOB

    private LocalDateTime publishedDate;

    private String author;

}
