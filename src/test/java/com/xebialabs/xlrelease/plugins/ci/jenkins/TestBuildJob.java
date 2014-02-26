package com.xebialabs.xlrelease.plugins.ci.jenkins;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jdewinne
 * Date: 12/17/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestBuildJob {



    public static void main(String[] args) throws Exception {
        String url = "http://192.172.1.10:8080/";
        String keyFile = "/home/jdewinne/.ssh/id_rsa_jenkins";
        String password = "jenkins";
        String jobName = "test xl release";

        JenkinsBuildJob jenkinsBuildJob = new JenkinsBuildJob(url, keyFile, password, jobName, new Properties());

        jenkinsBuildJob.runInJenkins();
    }
}
