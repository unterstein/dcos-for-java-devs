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
- the java service depends on the database and consumes data
- We will get nice juck norris jokes if everything is running ;-)


# Get this demo working
- You need java, docker and docker-compose to be installed on your machine.
- Go to sources and run this command

```
./build.sh && ./run.sh
```

- Point a browser of your choice to your docker ip on port ```18080``` for http to view REST API.