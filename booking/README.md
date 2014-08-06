Seam Booking Example
====================

This example demonstrates the migration of Seam Booking application to pure Java EE 6 environment.
Transaction and persistence context management is handled by the EJB container. This example runs on JBoss AS as an EAR.

Running the example
-------------------

### Using Maven

To deploy the example to a running JBoss AS instance, follow these steps:

1. In the example root directory run:

        mvn clean install

2. Set JBOSS_HOME environment property.

3. In the booking-ear directory run:

        mvn jboss-as:deploy

4. Open this URL in a web browser: http://localhost:8080/seam-booking


Testing the example
-------------------

This example is covered by integration and functional tests. All tests use the following technologies:

* __Arquillian__ -  as the framework for EE testing, for managing of container lifecycle and deployment of test archive,
* __ShrinkWrap__ - to create the test archive (WAR).


### Integration tests //// TODO rewrite ////

Integration tests cover core application logic and reside in the EJB module. In addition to Arquillian and ShrinkWrap, the integration tests also use:

* __JUnitSeamTest__ - to hook into the JSF lifecycle and assert server-side state,
* __ShrinkWrap Resolver__ - to resolve dependencies of the project for packaging in the test archive.

The tests are executed in Maven's test phase. By default they are skipped and can be executed on JBoss AS with:

    mvn clean test -Darquillian=jbossas-managed-7

The `JBOSS_HOME` environment variable must be set and point to a JBoss AS instance directory.

To test on a running server, use

    mvn clean test -Darquillian=jbossas-remote-7

#### Using Ant

In the example root directory run:

    ant clean test


### Functional tests

Functional tests are located in a separate project and are not executed during the build of the example. They test the built archive in an application server through browser-testing. They use:

* __Arquillian Graphene Extension__ - an advanced Ajax-capable type-safe Selenium-based browser testing tool,
* __Arquillian Drone Extension__ - to automatically run and stop browser instances.

_Note: It is necessary to first build and package the example, because the functional test references the built archive for automatic deployment to the server._

Run the functional test on JBoss AS instance with
    
    mvn -f booking-ftest/pom.xml clean test

The `JBOSS_HOME` environment variable must be set and point to a JBoss AS instance directory.

Several variables can be configured:

* path to an alternative archive for testing

        -DtestDeployment=/path/to/archive.ear

* the browser to use for testing

        -Dbrowser=htmlUnit

* test on a running server

        -Dremote

Testing in JBDS
---------------
### Integration tests

1. Open JBDS and start a configured instance of JBoss AS

2. Import the example project and its submodules

3. In the _Project Explorer_, select the EJB module project, then
    1. Type `Ctrl+Alt+P` (_Select Maven Profiles_) and check `integration-tests` and `arq-jbossas-7-remote`
    2. Right-click the module and select _Run As_ - _JUnit Test_

### Functional tests

It is not possible to run the functional tests of this example in JBDS, because they use the maven-dependency-plugin to copy test classes from a different maven artifact, which is not a configuration supported by JBDS.
