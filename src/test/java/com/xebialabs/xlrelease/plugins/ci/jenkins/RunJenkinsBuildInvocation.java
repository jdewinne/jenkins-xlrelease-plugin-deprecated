package com.xebialabs.xlrelease.plugins.ci.jenkins;

import java.util.Properties;

public class RunJenkinsBuildInvocation {

    public static void main(String[] args) throws Exception {
        String url = "http://server:port/";
        String keyFile = "/path/to/my/keyfile";
        String password = "password";
        String jobName = "my job name";

        JenkinsBuildInvocation build = new JenkinsBuildInvocation(url, keyFile, password, jobName, new Properties());

        build.run();
    }
}
