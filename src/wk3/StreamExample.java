package wk3;/*
What is a Stream?
A Stream represents a sequence of elements (like a list, array, or collection).

Stream is an interface present in java.util.stream

============================================
JAVA STREAMS – WAYS TO CREATE STREAMS
===========================================

1) FROM A COLLECTION (MOST COMMON)

List<Integer> list = Arrays.asList(1, 2, 3);

Stream<Integer> s = list.stream();

Works with: List, Set, Collection
Best when data already exists in a collection

---------------------------------------------

2) FROM VALUES (Stream.of)

Stream<Integer> s = Stream.of(1, 2, 3);

Used for direct values
Good for small data or testing

--------------------------------------------

3) FROM ARRAYS (Arrays.stream)

int[] arr = {1, 2, 3};

IntStream s = Arrays.stream(arr);

Works with arrays
Better performance for primitive arrays

---------------------------------------------

4) USING STREAM BUILDER

Stream<String> s = Stream.<String>builder()
    .add("A")
    .add("B")
    .add("C")
    .build();

Useful when building stream step-by-step

----------------------------------------------

5) USING generate() (INFINITE STREAM)

Stream<Integer> s = Stream.generate(() -> 1);

Example with limit:
Stream.generate(() -> 1)
      .limit(5)
      .forEach(System.out::print);

Output:
11111

Creates infinite stream
Must use limit()

----------------------------------------

6) USING iterate() (SEQUENCE GENERATION)

Stream<Integer> s = Stream.iterate(1, n -> n + 1);

Example:
Stream.iterate(1, n -> n + 1)
      .limit(5)
      .forEach(System.out::print);

Output:
12345

Generates sequence
Useful for patterns and series

----------------------------------------

7) FROM FILES (I/O STREAMS)

Stream<String> lines = Files.lines(Path.of("file.txt"));

Reads file line by line
Used in file processing

----------------------------------------

8) EMPTY STREAM

Stream<String> empty = Stream.empty();

Used to avoid null checks
Represents no data

----------------------------------------

========================
SUMMARY TABLE
========================

list.stream()        → from Collection
Stream.of()          → from values
Arrays.stream()      → from array
Stream.builder()     → manual building
Stream.generate()    → infinite values
Stream.iterate()     → sequence generation
Files.lines()        → file input
Stream.empty()       → empty stream

========================
KEY IDEA
========================

Streams can be created from:
- collections
- arrays
- direct values
- builders
- infinite generators
- files
- empty source

Streams in Java can be created from multiple sources like collections, arrays, direct values, generators, and files depending on how the data is provided.

Streams allow functional-style operations on data: map, filter, reduce, sort, etc.
Streams do not store data — they operate on existing collections or arrays.
Streams are not the same as I/O streams (used for files or network).

Where is it used?
To process collections, arrays, or sequences of data.
Makes filtering, mapping, sorting, and aggregating data easier.
Often used with lambdas for concise code.

Key Points
Streams do not change the original collection — they produce results.
Can chain multiple operations: .filter(...).map(...).sorted().forEach(...)
Always used with lambda expressions or method references for concise code.
Ideal for processing large collections in a readable, functional style.

Key operations in Streams
Type	Example	Description
filter	stream.filter(x -> x.startsWith("A"))	Keep only elements that match a condition
map	stream.map(String::toUpperCase)	Transform each element
forEach	stream.forEach(System.out::println)	Perform action on each element
sorted	stream.sorted()	Sort elements
collect	stream.collect(Collectors.toList()) Mutable Reduction-	Gather elements into a collection; mutate a single existing container
reduce	stream.reduce(0, (a,b) -> a+b)	Immutable Reduction - Combine elements into a single value; a+b creates a new integer everytime
Research for more
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample {

    public static void main(String[] args) {

        List<String> students = Arrays.asList("John", "Emma", "Liam", "James");

        // 1. Filter + forEach
        System.out.println("Names starting with J");
        students.stream()
                .filter(name -> name.startsWith("J"))
                .forEach(System.out::println);

        // 2. Map to uppercase + forEach
        System.out.println("Names in uppercase");
        students.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // 3. Sorted + collect 
        List<String> sortedStudents = students.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Sorted List");
        System.out.println(sortedStudents);

        // 4. Filter + sorted + collect 
        List<String> jStudentsSorted = students.stream()
                .filter(name -> name.startsWith("J"))
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Sorted List of names starting with J");
        System.out.println(jStudentsSorted);

        // Numbers example 
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum of given numbers");
        System.out.println(sum); // Output: 15
    }
}
