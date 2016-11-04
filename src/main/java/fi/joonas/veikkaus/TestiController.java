package fi.joonas.veikkaus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestiController {

    @GetMapping("/testi")
    public String testiForm(Model model) {
        model.addAttribute("testi", new Testi());
        return "testi";
    }

    @PostMapping("/testi")
    public String testiSubmit(@ModelAttribute Testi testi) {
        return "testiresult";
    }

}
