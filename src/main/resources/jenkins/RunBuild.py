import sys
from java.util import Properties
from java.io import StringReader
from com.xebialabs.xlrelease.plugins.ci.jenkins import JenkinsBuildInvocation

print "Going to build jenkins job %s \n" %jobName
print "with parameters: %s. \n" %parameters
props = Properties()
props.load(StringReader(parameters))

buildJob = JenkinsBuildInvocation(jenkinsUrl, keyFile, password,jobName,props)
result = buildJob.run()
print "Build result is %s. \n" %result

sys.exit(result)
