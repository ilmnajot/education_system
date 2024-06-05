package uz.ilmnajot.school.service;

import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.NewsRequest;

public interface NewsService {
    ApiResponse addNews(NewsRequest request);

    ApiResponse getNewsById(Long newsId);

    ApiResponse getAllNews();

    ApiResponse deleteNews(Long newsId);

    ApiResponse updateNews(NewsRequest request, Long newsId);
}
