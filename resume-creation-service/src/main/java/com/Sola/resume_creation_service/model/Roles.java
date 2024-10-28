package com.Sola.resume_creation_service.model;

public enum Roles {
    ROLE_RESUMES,
    ROLE_ADMIN
}

// implements GrantedAuthority for Roles. SpringSecurity would be enabled!