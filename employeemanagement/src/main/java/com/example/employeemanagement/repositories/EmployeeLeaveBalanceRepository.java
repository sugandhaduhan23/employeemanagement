package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.EmployeeLeaveBalance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeLeaveBalanceRepository extends CrudRepository<EmployeeLeaveBalance, Integer> {
    
    @Query(value="SELECT * FROM employeeleavebalance WHERE employee_id = :empId", nativeQuery = true)
    Iterable<EmployeeLeaveBalance> findEmployeeLeaves(Integer empId);

    @Query(value="SELECT * FROM employeeleavebalance WHERE leave_type_id = :leaveType", nativeQuery = true)
    Iterable<EmployeeLeaveBalance> findEmployeesWithLeaveType(Integer leaveType);

    @Query(value="SELECT * FROM employeeleavebalance WHERE employee_id = :empId and leave_type_id = :leaveType", nativeQuery = true)
    EmployeeLeaveBalance findEmployeeLeaveBalance(Integer empId, Integer leaveType);

}
