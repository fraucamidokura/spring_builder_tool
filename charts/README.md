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

To be able to access to the server you would need to make a port-forward

````shell
 kubectl -n builder port-forward  service/local-builder-tool 8080:8080
````


to safely clean everything just remove the kind cluster

````shell
kind delete cluster --name builder 
````