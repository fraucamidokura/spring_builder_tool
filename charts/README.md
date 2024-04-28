Please to run the chart we have used kind.

Run this on the root of the project
````shell
kind create cluster --name builder 
helm upgrade --install builder ./charts --create-namespace --namespace builder --wait --atomic 
````

you can also run it with gradle

````shell
gradlew bootRunInCluster
````

The server is accessible by http://localhost:8080

to safely clean everything just remove the kind cluster

````shell
kind delete cluster --name builder 
````