package reception.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/reception")
public class ReceptionController {

    private final RestTemplate restTemplate;

    public ReceptionController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/users")
    public List<?> getUsers() {
        return restTemplate.getForObject("http://users-service/user", List.class);
    }
}
