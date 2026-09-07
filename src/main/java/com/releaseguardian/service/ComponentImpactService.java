package com.releaseguardian.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentImpactService {

    public List<String> identifyImpactedComponents(
            List<String> changedFiles) {

        List<String> components = new ArrayList<>();

        for (String file : changedFiles) {

            String normalizedPath = file.toLowerCase();

            if (normalizedPath.contains("/payment/")
                    && !components.contains("PAYMENT")) {

                components.add("PAYMENT");
            }

            if (normalizedPath.contains("/auth/")
                    && !components.contains("AUTHENTICATION")) {

                components.add("AUTHENTICATION");
            }
        }

        return components;
    }
}