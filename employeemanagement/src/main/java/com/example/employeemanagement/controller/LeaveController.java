package com.example.employeemanagement.controller;

import java.util.logging.Logger;

import com.example.employeemanagement.model.EmployeeLeaveBalance;
import com.example.employeemanagement.model.LeaveType;
import com.example.employeemanagement.repositories.EmployeeLeaveBalanceRepository;
import com.example.employeemanagement.repositories.LeaveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge = 3600)
public class LeaveController {
    
    private static Logger logger = Logger.getLogger(LeaveController.class.getName());
    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    private final LeaveRepository leaveRepository;

    public LeaveController(LeaveRepository leaveRepository){
        this.leaveRepository = leaveRepository;
        if(leaveRepository.count() == 0){
            leaveRepository.save(new LeaveType("Earned Leaves", 12));
            leaveRepository.save(new LeaveType("Sick Leaves", 10));
            leaveRepository.save(new LeaveType("Loss of Pay", 365));
            leaveRepository.save(new LeaveType("Casual Leaves", 10));
            leaveRepository.save(new LeaveType("Comp Off", 365));
        }
    }


    /*************************************************************
     * This API will all the leaves types to populate the DD on UI
     **************************************************************/
    @GetMapping("/leaveType")
    public Iterable<LeaveType> getLeaveTypes() {
        logger.info("Fetching Leave types");
        return leaveRepository.findAll();
    }


    /*************************************************************
     * This API will fetch leave balances of all users.
     **************************************************************/
    @GetMapping("/leaveBalance")
    public Iterable<EmployeeLeaveBalance> getLeaveBalanceReport() {
        logger.info("Fetching Leave types");
        return employeeLeaveBalanceRepository.findAll();
    }


    /*************************************************************
     * This API will fetch leave balance of a particular user.
     **************************************************************/
    @GetMapping("/leaveBalance/{empId}")
    public Iterable<EmployeeLeaveBalance> getEmployeeLeaveBalance(@PathVariable Integer empId) {
        logger.info("Fetching Leave Balance");
        return employeeLeaveBalanceRepository.findEmployeeLeaves(empId);
    }


    /*************************************************************
     * This API will fetch user based on leave types. This will be
     * used on front end for filtering.
     **************************************************************/
    @GetMapping("/leavetype/{leaveType}")
    public Iterable<EmployeeLeaveBalance> getEmployeeSpecificLeaveBalance(@PathVariable Integer leaveType) {
        logger.info("Fetching Leave Balance");
        return employeeLeaveBalanceRepository.findEmployeesWithLeaveType(leaveType);
    }
}
