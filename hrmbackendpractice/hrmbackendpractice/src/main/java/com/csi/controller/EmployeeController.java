package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Employee;
import com.csi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveData(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.saveData(employee));
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeService.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<Employee> findByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeService.findByEmpName(empName));
    }

    @GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity<Employee> findByEmpContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeService.findByEmpContactNumber(empContactNumber));
    }

    @GetMapping("/findByDob/{empDOB}")
    public ResponseEntity<Employee> findByEmpDOB(@PathVariable Date empDOB) {
        return ResponseEntity.ok(employeeService.findByDob(empDOB));
    }

    @PutMapping("/update/{enpId}/{employee}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @RequestBody Employee employee) {
        Employee employee1 = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee ID does not Exit"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpaddress(employee.getEmpaddress());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpUID(employee.getEmpUID());
        employee1.setEmpPancard(employee.getEmpPancard());

        return new ResponseEntity<>(employeeService.update(employee1), HttpStatus.CREATED);
    }

    @GetMapping("/filterbyanyinput/{input}")
    public ResponseEntity<List<Employee>> filterByAnyInput(@PathVariable String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(input)
                || emp.getEmpName().equals(input)
                || emp.getEmpEmailId().equals(input)
                || String.valueOf(emp.getEmpContactNumber()).equals(input)
                || String.valueOf(emp.getEmpId()).equals(input)
                || String.valueOf(emp.getEmpSalary()).equals(input)).toList());
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparingInt(Employee::getEmpId)).toList());
    }

    @GetMapping("/sortbyname/{empName}")
    public ResponseEntity<List<Employee>> sortByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());
    }

    @GetMapping("/sortbysalary/{sortBySalary}")
    public ResponseEntity<List<Employee>> sortBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/sortbyDOB/{empDOB}")
    public ResponseEntity<List<Employee>> sortByDOB(@PathVariable Date empDOB) {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());
    }

    @GetMapping("/filterbyid/{empId}")
    public ResponseEntity<List<Employee>> filterById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(employee -> employee.getEmpId() == empId).toList());
    }

    @GetMapping("/filterbyname/{empName}")
    public ResponseEntity<List<Employee>> filterByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(employee -> employee.getEmpName().equals(empName)).toList());
    }

    @GetMapping("/filterbysalary/{empSalary}")
    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpSalary() >= empSalary).toList());
    }

    @GetMapping("/filterbydob/{empDOB}")
    public ResponseEntity<List<Employee>> filterByDOB(@PathVariable String empDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(empDOB)).toList());
    }


    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @Valid @PathVariable String empAddress) {
        Employee employee = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id does not exist"));
        employee.setEmpaddress(empAddress);

        return new ResponseEntity<>(employeeService.changeAddress(employee), HttpStatus.CREATED);

    }

    @GetMapping("/loaneligibility/{empId}")
    public ResponseEntity<String> loanEligibility(@PathVariable int empId) {
        boolean flag = false;

        String msg = "";

        for (Employee employee : employeeService.findAll()) {

            if (employee.getEmpId() == empId && employee.getEmpSalary() >= 50000) {
                msg = "Eligible for loan";

                flag = true;

                break;

            }

            if (!flag) {
                msg = "Not Eligible for vote";
            }


        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll(){
        return  ResponseEntity.ok(employeeService.findAll());
    }


    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deletebyId(@PathVariable int empId) {
        employeeService.deleteById(empId);

        return ResponseEntity.ok("Data deleted Successfully");
    }

    @GetMapping("/sumofsalary")
    public ResponseEntity<Double> sumOfSalary() {
        double sum = employeeService.findAll().stream().collect(Collectors.summingDouble(Employee::getEmpSalary));
        return ResponseEntity.ok(sum);
    }


    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();

        return ResponseEntity.ok("All data deleted Successfully");
    }
}

