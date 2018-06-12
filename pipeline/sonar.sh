#!/bin/bash
echo "This is sonar.sh"

npm i sonar-scanner && chown -R 10000:10000 node_modules

./node_modules/sonar-scanner/bin/sonar-scanner -X \
	-Dsonar.login=admin \
	-Dsonar.password=admin \
	-Dsonar.host.url=http://democluster-team1-sonarqube-sonarqube.democluster-team1.svc.cluster.local:9000 \
	-Dsonar.projectKey=dandevops:demo \
	-Dsonar.projectName=demo \
	-Dsonar.projectVersion=1.0 \
	-Dsonar.sources=examples \
	-Dsonar.exclusions=examples/downloads/**

chown -R 10000:10000 .scannerwork


