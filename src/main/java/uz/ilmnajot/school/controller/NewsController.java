package uz.ilmnajot.school.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.NewsRequest;
import uz.ilmnajot.school.service.NewsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@SecurityRequirement(name = "Bearer")
public class NewsController {

    private final NewsService newsService;
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"));

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PreAuthorize("hasAuthority('ADD_NEWS')") //it does not work...
    @PostMapping(value = "/addNews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public HttpEntity<ApiResponse> addNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) MultipartFile images,
            @RequestParam(value = "publishedDate", required = false) String publishedDate,
            @RequestParam("author") String author) {

        logger.debug("Title: {}", title);
        logger.debug("Content: {}", content);
        logger.debug("Images: {}", images != null ? images.getOriginalFilename() : "null");
        logger.debug("Published Date: {}", publishedDate);
        logger.debug("Author: {}", author);

        if (publishedDate == null || publishedDate.trim().isEmpty()) {
            logger.error("Published date is empty or null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("false", false, "Published date is required"));
        }
        LocalDateTime parsedDate = null;
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                parsedDate = LocalDateTime.parse(publishedDate, formatter);
                break;
            } catch (DateTimeParseException e) {
                // Continue to the next formatter
            }
        }
        if (parsedDate == null) {
            logger.error("Error parsing published date: {}", publishedDate);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("false",false, "Invalid date format"));
        }
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle(title);
        newsRequest.setContent(content);
        newsRequest.setImages(images);
        newsRequest.setPublishedDate(LocalDateTime.parse(publishedDate));
        newsRequest.setAuthor(author);
        ApiResponse apiResponse = newsService.addNews(newsRequest);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('GET_NEWS')") //it works...
    @GetMapping("/getNews/{newsId}")
    public HttpEntity<ApiResponse> getNews(@PathVariable(name = "newsId") Long newsId) {
        ApiResponse apiResponse = newsService.getNewsById(newsId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_NEWS')") //it works...
    @GetMapping("/allNews")
    public HttpEntity<ApiResponse> getAllNews() {
        ApiResponse allNews = newsService.getAllNews();
        return allNews != null
                ? ResponseEntity.status(HttpStatus.OK).body(allNews)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('DELETE_NEWS')") //it works...
    @DeleteMapping("/deleteNews/{newsId}")
    public HttpEntity<ApiResponse> removeArticle(@PathVariable(name = "newsId") Long newsId) {
        ApiResponse apiResponse = newsService.deleteNews(newsId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('UPDATE_NEWS')") //it works...
    @PutMapping("/updateNews/{newsId}")
    public HttpEntity<ApiResponse> updateNews(
            @RequestBody NewsRequest request,
            @PathVariable(name = "newsId") Long newsId) {
        ApiResponse apiResponse = newsService.updateNews(request, newsId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
