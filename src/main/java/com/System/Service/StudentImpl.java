package com.System.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.System.DTOS.CourseDTO;
import com.System.DTOS.RegisteredCourseDTO;
import com.System.DTOS.StudentDTO;
import com.System.Entities.Course;
import com.System.Entities.CourseRegistration;
import com.System.Entities.Student;
import com.System.Entities.User;
import com.System.Enum.Status;
import com.System.Exceptions.DuplicateRecordException;
import com.System.Exceptions.DuplicateUsernameException;
import com.System.Exceptions.FieldEmptyException;
import com.System.Exceptions.RecordNotFoundException;
import com.System.Repository.CourseRegistrationRepo;
import com.System.Repository.CourseRepository;
import com.System.Repository.StudentRepository;
import com.System.Repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class StudentImpl {
	
	@Autowired
	UserRepository userRepo;
	
	private static final Logger logger=LoggerFactory.getLogger(StudentImpl.class);

	
	@Autowired 
	CourseRepository courseRepo;
	
	@Autowired 
	Validator validator;
	
	@Autowired
	StudentRepository repo;
	
	@Autowired
	CourseRegistrationRepo coursereg;
	
	public StudentImpl() {
		// TODO Auto-generated constructor stub
	}

	
	@Transactional
	public Student addStudent(User user,Student student) {
		
		logger.error(" get records {} {} {}",user.getUsername(),user.getUseremail(),student.getStudentemail());
		
		Set<ConstraintViolation<Student>> violations=validator.validate(student);
		Set<ConstraintViolation<User>> violations2=validator.validate(user);

		if(!violations.isEmpty() || !violations2.isEmpty())
		{
			if(!violations.isEmpty())
				throw new ConstraintViolationException(violations);
			else
				throw new ConstraintViolationException(violations2);
		}
		boolean ifexist=repo.findBystudentemail(student.getStudentemail())!=null || userRepo.findByuseremail(user.getUseremail())!=null;
		
		logger.error(" get records {} {} {}",repo.findBystudentemail(student.getStudentemail()) , userRepo.findByuseremail(user.getUseremail()) ,
		userRepo.findByusername(user.getUsername()));
		if(ifexist)
			throw new DuplicateRecordException("Student exist with same email");
		boolean ifusernamepresent=userRepo.findByusername(user.getUsername())!=null;
		if(ifusernamepresent)
			throw new DuplicateUsernameException("Username already taken!");
		
		
		User u=userRepo.save(user);
		Student s=repo.save(student);
		
		if(u!=null && s!=null)
		  return s;
	
	
		return null;
	}

	public List<Student> getStudents() {
		
		List<Student> students=repo.findAll();
		if(students==null || students.size()==0)
		throw new RecordNotFoundException("No students registered yet!");
		return students;
	}

	@Transactional
	public boolean deleteStudent(String email) {
		Optional<Student> ifexist=Optional.ofNullable(repo.findBystudentemail(email));
		Optional<User> user=Optional.ofNullable(userRepo.findByuseremail(email));
		if(ifexist.isEmpty() || user.isEmpty())
			throw new RecordNotFoundException("Student does not exist with email:!"+email);
		repo.delete(ifexist.get());
	    userRepo.delete(user.get());
		ifexist = Optional.ofNullable(repo.findBystudentemail(email));
		return ifexist.isEmpty();
	}

	public String buyCourse(String email, String courseName,int state) {
		
		logger.error("{} {} {}0",email,courseName,state);
		if(email==null || courseName==null || email.length()==0 || courseName.length()==0)
		{
			if(email==null || email.length()==0)
			throw new FieldEmptyException("Email cannot be empty");
			if(courseName==null || courseName.length()==0)
				throw new FieldEmptyException("courseName cannot be empty");
		}
		
		
		String status="";
		
		Student student=repo.findBystudentemail(email);
		Course course=courseRepo.findBycourseTitle(courseName);
		
		if(student==null)
			throw new RecordNotFoundException("Student not found!");
		if(course==null)
			throw new RecordNotFoundException("Course does not exist!");
		
		System.out.println("find By:{} "+student+course);
		Set<CourseRegistration> ifexist= student.getCourseRegistered();
		System.out.println(ifexist.size());
		logger.error("size {}",ifexist.size());
		for(CourseRegistration reg:ifexist)
		{
			logger.error(" Name: ",reg.getCourseName());
			System.out.println(reg.getCourseName()+" "+course.getCourseTitle());
			
			if(reg.getCourseName().equals(course.getCourseTitle()))
			{
				logger.info(status);
				int state_=reg.isAddedToCart()?0:1;
				logger.error(" state ",state_);
				switch(state_)
				{
				case 1:
					throw new DuplicateRecordException("already purchased!");
				case 0:
					throw new DuplicateRecordException("already in the cart!");
				}
			}
					
		}
	
			
		
		
		
		CourseRegistration newreg=new CourseRegistration();
		logger.error("{} state",state);
		newreg.setAddedToCart(state==0?true:false);
		newreg.setCourse(course);
		newreg.setPurchased(state==1?true:false);
		newreg.setStudent(student);
		newreg.setCourseName(courseName);
		newreg.setRegistrationDate(LocalDateTime.now());
		
		
		student.getCourseRegistered().add(newreg);
		repo.save(student);
		status=state==1?"Purchased successfully":"Added to Cart";
		return status;
	}

	public List<RegisteredCourseDTO> getcourses(String email) {
		List<RegisteredCourseDTO> coursedto=new ArrayList();
		
		
		Long id =repo.findBystudentemail(email).getStudentId();
		System.out.println("student"+ id);
		if(id==null)
			throw new RecordNotFoundException("Student does not exist!");
		
		
		Set<CourseRegistration> courses=coursereg.getcourseIDS(id);
		System.out.println("student"+courses);
		for(CourseRegistration reg:courses)
		{
			
			Course course= courseRepo.findBycourseTitle(reg.getCourseName());
			
			RegisteredCourseDTO dto=new RegisteredCourseDTO();
			dto.setCourseTitle(course.getCourseTitle());
			dto.setDesc(course.getCourseDescription());
			dto.setPrice(course.getPrice());
			dto.setEndDate(course.getEndDate());
			dto.setStartDate(course.getStartDate());
			dto.setRegistrationTime(reg.getRegistrationDate().toLocalDate());
			dto.setStatus(reg.isAddedToCart()?String.valueOf(Status.CART):String.valueOf(Status.PURCHASED));
			
			coursedto.add(dto);
		}
		if(courses==null)
			throw new RecordNotFoundException("No courses enrolled for student"+ id);
		return coursedto;
	}


	public StudentDTO getStudentData(String email) {
		logger.error("impl hit");
		Optional<Student> student=Optional.ofNullable(repo.findBystudentemail(email));
		//logger.error("student is  {}",student.get());
		if(student.isEmpty())
			throw new RecordNotFoundException("No student details found!");
		StudentDTO studentdto=new StudentDTO();
		studentdto.setFirstName(student.get().getFirstName());
		studentdto.setLastName(student.get().getLastName());
		studentdto.setEmail(student.get().getStudentemail());
		studentdto.setMobile(student.get().getMobileNumber());
		
		return studentdto;
	}


	public Set<CourseDTO> getAllCourses() {
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
		return courselist;
	}


	public HttpStatusCode deleteStudentCourse(String email, String courseName) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean updateCourseRegistration(String email, String courseName) {
		Optional<Course> c=Optional.ofNullable(courseRepo.findBycourseTitle(courseName));
		Optional<Student> s= Optional.ofNullable(repo.findBystudentemail(email));
		
		logger.error("hit checkout{} {}",s.get().getFirstName(),c.get().getCourseTitle());

		
		if(s.isEmpty())
			throw new RecordNotFoundException("Student details not found! "+email);
		if(c.isEmpty())
			throw new RecordNotFoundException("Course details not found! "+courseName);
	
		
		Course c1=c.get();
		Student s1=s.get();
		Optional<CourseRegistration> cr= coursereg.findByStudentAndCourse(s1, c1);
		
		if(cr.isPresent() && cr.get().isPurchased() )
			throw new DuplicateRecordException("Already purchased");
		if(cr.isEmpty())
			throw new RecordNotFoundException("Course not in the cart or already purchased!");
	    cr.get().setAddedToCart(false);
	    cr.get().setPurchased(true);
	    coursereg.save(cr.get());
	    return true;
	}
	
	public String deleteCourseRegistration(String email, String courseName) {
		Optional<Course> c=Optional.ofNullable(courseRepo.findBycourseTitle(courseName));
		Optional<Student> s= Optional.ofNullable(repo.findBystudentemail(email));
		
		logger.error("hit checkout{} {}",s.get().getFirstName(),c.get().getCourseTitle());

		
		if(s.isEmpty())
			throw new RecordNotFoundException("Student details not found! "+email);
		if(c.isEmpty())
			throw new RecordNotFoundException("Course details not found! "+courseName);
	
		
		Course c1=c.get();
		Student s1=s.get();
		Optional<CourseRegistration> cr= coursereg.findByStudentAndCourse(s1, c1);
		
		if(cr.isEmpty())
			throw new RecordNotFoundException("Already cancelled!");
	    cr.get().setAddedToCart(false);
	    cr.get().setPurchased(true);
	    coursereg.delete(cr.get());
	    return s.get().getFirstName();
	}
	
}
