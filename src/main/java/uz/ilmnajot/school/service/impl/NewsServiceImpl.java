package uz.ilmnajot.school.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.school.entity.News;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.NewsRequest;
import uz.ilmnajot.school.model.response.NewsResponse;
import uz.ilmnajot.school.repository.NewsRepository;
import uz.ilmnajot.school.service.NewsService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    @Override
    public ApiResponse addNews(NewsRequest request) {
        Optional<News> optionalNews = newsRepository.findByTitle(request.getTitle());
        if (optionalNews.isPresent()) {
            throw new UserException("news already exist", HttpStatus.BAD_REQUEST);
        }
        News news = new News();
        return getApiResponse(request, news);
    }


    @Override
    public ApiResponse getNewsById(Long newsId) {
        News news = getNews(newsId);
//        NewsResponse response = new NewsResponse();
        NewsResponse newsResponse = NewsResponse.newsToDto(news);
        return new ApiResponse("success", true, newsResponse);
    }


    @Override
    public ApiResponse getAllNews() {
        List<News> news = newsRepository.findAll();
        List<NewsResponse> list =
                news
                        .stream()
                        .map(NewsResponse::newsToDto)
                        .toList();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("News response", list);
        responseMap.put("current page", 1);
        responseMap.put("totalItems", responseMap.size());
        return new ApiResponse("Users Found", true, responseMap);
    }


    @Override
    public ApiResponse deleteNews(Long newsId) {
        News news = getNews(newsId);
        if (news != null) {
            newsRepository.deleteById(newsId);
            return new ApiResponse("success", true, "article with id " + newsId + "  has been removed ");
        }
        throw new UserException("article has not been found");
    }


    @Override
    public ApiResponse updateNews(NewsRequest request, Long newsId) {
        News news = getNews(newsId);
        if (news != null) {
            News news1 = new News();
            news1.setId(newsId);
            return getApiResponse(request, news1);

        }
        throw new UserException("user not found", HttpStatus.NOT_FOUND);
    }


    private ApiResponse getApiResponse(NewsRequest request, News news) {
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        MultipartFile images = request.getImages();
        if (images != null) {
            try {
                news.setImages(request.getImages().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return new ApiResponse("error", false, e.getMessage());
            }
        } else {
            news.setImages(null);
        }
        news.setPublishedDate(request.getPublishedDate());
        news.setAuthor(request.getAuthor());
        News savedNews = newsRepository.save(news);
        NewsResponse response = NewsResponse.newsToDto(savedNews);
        return new ApiResponse("success", true, response);
    }

    public News getNews(Long newsId) {
        Optional<News> byId = newsRepository.findById(newsId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new UserException("news not found", HttpStatus.BAD_REQUEST);
    }
}
