package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;

public class DiskSpaceDiagnostics implements IDiagnose {

    private static final long DEFAULT_THRESHOLD_BYTES = 10L * 1024 * 1024 * 1024;

    private final long thresholdBytes;

    public DiskSpaceDiagnostics() {
        this.thresholdBytes = DEFAULT_THRESHOLD_BYTES;
    }

    public DiskSpaceDiagnostics(long thresholdBytes) {
        this.thresholdBytes = thresholdBytes;
    }

    @Override
    public String getName() {
        return "Disk Space Diagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks if the available disk space is above a specified threshold.";
    }

    @Override
    public Diagnostics run() {
        Path cwd = Paths.get(System.getProperty("user.dir", "."));
        File file = cwd.toFile();

        long usable = file.getUsableSpace();
        boolean healthy = usable >= thresholdBytes;

        String message = MessageFormat.format(
                "Path={0}, usable={1} bytes ({2} GiB), threshold={3} bytes ({4} GiB)",
                cwd.toAbsolutePath(),
                usable,
                String.format("%.2f", usable / (1024.0 * 1024 * 1024)),
                thresholdBytes,
                String.format("%.2f", thresholdBytes / (1024.0 * 1024 * 1024))
        );

        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setSuccess(healthy);
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        if (!healthy) {
            diagnostics.setErrorMessage(message);
        }

        System.out.println(new Date() + " - " + message);

        return diagnostics;
    }
}
