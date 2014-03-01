import sys
from java.util import Properties
from java.io import StringReader
from com.xebialabs.xlrelease.plugins.ci.jenkins import JenkinsBuildInvocation

print "About to run Jenkins build for job '%s'\n" % (jobName)
print "Build parameters: '%s'\n" % (parameters)
props = Properties()
props.load(StringReader(parameters))

build = JenkinsBuildInvocation(jenkinsUrl, keyFile, password, jobName, props)
exitCode = build.run()
print "Build exit code is: %s\n" % (exitCode)

sys.exit(exitCode)
