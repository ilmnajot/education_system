package uz.ilmnajot.school.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.Course;
import uz.ilmnajot.school.entity.User;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.CourseRequest;
import uz.ilmnajot.school.model.response.CourseResponse;
import uz.ilmnajot.school.model.response.UserResponse;
import uz.ilmnajot.school.model.response.UserResponseForCourse;
import uz.ilmnajot.school.repository.CourseRepository;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.service.CourseService;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Override
    public ApiResponse addCourse(CourseRequest request) {
        Optional<Course> optionalCourse = courseRepository.findByName(request.getName());
        if (optionalCourse.isPresent()) {
            throw new UserException("Course already exists", HttpStatus.CONFLICT);
        }
        Course course = new Course();

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setImageUrl(request.getImageUrl());
        course.setDurationInHours(request.getDurationInHours());
        Course savedCourse = courseRepository.save(course);
        CourseResponse dto = new CourseResponse().toDto(savedCourse);

        return new ApiResponse("successfully course has been added", true, dto);
    }

    @Override
    public ApiResponse updateCourse(CourseRequest request, Long courseId) {
        Course course = getCourseById(courseId);
        if (course.getName().equals(request.getName())) {
            throw new UserException("Course already exists", HttpStatus.CONFLICT);
        }
        course.setId(courseId);

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setImageUrl(request.getImageUrl());
        course.setDurationInHours(request.getDurationInHours());
        Course savedCourse = courseRepository.save(course);
        CourseResponse dto = new CourseResponse().toDto(savedCourse);

        return new ApiResponse("successfully course has been updated", true, dto);
    }

    private void savedCourse(CourseRequest request, Course course) {
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setImageUrl(request.getImageUrl());
        course.setDurationInHours(request.getDurationInHours());
        Course savedCourse = courseRepository.save(course);
        CourseResponse dto = new CourseResponse().toDto(savedCourse);
    }

    @Override
    public ApiResponse deleteCourse(Long courseId) {
        getCourseById(courseId);
        courseRepository.deleteById(courseId);
        return new ApiResponse("success", true, "course deleted successfully");
    }

    @Override
    public ApiResponse getCourse(Long courseId) {
        Course course = getCourseById(courseId);
        CourseResponse response = new CourseResponse();
        CourseResponse responseDto = response.toDto(course);
        return new ApiResponse("success", true, responseDto);
    }

    @Override
    public ApiResponse getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        if (courseList.isEmpty()) {
            throw new UserException("there is no any course", HttpStatus.NOT_FOUND);
        }
        List<CourseResponse> responseList = courseList
                .stream()
                .map(course -> new CourseResponse().toDto(course))
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("courses", responseList);
        result.put("current_page", 1);
        result.put("total", courseList.size());
        return new ApiResponse("success", true, result);
    }

    @Override
    public ApiResponse getCourseByUserId(Long userId) {
        User user = getUserById(userId);
        Set<Course> courses = user.getCourses();
        List<CourseResponse> list = courses
                .stream()
                .map(course -> new CourseResponse().toDto(course))
                .toList();
        Map<String, Object> map = new HashMap<>();
        map.put("users", list);
        map.put("currentPage", 1);
        map.put("total Items", list.size());
        return new ApiResponse("success", true, map);
    }

    @Override
    public ApiResponse getStudentByCourseId(Long courseId) {
        Course course = getCourseById(courseId);
        Set<User> users = course.getUsers();
        List<UserResponseForCourse> list = users
                .stream()
                .map(user -> new UserResponseForCourse().toDto(user))
                .toList();
        Map<String, Object> map = new HashMap<>();
        map.put("users", list);
        map.put("currentPage", 1);
        map.put("total Items", list.size());
        return new ApiResponse("success", true, map);
    }

    @Override
    public ApiResponse addUserToCourse(Long userId, Long courseId) {
        Course course = getCourseById(courseId);
        User user = getUserById(userId);
        course.getUsers().add(user);
        Course savedCourse = courseRepository.save(course);
        CourseResponse dto = new CourseResponse().toDto(savedCourse);
        return new ApiResponse("success", true, "course  " + dto + " has been to the user: " + userId);
    }

    @Override
    public ApiResponse removeUserFromCourse(Long userId, Long courseId) {
        User user = getUserById(userId);
        Course course = getCourseById(courseId);
        course.getUsers().remove(user);
        courseRepository.save(course);
        return new ApiResponse("success", true, " user: " + userId + " has been removed from course: " + courseId);
    }


    private Course getCourseById(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return course.get();
        }
        throw new UserException("there is no course with id " + courseId);
    }

    private User getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("there is no course with id " + userId);
    }
}
