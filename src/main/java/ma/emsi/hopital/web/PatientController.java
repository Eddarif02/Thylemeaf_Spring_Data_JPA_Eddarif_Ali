package ma.emsi.hopital.web;

import lombok.AllArgsConstructor;
import ma.emsi.hopital.entities.Patient;
import ma.emsi.hopital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientrepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page ,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw
                        ) {
        Page<Patient> patientPage = patientrepository.findByNomContains(kw,PageRequest.of(page,size));
        model.addAttribute("keyword",kw);
        model.addAttribute("patientList", patientPage.getContent());
        model.addAttribute("pages", new int [patientPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(name="id") long id,String kayword,int page) {
        patientrepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+kayword;
    }
}
