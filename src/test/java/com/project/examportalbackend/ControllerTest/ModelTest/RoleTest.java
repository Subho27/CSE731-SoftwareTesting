package com.project.examportalbackend.ControllerTest.ModelTest;

import com.project.examportalbackend.models.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void testToString() {
        // Create a Role object using the builder
        String roleName = "ADMIN";
        String roleDescription = "Administrator with full access";

        Role role = Role.builder()
                .roleName(roleName)
                .roleDescription(roleDescription)
                .build();

        // Get the string representation of the Role object
        String toStringResult = role.toString();

        // Assert that the toString includes the roleDescription
        assertTrue(toStringResult.contains("roleDescription=Administrator with full access"));
        assertTrue(toStringResult.contains("roleName=ADMIN"));
    }
}
