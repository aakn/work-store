# work-store

### Intellij Setup

This project uses Lombok. Lombok Requires Annotation Processing.
For the plugin to function correctly, please enable it under
`"Settings > Build > Compiler > Annotation Processors"`

### Libraries Used

1. Dropwizard
2. Lombok
3. Guice
4. Hystrix
5. Freemarker

**Test Libraries**

1. Spock (in Groovy)
2. Dropwizard testing



### How to start the application

```bash
./gradlew run
```

### How to run the tests

```bash
./gradlew test
```