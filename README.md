# Abstract
## Deutsch
Containerorchestrierung mit Mesos – DC/OS für Java-Entwickler

In Zeiten von Docker, Big Data und Microservices wird es immer wichtiger, seine verteilte Anwendung sinnvoll auf sein Cluster zu verteilen und dabei trotzdem noch den Überblick zu behalten. Daher werden Plattformprodukte bzw. Cluster-Management-Systeme wie Kubernetes oder DC/OS immer wichtiger und halten in immer mehr Bereiche Einzug. In dieser Session werden wir einen Java-basierten Service implementieren und ihn mit einer Handvoll umgebenden Services zunächst mit Docker-Compose starten. Danach werden wir sehen, wie einfach es ist, die gleiche verteilte Anwendung in DC/OS auf dem eigenen Cluster zu deployen und zu monitoren. Danach werden wir einzelne Services unabhängig von der restlichen Anwendung skalieren und im laufenden Betrieb aktualisieren. Und das Coolste dabei: Wir können uns alle Vorgänge visualisieren lassen.



## English
Container orchestration on Mesos - DC/OS for java devs

In the era of docker, big data and microservices it is really important to distribute your applications reasonable across your cluster and keep a good overview of all of your applications. Because of this, cluster management software like DC/OS are very important. In this session we will implement a java based service and will start him with docker-compose first. Then we will deploy our service to DC/OS and see how easy it is to scale, upgrade, monitor and do other fancy stuff. And the best thing for the demo: We will have a nice UI to visualize.

# Talk

To see the presentation, just open the pdf file in the presentation folder or go to https://speakerdeck.com/unterstein/dcos-for-java-devs

# What is in this demo?
- One java service, one mysql database
- The java service depends on the database and consumes data
- We will get nice chuck norris jokes if everything is running ;-)


# Get this demo working locally
- You need java, docker and docker-compose to be installed on your machine.
- Go to sources and run this command

```
./build.sh && ./run.sh
```

- Point a browser of your choice to your docker ip on port ```18080``` for http to view REST API.

# Get this demo working on DC/OS
- You need a running DC/OS cluster to run this demo
- You have multiple options to spin up a DC/OS cluster
	- Go to https://dcos.io/ and to walk through the possibilities or browse the docs
	- Go to https://github.com/dcos/dcos-vagrant if you want to play around on your local box


# What was shown in demo #3
- DC/OS Dashboard
- Service UI: Simple sleep task, including constraints
- Show marathon-configuration.json
- DC/OS Universe, install `marathon-lb`
- CLI: `dcos marathon group add marathon-configuration.json`
- Look what happens in Service UI, show AppDefinition, logging, ...
- CLI: `dcos task log $taskId`
- CLI: `dcos marathon app update chuck-jocke/service instances=20`
- Service UI: Change `"SERVICE_VERSION":"4"` and see rolling update
- Toggle health check `curl -XPUT your.public.elb.amazonaws.com/health and see marathon will replace the task


