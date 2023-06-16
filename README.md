# Course links

- [Chess](https://github.com/BYUCS240TA/Chess)
  - [Example projects](https://github.com/BYUCS240TA/Chess/tree/main/example-chess-projects)
  - [Specification](https://github.com/BYUCS240TA/Chess/tree/main/specifications)

# Instruction

## IntelliJ

- Turn on format on save in Settings>Tools>Actions on Save
- Select multiple lines with cursors: `Option-Option-down/up arrow`
- Select same text multiple cursors: `Ctrl-G`

## Curl

Allocated time: 20 minutes
https://docs.google.com/presentation/d/1pM_tUVD7c6kWpHkEwuRpbWmoBFss3GuK

## Git/GitHub

Allocated time: 70 minutes

## MySQL

Allocated time: 45 minutes

## WebSocket

Allocated time: 70 minutes

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

1. You have four hours to complete the exam. There are three tries with decreasing max value (100, 80, 60). Save 30 minutes to upload your artifacts.
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
1. Just remember `File` and `Scanner` and that it users `hasNext` and `next` to iterate.
1. Make sure you lowercase the words passed to `suggestSimilarWord`.
1. All of the edit functions take a form of two substrings being combined in some way.
   ```java
     private void calculateAlteration(String inputWord, ArrayList<String> words) {
     for (var i = 0; i < inputWord.length(); i++) {
         for (var c : Trie.alphabet) {
             if (inputWord.charAt(i) != c) {
                 var p = inputWord.substring(0, i);
                 var s = inputWord.substring(i + 1);
                 words.add(String.format("%s%c%s", p, c, s));
             }
         }
     }
   }
   ```
1. Collect the matching results and return the one that has the highest value.
1. If nothing matches then run it again with the list from edit distance 1.

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

## Results

1. I implemented this three times. Once to figure it out. A second time to see if I could do it again. This time I took careful notes (above). A third time to time myself. Each time I created a totally different solution.
1. On the third time, without using my notes, I was able to complete this in 1:45 minutes.
1. I find this very nerve racking. It forces me to go as fast as possible without thinking if the architecture is good.
1. The tests are vital. Just let them guide your efforts.
1. The basic outline is:
   1. Create an empty project and copy in the interfaces, tests, jars, and test files.
   1. Implement main, and the stubbed classes. Set up the project to reference the jars.
   1. Implement reading spell checker file and simply returning the given word.
   1. Run the trie tests
   1. Implement Trie and Node find and add ability.
   1. Implement toString
   1. Implement hashCode and equals
   1. Run Spell tests
   1. Implement a class to track the candidate words, the generated words, the best current match, and to generate candidates using insertion, deletion, alteration, and transposition.
   1. Use the class for edit distance one, with the input word as the candidate.
   1. Use the class for edit distance two, with edit distance one's generated words and the candidates.

# Chess

## Codebase

`git clone https://github.com/BYUCS240TA/Chess.git`

### Top level

1. example-chess-projects - Project solutions
1. specifications - Specifications. This is what is given to the students
1. Example Chess Projects - Old stuff, maybe we can kill this now?
1. Specs - Old stuff, maybe we can kill this now?
1. proguard-7.3.2 - Used to obfuscate the test driver code

### Project level (specifications)

The chess project is divided into 6 different projects.

1. 1-chess-game - Basic board setup and moves. When the tests all pass the student is done.
1. 2-server-design - Stubbed implementation of service endpoints. Produce the JavaDocs to pass off this deliverable. It isn't clear to me from the documentation what endpoints or code structure is required. There is an API_Calls.txt file in the root that details the endpoints. Are we going to give them that information? The diagram seems to indicate that there is a required structure to the code (Server, Handlers, Services, Data Access, Models), but I'm not sure that is enough to actually produce what you are hoping for. Honestly, I'm not sure what I am supposed to produce for this.
1. 3-web-api - Actually implement the service. Lots of detail here about what is to be built. When the tests all pass the student is done.
1. 4-database - Implement the database. When the tests all pass the student is done.
1. 5-pre-game - Implement the client. When the tests all pass the student is done.
1. 6-game-play - Implement the game using WebSocket for communication. When the tests all pass the student is done.

## Database

It looks like the backend database is expected to be mysql and it is hard coded to look for it on the same server as the code is running on.

`localhost:3306/chess`

```mysql
CREATE DATABASE IF NOT EXISTS chess;
SHOW databases;
```

We need to change the root user and password found in `Database.java`.

    private static final String DB_USERNAME = "chessadmin";
    private static final String DB_PASSWORD = "monkeypie";

### Users

```mysql
SELECT user FROM mysql.user;
CREATE USER IF NOT EXISTS chessadmin IDENTIFIED BY 'monkeypie';
GRANT CREATE, SELECT, INSERT ON chess.* TO chessadmin;
```

## Building it for myself

### 1-chess-game

I followed the instructions `GettingStarted-ChessGame.docx` in order to get the project set up.

1. Create project
1. Create `main`, `starter`, and `test` under `src` dir. Mark `main` and `stater` as "Sources root", mark `test` as "Test root".
1. Copy over `chess` and `passoffTeests` folders from the `specification` provided code.
1. Added `org.junit.jupiter:junit-jupiter:5.9.2` as a dependency from Maven.
1. Reviewed `ChessGame.docx`. This basically tells the class structure and how to setup the tests. The rules of chess are also covered.

I then created a stub for the required interfaces (board, game, move, piece, and position) in a `chess` package.

I then starting with `BishopMoveTest`. After a bunch of boilerplate code and hashCode and equals functionality I finally got the tests to pass. You must make sure you check the color of the pieces.
