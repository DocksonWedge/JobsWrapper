# JobsWrapper
This is an example project with a simple api to show off automated REST testing in Kotlin. Using:
* **Production framework**
  * Ktor
  * Kotlinx
  * Maven
* **Test Framework**
  * Kotlin-test + JUnit5
  * use restassured for endpoint tests 
  * assertJ wanted for unit tests??
  
**To Add:**
* Create CLI tools
* Dockerize container and tests
* jacoco for code coverage
* Add Dependecy mocks
* Dependency injection(Koin? Guice?)
* Performance tests? (against mocks only)
* Setup test deploy pipeline
* output test results report for non-technical review

To run(needs environment variables):
mvn clean compile exec:java -Dexec.mainClass=app.MainKt

**To run(with all tests including endpoint that needs to be excluded):**
mvn clean compile exec:java -Dexec.mainClass=app.MainKt

**To build and package**
mvn clean install -DskipTests

**Starting local docker images, including mocks:**
docker-compose -f docker-compose.local.yml -f docker-compose.mock.yml up