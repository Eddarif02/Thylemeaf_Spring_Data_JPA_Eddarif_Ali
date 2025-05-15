package ma.emsi.hopital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.emsi.hopital.entities.Patient;
import ma.emsi.hopital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {

        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);

        return "patient";
    }

    @GetMapping("/admin/formPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")//proteger coté serveur car masquer 'est proteger cote client
    public String delete(@RequestParam Long id,
                         @RequestParam String keyword,
                         @RequestParam int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@RequestParam Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        return "formPatient";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }
}