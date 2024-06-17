package uz.ilmnajot.school.service;

import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.CourseRequest;

public interface CourseService {

    ApiResponse addCourse(CourseRequest request);

    ApiResponse updateCourse(CourseRequest request, Long courseId);

    ApiResponse deleteCourse(Long courseId);

    ApiResponse getCourse(Long courseId);

    ApiResponse getAllCourses();

    ApiResponse getCourseByUserId(Long userId);

    ApiResponse getStudentByCourseId(Long courseId);

    ApiResponse addUserToCourse(Long userId, Long courseId);

    ApiResponse removeUserFromCourse(Long userId, Long courseId);
}
