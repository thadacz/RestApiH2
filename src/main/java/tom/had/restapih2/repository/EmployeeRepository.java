package tom.had.restapih2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tom.had.restapih2.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
