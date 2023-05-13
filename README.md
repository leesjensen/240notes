# All things Java

## History

- 1991: work on Java began as James Gosling.

    <img src="https://upload.wikimedia.org/wikipedia/commons/1/14/James_Gosling_2008.jpg" width="200" />

- 1996: Version 1 released.
- 2000: I started using Java with version 3.
- 2006: Sun starts (OpenJDK](http://openjdk.java.net/projects/jdk/) under a GPL license. OpenJDK become the reference implementation.
- 2010: Oracle acquires Sun and Java
- 2019 (JDK8) OracleJDK requires license for production deployment. OpenJDK gains steam.
- 2021 Oracle relents and makes it open again with option for paid support.
- 2023 Adoptium becomes the primary location to get OpenJDK

[History](https://www.marcobehler.com/guides/a-guide-to-java-versions-and-features)

## Java Goals

1. It must be simple, object-oriented, and familiar.
1. It must be robust and secure.
1. It must be architecture-neutral and portable.
1. It must execute with high performance.
1. It must be interpreted, threaded, and dynamic.

## Installations

Install the [zip file](https://adoptium.net/) and add it to your path. Adoptium is considered the best place to go.

## Versions

- **8**:
  - Lambdas
    ```java
    Runnable runnable = () -> System.out.println("Hello world two!");
    ```
  - Function style collections
    ```java
    List<String> list = Arrays.asList("franz", "ferdinand", "fiel", "vom", "pferd");
    ```
  - Stream chaining
    ```java
    list.stream()
    .filter(name -> name.startsWith("f"))
    .map(String::toUpperCase)
    .sorted()
    .forEach(System.out::println);
    ```
- **9**: more stream and collection functions. HTTP client
- **13**: Unicode 12.1 support, new switch syntax, multiline string with `"""` syntax
- **14**: Records
- **18**: UTF-8 is default. jwebserver HTTP server
