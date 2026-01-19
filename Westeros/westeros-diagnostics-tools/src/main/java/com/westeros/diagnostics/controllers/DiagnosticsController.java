package com.westeros.diagnostics.controllers;

import com.westeros.diagnostics.runners.IRunDiagnoses;
import com.westeros.diagnostics.services.contract.Diagnostics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("diagnostics")
public class DiagnosticsController {
    private final IRunDiagnoses diagnosticsRunner;
    public DiagnosticsController(IRunDiagnoses diagnosticsRunner) {
        this.diagnosticsRunner = diagnosticsRunner;
    }

    @GetMapping
    public List<Diagnostics> runDiagnostics() {
        return diagnosticsRunner.runAll();
    }

    @GetMapping
    public ResponseEntity<String> checkStatus(){
        return ResponseEntity.ok("ALIVE");
    }
}
