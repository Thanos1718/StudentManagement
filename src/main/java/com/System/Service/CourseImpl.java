package com.System.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.System.Controller.CourseController;
import com.System.DTOS.CourseDTO;
import com.System.Entities.Course;
import com.System.Exceptions.DatabaseException;
import com.System.Exceptions.DuplicateRecordException;
import com.System.Exceptions.RecordNotFoundException;
import com.System.Repository.CourseRepository;

@Service
public class CourseImpl {
	
	private static final Logger logger= LoggerFactory.getLogger(CourseController.class);

	
	@Autowired
	private CourseRepository courseRepo;

	public CourseImpl() {
		// TODO Auto-generated constructor stub
	}

	public boolean addCourse(CourseDTO course) {
		System.out.println(" Impl course"+course);
		
		Course c=new Course();
		c.setCourseDescription(course.getDescription());
		c.setCourseTitle(course.getCourseName());
		c.setEndDate(course.getEndDate());
		c.setPrice(course.getPrice());
		c.setStartDate(course.getStartDate());
		logger.error("course",c.getCourseTitle());
		
		
		Optional<Course> existcourse=Optional.ofNullable(courseRepo.findBycourseTitle(course.getCourseName()));
		
		logger.error("existed",existcourse);
		if(existcourse.isPresent())
			throw new DuplicateRecordException("course exist!");
		
		Course insertedCourse=courseRepo.save(c);
		
		if(insertedCourse==null)
			throw new DatabaseException("Error while adding record!");
		return insertedCourse!=null;
		
	}

	public CourseDTO getCourse(String title) {
		//System.out.println("service layer"+courseRepo.findBycourseTitle(title));
		
		Optional<Course> course=Optional.ofNullable(courseRepo.findBycourseTitle(title));
		if(course.isPresent())
		{
			CourseDTO dto=new CourseDTO();
			dto.setCourseName(course.get().getCourseTitle());
			dto.setDescription(course.get().getCourseDescription());
			dto.setPrice(course.get().getPrice());
			dto.setStartDate(course.get().getStartDate());
			dto.setEndDate(course.get().getEndDate());
			return dto;
		}
		else
			throw new RecordNotFoundException("Course is not registered yet!");
		
	}

	public Set<CourseDTO> getAllCourses() {
		logger.error("in side get allcoursee");
		
		List<Course> courses=courseRepo.findAll();
		
		Set<CourseDTO> courselist=new HashSet();
		
		if(courses==null || courses.size()==0)
			throw new RecordNotFoundException("Add a course!");
		
		for(Course c : courses)
		{
			
			CourseDTO c1=new CourseDTO();
			c1.setCourseName(c.getCourseTitle());
			c1.setDescription(c.getCourseDescription());
			c1.setEndDate(c.getEndDate());
			c1.setPrice(c.getPrice());
			c1.setStartDate(c.getStartDate());
			courselist.add(c1);
		}
	//	logger.info("fetche courses ")
		return courselist;
	}

	public boolean deleteCourse(CourseDTO course) {
		
		Course c= courseRepo.findBycourseTitle(course.getCourseName());
		if(c==null)
			throw new RecordNotFoundException("Couse does not exist");
		 courseRepo.delete(c);
		 return courseRepo.findBycourseTitle(c.getCourseTitle())==null;
	}

}
