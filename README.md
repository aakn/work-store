# work-store

### Application stack

1. Dropwizard
2. Lombok
3. Guice
4. Hystrix
5. Freemarker
6. Hibernate

**Test Dependencies**

* Spock (in Groovy)
* Dropwizard Testing

**Database**

* This app uses H2 as the database and is accessed via Hibernate. 
* H2 is an in-memory database with file persistence support.
* H2 can be replaced by any database that has a JDBC driver (ex. MySQL or Postgres) with minimal changes.
* I could've chosen to load from the said API on startup. But I've chosen to use a datastore here 
to allow the app to scale easily to larger data sets, without any major revamps. So, for a smaller 
data set I've chosen to go for H2.

**Batch Processing**

* The batch processor loads of the data from the given API synchronously.
* But it should be easy to move this to an asynchronous implementation using Quartz, where a job 
can be submitted to be executed immediately.

**UI**

* The UI is built using freemarker - a templating language and Bootstrap.
* The html pages are rendered with the required data first at the server, and the final rendered 
html page is sent to the browser. No ajax calls were used.
* Pagination hasn't been implemented in the UI, but the API's allow the user to configure the page 
number and page size.

### Running the tests

```bash
./gradlew test
```

### Running the application

#### Configuration

The config file can be found at manager/config/development.yaml

#### Starting
```bash
./gradlew run
```

#### Populating the data

The data can be populated using the batch processor API

```bash
curl -X POST "http://localhost:3000/api/works" -H 'content-type: application/json' -d '{
  "url": "http://take-home-test.herokuapp.com/api/v1/works.xml",
  "directory": "redbubble"
}'
```
_Both the directory and the url are parameterized_

**Note:** Seed data for the directory redbubble is populated (with the given API), if the app finds 
it missing on the app startup. If you try to populate again for the same directory, it'll throw an 
exception. If you want to disable seeding, it can be done by setting the seedDataConfiguration.enabled 
to false in the yaml file. 

#### Accessing the UI

UI can be accessed at: http://localhost:3000/gallery/{directory}

For example: if you want to access the `redbubble` directory (seeded by default), 
then the URL will be http://localhost:3000/gallery/redbubble
 
#### API documentation

API documentations (Swagger Docs) can be accessed at http://localhost:3000/apidocs/ . Here the 
namespace is nothing but the directory.

#### Cleaning the data

In case data needs to be cleaned (let's say due to the unique constraint on external id and 
directory), this can be done by deleting the file in the `manager/db` folder. Thing to note here is, 
if seeding is enabled then the data under the redbubble directory will be populated again on startup. 

### Intellij Setup

This project uses Lombok. Lombok Requires Annotation Processing.
For the plugin to function correctly, please enable it under
`"Settings > Build > Compiler > Annotation Processors"`
