package com.example.springsecurityusersroles.rest;

import com.example.springsecurityusersroles.model.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "Ivan", "Ivanov"),
            new Developer(2l, "Sergey", "Sergeev"),
            new Developer(3L, "Petr", "Petrov")
    ).collect(Collectors.toList());


    @GetMapping
    public List<Developer> getAll() {
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    // @PreAuthorize("hasRole('ADMIN')") // ---- теперь так работает

    public Developer getById(@PathVariable Long id) {
        return DEVELOPERS.stream().filter(developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")

    public Developer create(@RequestBody Developer developer) {
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")

    public void deleteById(@PathVariable Long id) {
        this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }

}
