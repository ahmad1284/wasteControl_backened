package com.example.wasteControl.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringAuditor implements AuditorAware {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("none").filter(s -> !s.isEmpty());
    }
}
