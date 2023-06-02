# Course links

- [Chess](https://github.com/BYUCS240TA/Chess)
  - [Example projects](https://github.com/BYUCS240TA/Chess/tree/main/example-chess-projects)
  - [Specification](https://github.com/BYUCS240TA/Chess/tree/main/specifications)

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

I actually installed by installing IntelliJ and then having it install the JDK for me.

I then added the following to my `.zshrc` so that I could launch from the console.

```sh
alias idea='open -na "IntelliJ IDEA CE.app" --args '
```

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

## Configure IntelliJ

- Added `Reformat code` action on Save in the Tools settings

## Notes of things to fix.

- `Installing Java` video references Android Studio for IDE.
- `JavaDoc` references API 16. 17 was the last LTS, 20 is latest

## Building

To run from the console use:

```sh
java -classpath out/production/spell spell.Main notsobig.txt cow
```

To build a jar file from the output use:

```sh
java -classpath spell.jar spell.Main notsobig.txt cow
```

# Implementing SpellChecker

1. Download the two zip files
1. Create a project named SpellCorrector
1. Move the source code into `src/spell`
1. Move the tests into `src/passoff`
1. Move the jar files into `jar`
1. Add the jar files to the `project structure` > `project settings` > `module` > `dependencies`
1. Create Trie, SpellCorrector, and Node classes and implement the stub of their interfaces.
1. Implement Node
   1. Add child array and value properties
   1. Implement `INode` methods
1. Implement Trie
   1. Add `root` Node
1. Add default Trie `toString`, `hashCode`, and `equals` methods.
   1. `toString` must return a new line separated list of words.
   1. `hashCode` must not use any of the automatic hashing functions.
   1. `equals` must not use hashCode or toString
1. Add `SpellCorrector` to main class
1. Run `TrieTest` and fix until they all pass
   1. Most of the code is in Trie. These are just recursive functions for manipulating INode.
   1. I started with the generated functions for `toString`, `hashCode`, and `equals` and then tweaked them to work.
   1. I did implement `equals` on Node and then just compared the root nodes of Trie.
1. Run `SpellTest` and fix until they all pass
   1. Implement the `SpellCorrector.useDictionary` method using `File` and `Scanner`.
      ```java
      public void useDictionary(String dictionaryFileName) throws IOException {
          var trie = new Trie();
          try (Scanner scanner = new Scanner(new File(dictionaryFileName))) {
              while (scanner.hasNext()) {
                  String n = scanner.next();
                  trie.add(n.toLowerCase());
              }
          }
      }
      ```
1. Make sure you lowercase the words passed to `suggestSimilarWord`.

## Similarity definition

The following rules define how a word is determined to be most similar:

1. It has the “closest” edit distance from the input string
1. If multiple words have the same edit distance, the most similar word is the one with the closest edit distance that is found the most times in the dictionary
1. If multiple words are the same edit distance and have the same count/frequency, the most similar word is the one that is first alphabetically

## Edit distance

1. Deletion Distance: A string s has a deletion distance 1 from another string t if and only if t is equal to s with one character removed. The only strings that are a deletion distance of 1 from “bird” are “ird”, “brd”, “bid”, and “bir”. Note that if a string s has a deletion distance of 1 from another string t then |s| = |t| -1. Also, there are exactly |t| strings that are a deletion distance of 1 from t. The dictionary may contain 0 to n of the strings one deletion distance from t.
1. Transposition Distance: A string s has a transposition distance 1 from another string t if and only if t is equal to s with two adjacent characters transposed. The only strings that are a transposition Distance of 1 from “house” are “ohuse”, “huose”, “hosue” and “houes”. Note that if a string s has a transposition distance of 1 from another string t then |s| = |t|. Also, there are exactly |t| - 1 strings that are a transposition distance of 1 from t. The dictionary may contain 0 to n of the strings one transposition distance from t.
1. Alteration Distance: A string s has an alteration distance 1 from another string t if and only if t is equal to s with exactly one character in s replaced by a lowercase letter that is not equal to the original letter. The only strings that are an alternation distance of 1 from “top” are “aop”, “bop”, …, “zop”, “tap”, “tbp”, …, “tzp”, “toa”, “tob”, …, and “toz”. Note that if a string s has an alteration distance of 1 from another string t then |s| = |t|. Also, there are exactly 25\* |t| strings that are an alteration distance of 1 from t. The dictionary may contain 0 to n of the strings one alteration distance from t.
1. Insertion Distance: A string s has an insertion distance 1 from another string t if and only if t has a deletion distance of 1 from s. The only strings that are an insertion distance of 1 from “ask” are “aask”, “bask”, “cask”, … “zask”, “aask”, “absk”, “acsk”, … “azsk”, “asak”, “asbk”, “asck”, … “aszk”, “aska”, “askb”, “askc”, … “askz”. Note that if a string s has an insertion distance of 1 from another string t then |s| = |t|+1. Also, there are exactly 26\* (|t| + 1) strings that are an insertion distance of 1 from t. The dictionary may contain 0 to n of the strings one insertion distance from t.
