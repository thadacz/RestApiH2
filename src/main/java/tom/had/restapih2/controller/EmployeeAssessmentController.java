package tom.had.restapih2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import tom.had.restapih2.model.EmployeeAssessment;

@RestController
public class EmployeeAssessmentController {

    @PutMapping("/employees/{id}/assessments/{assessmentId}")
    public EmployeeAssessment updateAssessment(@PathVariable Long id, @PathVariable Long assessmentId){
        return null;
    }
}
