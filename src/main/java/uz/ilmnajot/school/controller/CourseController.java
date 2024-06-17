package uz.ilmnajot.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.CourseRequest;
import uz.ilmnajot.school.service.CourseService;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADD_COURSE')") //it works...
    @PostMapping("/addCourse")
    public HttpEntity<ApiResponse> addCourse(@RequestBody CourseRequest request) {
        ApiResponse apiResponse = courseService.addCourse(request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PreAuthorize("hasAuthority('ADD_USER_TO_COURSE')") //it works...
    @PostMapping("/addUserToCourse/{userId}/{courseId}")
    public HttpEntity<ApiResponse> addUserToCourse(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.addUserToCourse(userId, courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PreAuthorize("hasAuthority('DELETE_USER_FROM_COURSE')") //it works...
    @DeleteMapping("/removeUserFromCourse/{userId}/{courseId}")
    public HttpEntity<ApiResponse> removeUserFromCourse(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.removeUserFromCourse(userId, courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('GET_COURSE')") //it works...
    @GetMapping("/getCourse/{courseId}")
    public HttpEntity<ApiResponse> getCourse(@PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.getCourse(courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PreAuthorize("hasAuthority('GET_COURSE_BY_STUDENT_ID')") //it works...
    @GetMapping("/getCourseByStudentId/{studentId}")
    public HttpEntity<ApiResponse> getCourseByStudentId(@PathVariable(name = "studentId") Long studentId) {
        ApiResponse apiResponse = courseService.getCourseByUserId(studentId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_STUDENTS_BY_COURSE_ID')") //it works...
    @GetMapping("/getStudentByCourseId/{courseId}")
    public HttpEntity<ApiResponse> getStudentByCourseId(@PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.getStudentByCourseId(courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_COURSE')") //it works...
    @GetMapping("/allCourses")
    public HttpEntity<ApiResponse> getAllCourses() {
        ApiResponse allNews = courseService.getAllCourses();
        return allNews != null
                ? ResponseEntity.status(HttpStatus.OK).body(allNews)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')") //it works...
    @DeleteMapping("/deleteCourse/{courseId}")
    public HttpEntity<ApiResponse> removeCourse(@PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.deleteCourse(courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')") //it works...
    @PutMapping("/updateCourse/{courseId}")
    public HttpEntity<ApiResponse> updateCourse(
            @RequestBody CourseRequest request,
            @PathVariable(name = "courseId") Long courseId) {
        ApiResponse apiResponse = courseService.updateCourse(request, courseId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.OK).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
