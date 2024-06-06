package uz.ilmnajot.school.model.response;

import lombok.Data;
import uz.ilmnajot.school.entity.News;

import java.time.LocalDateTime;

@Data
public class NewsResponse {

    private Long id;

    private String title;

    private String content; //for storing large text content

    private byte[] images;      // for storing binary data as BLOB

    private LocalDateTime publishedDate;

    private String author;

    public static NewsResponse newsToDto(News news){
        NewsResponse dto = new NewsResponse();
        dto.setId(news.getId());
        dto.title = news.getTitle();
        dto.content = news.getContent();
        dto.images = news.getImages();
        dto.publishedDate = news.getPublishedDate();
        dto.author = news.getAuthor();
        return dto;
    }
}
