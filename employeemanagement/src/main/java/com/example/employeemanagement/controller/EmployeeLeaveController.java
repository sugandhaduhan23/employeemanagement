package com.example.employeemanagement.controller;

import java.util.logging.Logger;

import com.example.employeemanagement.dto.LeaveRequest;
import com.example.employeemanagement.dto.MessageDetails;
import com.example.employeemanagement.exceptions.InsufficientLeaveBalanceException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.EmployeeLeaveBalance;
import com.example.employeemanagement.model.EmployeeLeaves;
import com.example.employeemanagement.model.LeaveType;
import com.example.employeemanagement.repositories.EmployeeLeaveBalanceRepository;
import com.example.employeemanagement.repositories.EmployeeLeaveRepository;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.repositories.LeaveRepository;

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
@RequestMapping("/leave")
public class EmployeeLeaveController {
   
    private static Logger logger = Logger.getLogger(EmployeeLeaveController.class.getName());
    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;
    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LeaveRepository leaveTypeRepository;

    
     /*************************************************************
     * This API will fetch all the leave requests of the employees 
     * irrespective of the status.
     **************************************************************/
    @GetMapping
    public Iterable<EmployeeLeaves> fetchEmployeeLeaveRequestReport(){
        logger.info("Fetching Employee Leaves");
        return employeeLeaveRepository.findAll();
    }

     /*************************************************************
     * This API will fetch all the leave requests of the employees 
     * by employee id.
     **************************************************************/
    @GetMapping("/{empId}")
    public Iterable<EmployeeLeaves> findEmployeeLeaves(@PathVariable Integer empId){
        logger.info("Fetching Employee Leaves");
        return employeeLeaveRepository.findEmployeeLeaveRequests(empId);
    }


     /*************************************************************
     * This API will fetch leave requests of the employees working 
     * under a project manager. If the employee has not been assigned
     * any project manager the leave request will be redirected to HR's
     * inbox for approval
     **************************************************************/
    @GetMapping("/{roleId}/{projectId}/{status}")
    public Iterable<EmployeeLeaves> fetchEmployeePendingLeaveRequests(@PathVariable Integer roleId, @PathVariable(required = false) Integer projectId, @PathVariable(required = false) Integer status){
        logger.info("Fetching Employee Leave Request");
        if(roleId == 2){
            return employeeLeaveRepository.findPendingLeaveRequests(projectId, status);
        }
        else
            return employeeLeaveRepository.findPendingLeaveWithNoProject(status);
    }

    
     /*************************************************************
     * This API will allow the employee to raise a leave request
     * in the system. Once the request is raised the leave balance
     * will also be upadted with the leftover leaves.
     **************************************************************/
    @PostMapping
    public ResponseEntity<MessageDetails> saveLeaveRequest(@RequestBody LeaveRequest leaveRequest){
        logger.info("Saving Employee Leave Request");

        Employee employee = employeeRepository.findById(leaveRequest.getEmployeeId()).get();
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequest.getLeaveTypeId()).get();

        EmployeeLeaves employeeLeaveRequest = new EmployeeLeaves(employee, leaveType, leaveRequest.getLeaveReason(),
                                                                 leaveRequest.getLeaveFrom(), leaveRequest.getLeaveTo(), 
                                                                 leaveRequest.getLeaveCount(), 1);
        
        //Update Leftover Leave Balance
        EmployeeLeaveBalance empLeaveBalance = employeeLeaveBalanceRepository.findEmployeeLeaveBalance(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeId());
        var leaveBalance = empLeaveBalance.getLeaveBalance() - leaveRequest.getLeaveCount();
        if(leaveBalance<=0)
            throw new InsufficientLeaveBalanceException();
        else
            empLeaveBalance.setLeaveBalance(leaveBalance);

        employeeLeaveRepository.save(employeeLeaveRequest);
        employeeLeaveBalanceRepository.save(empLeaveBalance);
        
        MessageDetails msg = new MessageDetails("Leave Request saved successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);

    }

    
     /*************************************************************
     * This API will let the employee update the leave request.
     **************************************************************/
    @PutMapping
    public ResponseEntity<MessageDetails> updateLeaveRequest(@RequestBody LeaveRequest leaveRequest){
        logger.info("Updating Employee Leave Request");

        EmployeeLeaves employeeLeaveRequest = employeeLeaveRepository.findById(leaveRequest.getId()).get();
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequest.getLeaveTypeId()).get();

        employeeLeaveRequest.setLeave(leaveType);
        employeeLeaveRequest.setLeaveReason(leaveRequest.getLeaveReason());
        employeeLeaveRequest.setLeaveCount(leaveRequest.getLeaveCount());
        employeeLeaveRequest.setLeaveFrom(leaveRequest.getLeaveFrom());
        employeeLeaveRequest.setLeaveTo(leaveRequest.getLeaveTo());

         //Update Leftover Leave Balance
         EmployeeLeaveBalance empLeaveBalance = employeeLeaveBalanceRepository.findEmployeeLeaveBalance(leaveRequest.getEmployeeId(), leaveRequest.getLeaveTypeId());
         var leaveBalance = empLeaveBalance.getLeaveBalance() - leaveRequest.getLeaveCount();
         if(leaveBalance<=0)
             throw new InsufficientLeaveBalanceException();
         else if(employeeLeaveRequest.getLeaveCount() != leaveRequest.getLeaveCount())
             empLeaveBalance.setLeaveBalance(leaveBalance);

        employeeLeaveRepository.save(employeeLeaveRequest);

        MessageDetails msg = new MessageDetails("Leave Request details updated successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }


     /*************************************************************
     * This API will allow the managers to approve and reject the 
     * leave request.
     **************************************************************/
    @PutMapping("/updateStatus")
    public ResponseEntity<MessageDetails> manageLeaveRequest(@RequestBody LeaveRequest leaveRequest){
        logger.info("Approve/Reject Leaves");

        EmployeeLeaves employeeLeaveRequest = employeeLeaveRepository.findById(leaveRequest.getId()).get();
        employeeLeaveRequest.setStatus(leaveRequest.getStatus());
    
        if(leaveRequest.getStatus() == 3){
            EmployeeLeaveBalance empLeaveBalance = employeeLeaveBalanceRepository.findEmployeeLeaveBalance(employeeLeaveRequest.getEmployee().getEmployeeId(), employeeLeaveRequest.getLeave().getLeaveTypeId());
            var leaveBalance = empLeaveBalance.getLeaveBalance() + employeeLeaveRequest.getLeaveCount();
            empLeaveBalance.setLeaveBalance(leaveBalance);
            employeeLeaveBalanceRepository.save(empLeaveBalance);
        }

        employeeLeaveRepository.save(employeeLeaveRequest);


        MessageDetails msg = new MessageDetails("Leave Status updated successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }


     /*************************************************************
     * This API will let the employee withdraw the leave request. Once
     * the leave is withdrawn the leaves would be credited back to the
     * system.
     **************************************************************/
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDetails> withdrawLeaveRequest(@PathVariable Integer id){
        logger.info("Withdraw Employee Leave Request");

        //Credit Leave Balance
        EmployeeLeaves employeeLeaveRequest = employeeLeaveRepository.findById(id).get();
        EmployeeLeaveBalance empLeaveBalance = employeeLeaveBalanceRepository.findEmployeeLeaveBalance(employeeLeaveRequest.getEmployee().getEmployeeId(), employeeLeaveRequest.getLeave().getLeaveTypeId());
        var leaveBalance = empLeaveBalance.getLeaveBalance() + employeeLeaveRequest.getLeaveCount();
        empLeaveBalance.setLeaveBalance(leaveBalance);
        employeeLeaveBalanceRepository.save(empLeaveBalance);

        employeeLeaveRepository.deleteById(id);
        
        MessageDetails msg = new MessageDetails("Leave Withdrawn successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

}
