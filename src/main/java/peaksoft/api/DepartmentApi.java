package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Department;
import peaksoft.exceptions.MyException;

import peaksoft.service.DepartmentService;

@Controller
@RequestMapping("/department")
@RequiredArgsConstructor

public class DepartmentApi {

    private final DepartmentService departmentService;

    @GetMapping()
    String findAll(Model model){
        try {
            model.addAttribute("alldepartments", departmentService.getAllDepartments());
        } catch ( MyException e) {
            throw new RuntimeException(e);
        }
        return "Department/getAllDep";
    }

    @GetMapping("/create/{hospitalId}")
    String createDepartmentByHospitalId(@PathVariable Long hospitalId, Model model){
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("newDepartment", new Department());
        return "Department/save-page";
    }

    @RequestMapping (value = "/save/{hospitalId}", method = RequestMethod.POST)
    String saveDepartment(@PathVariable Long hospitalId, @ModelAttribute Department department){
        try {
            departmentService.saveDepartment(department,hospitalId);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/department/" +hospitalId;
    }
    @GetMapping("/{hospitalId}")
    String findAllDepartmentByHospitalId(Model model, @PathVariable Long hospitalId) throws MyException {
      try {


        model.addAttribute("hospitalId",hospitalId);
        model.addAttribute("departments", departmentService.findAll(hospitalId));
        return "Department/findDepartmentByHospital";
    }catch (Exception e){
          throw new MyException("It is error while getting department by hospital id");
      }
    }

    @DeleteMapping("{departmentId}/delete")
    String deletedep(@PathVariable("departmentId") Long id ){
        try {
            departmentService.deleteDepartment(id);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/department";
    }

    @GetMapping("{departmentId}/depUpdate")
    public String newUpdate(@PathVariable Long departmentId, Model model) {
        try {
            model.addAttribute("depUpdate",departmentService.getDepartmentById(departmentId));
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "Department/depUpdate";
    }

    @PostMapping("{departmentId}/depEdit")
    public String updateHos(@PathVariable Long departmentId, @ModelAttribute("depUpdate") Department newDepartment) {
        try {
            departmentService.updateDepartment(departmentId, newDepartment);
        } catch (MyException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/department";
    }

}