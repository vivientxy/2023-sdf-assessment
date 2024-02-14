## 2023 SDF Assessment - Task 02

To compile the code:
```
javac --source-path src -d classes src/WordDistribution/*
```

To run the code:
```
java -cp classes WordDistribution.App <directory_name>
```
```
java -cp classes WordDistribution.App frost.zip
```

## Description

This code takes in a directory name in the command line argument, and creates a folder named "texts", from which it will read text files.

It will then analyze the text file, and output a word distribution probability (i.e. it will print out each word, any possible next words, as well as the probability of the next word appearing)

## Java Version

This project runs on Java 21.