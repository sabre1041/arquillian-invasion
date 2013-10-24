arquillian-invasion
==============

Demonstration of the Arquillian Integration Testing Framework

###Project Overview
3 tier application to submit and view alien sightings with Arquillian Integration Tests at each tier

* Persistence
* Services
* UI

The following container types are utilized:

* JBoss EAP 6.1 Managed 
* JBoss EAP 6.1 Remote

The following extensions are utilized:

* Persistence
* Graphene

### Building the Project

The following describes how to build and run the various modes of Arquillian Integration test contained within the application

By default, Arquillian Integration testing is disabled and the project can be built by running the following:

    mvn clean install

### Deploying to JBoss
 
The application can be deployed to JBoss. After building the project, an Enterprise Archive is available. After the project is deployed successfully, it can be accessed at:
http://localhost:8080/arquillian-invasion-web 


### Running Arquillian Tests

To run Arquillian against a remote JBoss AS7/EAP6 container (running on local machine), run the following

    mvn clean install -P arquillian-remote

The Persistence project has been configured to run a JBoss AS7/EAP6 managed container. By default, Arquillian searches for the prescence of a $JBOSS_HOME environment variable.  If this variable is not defined, it can be configured in the *arquillian.xml* file. To run the tests in the persistence project in a managed container, run the following command

    mvn clean install -P arquillian-managed
    
The Persistence and Services projects can be tested in the cloud on OpenShift through the use of the openshift-express extension. Configure the *arquillian.xml* file with the appropriate configuration values and run the tests by running the following command:

    mvn clean install -P arquillian-openshift-express 
     
### Deployment to OpenShift
 
 The application can be hosted on Red Hat's OpenShift Platform as a Service (PaaS). It is designed to run on JBoss EAP 6 with a MySQL database.
 
 These instructions assume you have the necessary client tooling installed and configured on your local machine. Create the application, required cartridges, and pull in the sample code with the following command:
 
     rhc app create <app_name> jbosseap-6 mysql-5.1 
     cd <app_name>
     git remote add upstream -m master https://github.com/sabre1041/arquillian-invasion.git
     git pull -s -X theirs upstream master
     git push master
     
 The application should be available at the root of the application context