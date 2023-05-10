package com.example.employeemanagement.controller;

import java.util.List;
import java.util.logging.Logger;

import com.example.employeemanagement.dto.EmployeeInfo;
import com.example.employeemanagement.dto.EmployeeName;
import com.example.employeemanagement.dto.MessageDetails;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.exceptions.ProjectMappedException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.EmployeeLeaveBalance;
import com.example.employeemanagement.model.EmployeeLeaves;
import com.example.employeemanagement.model.LeaveType;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repositories.DepartmentRepository;
import com.example.employeemanagement.repositories.EmployeeLeaveBalanceRepository;
import com.example.employeemanagement.repositories.EmployeeLeaveRepository;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.repositories.LeaveRepository;
import com.example.employeemanagement.repositories.ProjectRepository;
import com.example.employeemanagement.repositories.RoleRepository;
import com.example.employeemanagement.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/employees")
public class EmployeeController implements CommandLineRunner {
    
    private static Logger logger = Logger.getLogger(EmployeeController.class.getName());
    
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaveRepository leaveTypeRepository;


     /*************************************************************
     * Create a admin when the application is loaded for the
     * first time
     **************************************************************/
    @Override
    public void run(String...args) throws Exception {
        if(employeeRepository.count() == 0){
            EmployeeInfo emp = new EmployeeInfo(null,"Admin",null, null,null,null,null,null,"admin@sfbu.com",1,null,null,null,null);
            saveEmployeeDetails(emp);
        }
    }


     /*************************************************************
     * This API will fetch all the employees to show all the 
     * employees of a organization on UI
     **************************************************************/
    @GetMapping
    public Iterable<Employee> getEmployees(){
        logger.info("Fetching Employees");
        return employeeRepository.findAll();
    }

    
    /*************************************************************
     * This API will fetch a particular employee using employeeID
     **************************************************************/
    @GetMapping("/{employeeID}")
    public Employee getEmployeeId(@PathVariable int employeeID){
        logger.info("Fetching Employee");
        return employeeRepository.findById(employeeID).orElseThrow(()
               -> new EmployeeNotFoundException());
    }

    
    /*************************************************************
     * This API will fetch all the employees name and ids to 
     * populate dropdowns on UI
     **************************************************************/
    @GetMapping("/names")
    public List<EmployeeName> getEmployeeNames(){
        logger.info("Fetching Employees Names");
        return employeeRepository.getEmployeeName();
    }

    
    /*************************************************************
     * This API will fetch all the managers to populate the dropdown
     * on UI
     **************************************************************/
    @GetMapping("/role/{roleId}")
    public Iterable<EmployeeName> getManagers(@PathVariable int roleId){
        logger.info("Fetching Employees");
        return employeeRepository.getEmployeeswithRole(roleId);
    }

    /*************************************************************
     * This API will create new employee along with the credetils.
     * Credentials will allow him to login to the application.
     * As soon as the employee is created, his account will aslo be
     * credited with default leave types.
     **************************************************************/
    @PostMapping
    public ResponseEntity<MessageDetails> saveEmployeeDetails(@RequestBody EmployeeInfo emp){
        logger.info("Saving New Employee");

        Employee employee = new Employee(emp.getEmpName(), emp.getDob(), emp.getDateOfJoining(), emp.getGender(),  emp.getPhoto(), emp.getSalary(),
        emp.getPhoneNo(), emp.getEmailId());
        if(emp.getRoleId()!=null)
        employee.setRole(roleRepository.findById(emp.getRoleId()).get());
        if(emp.getDepartmentId()!=null)
        employee.setDepartment(departmentRepository.findById(emp.getDepartmentId()).get());
        employee.setAddress(emp.getAddress());
        employee = employeeRepository.save(employee);
        
        Iterable<LeaveType> leaveType = this.leaveTypeRepository.findAll();
        //Inserting Leaves for an employee
        for(LeaveType lt : leaveType ){
            EmployeeLeaveBalance elb= new EmployeeLeaveBalance(employee, lt, lt.getLeavesAllowed());
            employeeLeaveBalanceRepository.save(elb);
        }

        //creating user credentials
        User user = new User(employee, employee.getEmailId(), "welcome@123", true);
        userRepository.save(user);
        
        MessageDetails msg = new MessageDetails("Employee Details were saved successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    /*************************************************************
     * This API will update the employee details and project.
     * Only HRs can update the project.
     **************************************************************/
    @PutMapping
    public ResponseEntity<MessageDetails> updateEmployeeDetails(@RequestBody EmployeeInfo emp){
        logger.info("Updating Employee Details");
        
        Employee employee = employeeRepository.findById(emp.getEmployeeId()).get();

        //Changing the project and the manager
         if(emp.getProjectId() != null && emp.getProjectId() != employee.getProject().getProjectId()){
            Project project = projectRepository.findById(emp.getProjectId()).get();
            //Remove mapping from old project
            employee.getProject().getTeamMembers().remove(employee);
            employeeRepository.save(employee);
            //Add Mapping to new project
            project.getTeamMembers().add(employee);
            employee.setProject(project);
            employee.setManager(project.getProjectManager());
         }else if(emp.getProjectId() == null){
            employee.setProject(null);
            employee.setManager(null);
         }

        employee.setEmpName(emp.getEmpName());
        employee.setDob(emp.getDob());
        employee.setDateOfJoining(emp.getDateOfJoining());
        employee.setGender(emp.getGender());
        employee.setPhoto(emp.getPhoto());
        employee.setSalary(emp.getSalary()); 
        employee.setPhoneNo( emp.getPhoneNo());
        employee.setEmailId(emp.getEmailId());
    
        employee.setRole(roleRepository.findById(emp.getRoleId()).get());
        employee.setDepartment(departmentRepository.findById(emp.getDepartmentId()).get());
        employee.setAddress(emp.getAddress());


        employeeRepository.save(employee);
        
        MessageDetails msg = new MessageDetails("Employee Details were upadted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    
    /*************************************************************
     * This API will delete an employee from the system along with it's
     * user credentials , leave requests if any, leave balance and any mappings
     * in the project.
     **************************************************************/
    @DeleteMapping("/{empId}")
    public ResponseEntity<MessageDetails> deleteEmployee(@PathVariable Integer empId){
        logger.info("Deleting Employee and it's mappings");

        Employee employee = employeeRepository.findById(empId).get();
        String projectCode = employee.getProject() != null ? employee.getProject().getProjectCode() : null ;
        
        //Removing employee mapping from any project
        if(projectCode!=null && employee.getRole().getRoleId()!=2){
            employee.getProject().getTeamMembers().remove(employee);
        }else if(projectCode!=null && employee.getRole().getRoleId()==2){
            throw new ProjectMappedException(projectCode);
        }
         
        //Deleting the employee leave balance
        Iterable<EmployeeLeaveBalance> elb = employeeLeaveBalanceRepository.findEmployeeLeaves(empId);
        employeeLeaveBalanceRepository.deleteAll(elb);

         //Deleting the employee leave requests
        Iterable<EmployeeLeaves> el = employeeLeaveRepository.findEmployeeLeaveRequests(empId);
        employeeLeaveRepository.deleteAll(el);

        
        employeeRepository.deleteById(empId);
        MessageDetails msg = new MessageDetails("Employee Details were deleted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }



}

