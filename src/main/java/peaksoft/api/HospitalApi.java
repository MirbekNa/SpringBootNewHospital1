package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import peaksoft.entity.Hospital;
import peaksoft.exceptions.MyException;
import peaksoft.service.HospitalService;


@Controller
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalApi {

    private final HospitalService hospitalService;
    @GetMapping
    public String getAllCompanies(Model model) throws MyException {
        model.addAttribute("allHospitals", hospitalService.getAllHospitals());
        return "Hospital/getAll";
    }
    @GetMapping("/new")
    String createHospital(Model model){
        model.addAttribute("newHospital", new Hospital());
        return "Hospital/newHospital";
    }

    @PostMapping("/save")
    String saveHospital(@ModelAttribute ("newHospital") Hospital hospital) throws MyException {
        hospitalService.saveHospital(hospital);
        return "redirect:/hospital";
    }

    @GetMapping("{hospitalId}/delete")
    String deleteHospital(@PathVariable("hospitalId") Long id ) throws MyException {
        hospitalService.deleteHospital(id);
        return "redirect:/hospital";
    }


    @GetMapping("{hospitalId}/newUpdate")
    public String newUpdate(@PathVariable Long hospitalId, Model model) throws MyException {
        model.addAttribute("update", hospitalService.getHospitalById(hospitalId));
        return "Hospital/newUpdate";
    }

    @PostMapping("{hospitalId}/edit")
    public String updateHos(@PathVariable Long hospitalId, @ModelAttribute("update") Hospital newhospital) throws MyException {
        hospitalService.updateHospital(hospitalId, newhospital);
        return "redirect:/hospital";
    }
}
