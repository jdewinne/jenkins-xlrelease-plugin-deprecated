package com.xebialabs.xlrelease.plugins.ci.jenkins;


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

public class JenkinsBuildJob {

    private String url;
    private String keyFile;
    private String password;
    private String jobName;
    private Properties parameters;
    private static final String BUILD_COMMAND = "build";

    public JenkinsBuildJob(final String url, final String keyFile, final String password, final String jobName, final Properties parameters) {
        this.url = url;
        this.keyFile = keyFile;
        this.password = password;
        this.jobName = jobName;
        this.parameters = parameters;
    }

    public int runInJenkins() {

        try {
            CLI cli = new CLI(new URL(url));
            KeyPair key = CLI.loadKey(new File(keyFile), password);
            cli.authenticate(Collections.singleton(key));
            cli.upgrade();
            return cli.execute(getArgs(), System.in, System.out, System.err);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return -1;

    }

    private List<String> getArgs() {
        // build JOB [-c] [-f] [-p] [-r N] [-s] [-v] [-w]
        List<String> arguments = new ArrayList<String>();
        arguments.add(BUILD_COMMAND);
        arguments.add(jobName);
        for(String key : parameters.stringPropertyNames())
        {
            arguments.add("-p");
            arguments.add(key+"="+parameters.getProperty(key));
        }
        arguments.add("-s");
        arguments.add("-v");
        arguments.add("-w");

        return arguments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(String keyFile) {
        this.keyFile = keyFile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(final String jobName) {
        this.jobName = jobName;
    }
}
