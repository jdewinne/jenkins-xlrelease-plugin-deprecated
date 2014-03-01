package com.xebialabs.xlrelease.plugin.jenkins;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import hudson.cli.CLI;

public class JenkinsBuildInvocation {
    private static final String CLI_BUILD_COMMAND = "build";

    private final String url;
    private final String keyFile;
    private final String password;
    private final String jobName;
    private final Properties parameters;

    public JenkinsBuildInvocation(String url, String keyFile, String password, String jobName, Properties parameters) {
        this.url = url;
        this.keyFile = keyFile;
        this.password = password;
        this.jobName = jobName;
        this.parameters = parameters;
    }

    public int run() {
        try {
            CLI cli = new CLI(new URL(url));
            try {
                if (keyFile != null) {
                    KeyPair key;
                    if (password != null) {
                        key = CLI.loadKey(new File(keyFile), password);
                    } else {
                        key = CLI.loadKey(new File(keyFile));
                    }
                    cli.authenticate(key);
                }
                return cli.execute(getArgs(), System.in, System.out, System.err);
            } catch (IOException | GeneralSecurityException e) {
                System.err.println("Error invoking Jenkins build: " + e.getMessage());
                e.printStackTrace();
                return 1;
            } finally {
                try {
                    cli.close();
                } catch (IOException | InterruptedException ignored) {}
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error creating Jenkins CLI: " + e.getMessage());
            e.printStackTrace();
            return 2;
        }
    }

    private List<String> getArgs() {
        /*
         * build JOB [-c] [-f] [-p] [-r N] [-s] [-v] [-w]
         *
         * JOB    : Name of the job to build
         * -c     : Check for SCM changes before starting the build, and if there's
         *          no change, exit without doing a build
         * -p     : Specify the build parameters in the key=value format.
         * -r VAL : Number of times to retry reading of the output log if it does not
         *          exists on first attempt. Defaults to 0. Use with -v.
         * -s     : Wait until the completion/abortion of the command
         * -v     : Prints out the console output of the build. Use with -s
         * -w     : Wait until the start of the command
         */
        List<String> arguments = new ArrayList<>();
        arguments.add(CLI_BUILD_COMMAND);
        arguments.add(jobName);
        for(String key : parameters.stringPropertyNames()) {
            arguments.add("-p");
            arguments.add(String.format("%s=%s", key, parameters.getProperty(key)));
        }
        arguments.add("-s");
        arguments.add("-v");
        arguments.add("-w");
        return arguments;
    }

    public String getUrl() {
        return url;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public String getPassword() {
        return password;
    }

    public String getJobName() {
        return jobName;
    }
}
