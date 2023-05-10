package com.example.employeemanagement.controller;

import java.util.logging.Logger;

import com.example.employeemanagement.dto.MessageDetails;
import com.example.employeemanagement.dto.ProjectInfo;
import com.example.employeemanagement.exceptions.ProjectNotFoundException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/project")
public class ProjectController {
   
    private static Logger logger = Logger.getLogger(ProjectController.class.getName());
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    

    /*************************************************************
     * This API will all projects with their details
    **************************************************************/
    @GetMapping
    public Iterable<Project> getProjects(){
        return projectRepository.findAll();
    }


     /*************************************************************
     * This API will fetch all the teambers of a project
    **************************************************************/
    @GetMapping("/team/{projectId}")
    public Project getProjectMembers(@PathVariable Integer projectId){
        Project project = projectRepository.findById(projectId).get();
        if(project == null)
            throw new ProjectNotFoundException(projectId);
        else
            return project;
    }

    /*************************************************************
     * This API will create a new project along with with project 
     * manager mapping.
    **************************************************************/
    @PostMapping
    public ResponseEntity<MessageDetails> saveProjectDetails(@RequestBody ProjectInfo proj){
        logger.info("Creating New Project");

        Project project = new Project(proj.getProjectCode(), proj.getProjectName(),proj.getTeamSize());
        project.setProjectManager(employeeRepository.findById(proj.getProjectManagerId()).get());

        project =  projectRepository.save(project);

        Employee employee = employeeRepository.findById(proj.getProjectManagerId()).get();
        employee.setProject(project);
        employeeRepository.save(employee);

        MessageDetails msg = new MessageDetails("Project created successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }


    /*************************************************************
     * This API will update change the project details, project manager 
     * mapping and manager of team members.
    **************************************************************/
    @PutMapping
    public ResponseEntity<MessageDetails> updateProjectDetails(@RequestBody ProjectInfo proj){
        logger.info("Updating Project Details");

        Project project = projectRepository.findById(proj.getProjectId()).get();
        project.setProjectCode(proj.getProjectCode());
        project.setProjectName(proj.getProjectName());
        project.setTeamSize(proj.getTeamSize());
       
        //if the project manager is changed
        if(proj.getProjectManagerId() != project.getProjectManager().getEmployeeId()){
            //Remove mapping from previous manager
            project.getProjectManager().setProject(null);
            projectRepository.save(project);
            Employee newManager = employeeRepository.findById(proj.getProjectManagerId()).get();
            project.setProjectManager(newManager);
            Iterable<Employee> employeeList = employeeRepository.findEmployeesByProject(project.getProjectId());  
            
            for (Employee emp : employeeList){
                emp.setManager(newManager);
                employeeRepository.save(emp);
            }

            newManager.setProject(project);
            employeeRepository.save(newManager);
        }
        
       
        projectRepository.save(project);

        MessageDetails msg = new MessageDetails("Project updated successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }


     /*************************************************************
     * This API map employee to a project
    **************************************************************/ 
    @PutMapping("/map")
    public ResponseEntity<MessageDetails> mapEmployeeToProject(@RequestBody ProjectInfo proj){
        logger.info("Mapping an Employee to a Project");

       // Project project = projectRepository.findByProjectCode(proj.getProjectCode());
        Project project = projectRepository.findById(proj.getProjectId()).get();
        //project.getTeamMembers().clear();
    
        for (int empId : proj.getTeamMembers()) {
            Employee teamMember = employeeRepository.findById(empId).get();
            teamMember.setProject(project);
            teamMember.setManager(employeeRepository.findById(project.getProjectManager().getEmployeeId()).get());
            employeeRepository.save(teamMember);
            project.getTeamMembers().add(teamMember);
        }

        projectRepository.save(project);

        MessageDetails msg = new MessageDetails("Team members added successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

     
    /*************************************************************
     * This API will unmap employees from a project
    **************************************************************/
    @PutMapping("/unmap")
    public ResponseEntity<MessageDetails> unmapEmployeeToProject(@RequestBody ProjectInfo proj){
        logger.info("Unmapping an Employee to a Project");

        Project project = projectRepository.findById(proj.getProjectId()).get();       
        
        for (int empId : proj.getTeamMembers()) {
            Employee teamMember = employeeRepository.findById(empId).get();
            teamMember.setProject(null);
            teamMember.setManager(null);
            employeeRepository.save(teamMember);
            project.getTeamMembers().remove(teamMember);
        }

        projectRepository.save(project);

        MessageDetails msg = new MessageDetails("Project updated successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }


     /*************************************************************
     * This API will delete the project along with the team members and
     * manager mapping.
    **************************************************************/
    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageDetails> deleteProject(@PathVariable Integer projectId){
        logger.info("Deleting Project");

        //Removing mappings from child tables
        Project project = projectRepository.findById(projectId).get();  
         if(project == null)
            throw new ProjectNotFoundException(projectId);   
        project.getTeamMembers().clear();  
        projectRepository.save(project);

        Iterable<Employee> employee = employeeRepository.findEmployeesByProject(project.getProjectId());       
        
        for (Employee emp : employee){
            emp.setProject(null);
            emp.setManager(null);
            employeeRepository.save(emp);
        }

        projectRepository.deleteById(projectId);

        MessageDetails msg = new MessageDetails("Project deleted successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }
    
}
