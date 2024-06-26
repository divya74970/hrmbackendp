package com.csi.repo;

import com.csi.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Employee findByEmpEmailIdAndEmpPassword(String empEmailId, String empPassword);

    Employee findByEmpName(String empName);

    Employee findByEmpContactNumber(long empContactNumber);

    Employee findByEmpDOB(Date empDOB);
}
