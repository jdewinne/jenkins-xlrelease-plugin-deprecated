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
            KeyPair key = CLI.loadKey(new File(keyFile), password);
            cli.authenticate(key);
            cli.upgrade();
            return cli.execute(getArgs(), System.in, System.out, System.err);
        } catch (IOException | InterruptedException | GeneralSecurityException e) {
            System.err.println("Error invoking Jenkins build: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    private List<String> getArgs() {
        // build JOB [-c] [-f] [-p] [-r N] [-s] [-v] [-w]
        List<String> arguments = new ArrayList<>();
        arguments.add(CLI_BUILD_COMMAND);
        arguments.add(jobName);
        for(String key : parameters.stringPropertyNames()) {
            arguments.add("-p");
            arguments.add(String.format("%s=%s", key, parameters.getProperty(key)));
        }
        // TODO: make configurable?
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
