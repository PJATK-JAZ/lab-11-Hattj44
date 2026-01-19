package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiagnosticsRunner implements IRunDiagnoses {

    private final List<IDiagnose> diagnoses;

    public DiagnosticsRunner(List<IDiagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    @Override
    public List<Diagnostics> runAll() {
            List<Diagnostics> results = new ArrayList<>();

            for (IDiagnose diagnose : diagnoses) {
                try {
                    results.add(diagnose.run());
                } catch (Exception e) {
                    Diagnostics failed = new Diagnostics();
                    failed.setName(diagnose.getName());
                    failed.setDescription(diagnose.getDescription());
                    failed.setSuccess(false);
                    failed.setErrorMessage("Diagnostics execution failed: " + e.getMessage());
                    results.add(failed);
                }
            }

            return results;
    }
}
