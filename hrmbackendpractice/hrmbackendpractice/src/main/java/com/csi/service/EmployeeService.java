package com.csi.service;

import com.csi.model.Employee;
import com.csi.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public String saveData(Employee employee) {
        employeeRepo.save(employee);
        return "Data Save sucessfully";
    }

    public boolean signIn(String empEmailId, String empPassword) {

        Employee employee = employeeRepo.findByEmpEmailIdAndEmpPassword(empEmailId, empPassword);
        boolean flag = false;

        if (employee != null && employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)) {
            flag = true;
        }
        return flag;
    }

    public Optional<Employee> findById(int empId) {
        return employeeRepo.findById(empId);
    }

    public Employee findByEmpContactNumber(long empContactNumber) {
        return employeeRepo.findByEmpContactNumber(empContactNumber);
    }

    public Employee findByEmpName(String empName) {
        return employeeRepo.findByEmpName(empName);
    }

    public Employee findByDob(Date empDOB) {
        return employeeRepo.findByEmpDOB(empDOB);
    }

    public List<Employee> findAll(){
       return employeeRepo.findAll();
    }

    public Employee update(Employee employee){
        return employeeRepo.save(employee);
    }

    public Employee changeAddress(Employee employee){
       return employeeRepo.save(employee);
    }

    public void deleteById(int empId) {
        employeeRepo.deleteById(empId);
    }

    public void deleteAll() {
        employeeRepo.deleteAll();
    }

}
