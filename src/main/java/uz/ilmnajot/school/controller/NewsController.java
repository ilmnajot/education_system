package uz.ilmnajot.school.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.NewsRequest;
import uz.ilmnajot.school.service.NewsService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PreAuthorize("hasAuthority('ADD_NEWS')")
    @PostMapping("/addArticle")
    public HttpEntity<ApiResponse> addNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("images") MultipartFile images,
            @RequestParam("publishedDate") String publishedDate,
            @RequestParam("author") String author) {
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

    @PreAuthorize("hasAuthority('GET_NEWS')")
    @GetMapping("/getNews/{newsId}")
    public HttpEntity<ApiResponse> getNews(@PathVariable(name = "newsId") Long newsId) {
        ApiResponse apiResponse = newsService.getNewsById(newsId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_NEWS')")
    @GetMapping("/allNews")
    public HttpEntity<ApiResponse> getAllNews() {
        ApiResponse allNews = newsService.getAllNews();
        return allNews != null
                ? ResponseEntity.status(HttpStatus.OK).body(allNews)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('DELETE_NEWS')")
    @DeleteMapping("/deleteNews/{newsId}")
    public HttpEntity<ApiResponse> removeArticle(@PathVariable(name = "newsId") Long newsId) {
        ApiResponse apiResponse = newsService.deleteNews(newsId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('UPDATE_NEWS')")
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
