package com.xebialabs.xlrelease.plugins.ci.jenkins;

import java.util.Properties;

public class TestJenkinsBuildInvocation {

    public static void main(String[] args) throws Exception {
        String url = "http://192.172.1.10:8080/";
        String keyFile = "/home/jdewinne/.ssh/id_rsa_jenkins";
        String password = "jenkins";
        String jobName = "test xl release";

        JenkinsBuildInvocation buildJob = new JenkinsBuildInvocation(url, keyFile, password, jobName, new Properties());

        buildJob.run();
    }
}
