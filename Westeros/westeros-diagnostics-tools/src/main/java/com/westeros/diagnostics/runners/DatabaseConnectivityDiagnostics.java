package com.westeros.diagnostics.runners;


import com.westeros.diagnostics.services.contract.Diagnostics;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnectivityDiagnostics implements IDiagnose {

    private final DataSource dataSource;

    public DatabaseConnectivityDiagnostics(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getName() {
        return "Database Connectivity Diagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks if the application can connect to the database.";
    }

    @Override
    public Diagnostics run() {
        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try (Connection connection = dataSource.getConnection()) {

            boolean valid = connection.isValid(2);
            diagnostics.setSuccess(valid);

            if (!valid) {
                diagnostics.setErrorMessage("Database connection is not valid");
            }

        } catch (SQLException e) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage(
                    "Database connection failed: " + e.getMessage()
            );
        }

        return diagnostics;
    }
}
