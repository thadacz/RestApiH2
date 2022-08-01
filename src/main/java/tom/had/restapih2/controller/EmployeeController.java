package tom.had.restapih2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import tom.had.restapih2.model.Employee;
import tom.had.restapih2.model.EmployeeDto;
import tom.had.restapih2.service.EmployeeService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController // zawiera response body (skonwertowana do jsona)
@RequiredArgsConstructor //
public class EmployeeController {

    private static final Long EMPTY_ID = null;
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        List<EntityModel<Employee>> employees = employeeService.getEmployees().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees")
                        ))
                .toList();
        return CollectionModel.of(employees,
                linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel()
                );
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployee(@PathVariable Long id) {
        return EntityModel.of(employeeService.getEmployee(id),
                linkTo(methodOn(EmployeeController.class).getEmployee(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel().withRel("employees"),
                linkTo(EmployeeController.class).slash("employees").slash(id).slash("deactivate")
                        .withRel("deactivate")
                );
    }

    @PostMapping("/employees")
    public ResponseEntity<EntityModel<Employee>> createEmployee(@RequestBody EmployeeDto employeeDTO) { // RequestBody automatycznie konwertuje do jsona
        Employee employee = employeeService.createEmployee(new Employee(
                EMPTY_ID,
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.isActive(),
                employeeDTO.getCreated()
        ));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/employees/{id}")
                .buildAndExpand(employee.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .body(EntityModel.of(employee,
                        linkTo(methodOn(EmployeeController.class).createEmployee(employeeDTO)).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel().withRel("employees"),
                        linkTo(EmployeeController.class).slash("employees").slash(employee.getId()).slash("deactivate")
                                .withRel("deactivate")
                ));
    }

    @PutMapping("/employees")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDTO) {
        employeeService.updateEmployee(new Employee(
                id,
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.isActive(),
                employeeDTO.getCreated()
        ));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/emloyees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // /emplyees/1?param1=1&param2=2
    @PutMapping("/employees/{id}/deactivate")
    //public void deactivateEmployee(@PathVariable Long id,@RequestParam(required = false) Long param1, @RequestParam(required = false) Long param2){
    public void deactivateEmployee(@PathVariable Long id){

    }
}
