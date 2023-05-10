package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.EmployeeLeaves;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeLeaveRepository extends CrudRepository<EmployeeLeaves, Integer>{
    
    @Query(value="SELECT * FROM employeeleaves WHERE employee_id = :empId", nativeQuery = true)
    Iterable<EmployeeLeaves> findEmployeeLeaveRequests(Integer empId);

    @Query(value="SELECT EL.* FROM employeeleaves EL, employee E WHERE EL.employee_id = E.employee_id and project_id = :project_id and role_id = 3 and status = :status", nativeQuery = true)
    Iterable<EmployeeLeaves> findPendingLeaveRequests(Integer project_id, Integer status);

    // @Query(value="SELECT Distinct EL.* FROM employeeleaves EL, employee E WHERE EL.employee_id = E.employee_id and "+
    // "(role_id = 2 and status = :status) "+
    // "UNION "+
    // "SELECT Distinct EL.* FROM employeeleaves EL, employee E WHERE EL.employee_id = E.employee_id and "+
    // "(project_id is null and status = :status);", nativeQuery = true)
    // Iterable<EmployeeLeaves> findPendingLeaveWithNoProject(Integer status);

    @Query(value="SELECT EL.* FROM employeeleaves EL, employee E WHERE EL.employee_id = E.employee_id and manager_id is null and status = :status", nativeQuery = true)
    Iterable<EmployeeLeaves> findPendingLeaveWithNoProject(Integer status);
    
}
