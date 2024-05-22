# spring_builder_tool
Use gradle to control all the CI CD and help developer bootstraping

## Sample project

We will build a sample project and will grow with the tool.

We will show how to add CI and CD to springboot. And ensure each PR is save and we do generate the docker image on each merge to main.

We want to run in local as we will run in production and therefore we will add task to deploy a helm chart into kind project.

We also want to run the end to end tests in this local enviroment.

We will also compare spring boot integration tests with the kind end to end tests.