import sys
from java.util import Properties
from java.io import StringReader
from com.xebialabs.xlrelease.jenkins import JenkinsBuildJob

print "Going to build jenkins job %s \n" %jobName
print "with parameters: %s. \n" %parameters
props = Properties()
props.load(StringReader(parameters))

buildJob = JenkinsBuildJob(jenkinsUrl, keyFile, password,jobName,props)
result = buildJob.runInJenkins()
print "Build result is %s. \n" %result

sys.exit(result)
