# Parci - Java File Organizer Desktop Application

Parci is a Java desktop application designed to organize files based on an ID number and place them in their respective city categories.

![image](https://github.com/breckenridmj/parci-java-file-organizer/assets/59925642/4277132c-993f-4a38-84a9-ed055c93eda4)

![image](https://github.com/breckenridmj/parci-java-file-organizer/assets/59925642/da87c458-ae03-41df-acaf-399b90c8bcd9)

## Requirements

- **Intellij:** IDE used to develop this program and convert the .java file into a .jar file. (Link: https://www.jetbrains.com)
- **Launch4j:** Used to convert the .jar file into a .exe executable. (Link: https://launch4j.sourceforge.net)
- **Inoo:** Used to create the .exe installer/setup. (Link: https://jrsoftware.org/isdl.php)
- **Convertio:** Used to convert image file into a .ico file. (Link: https://convertio.co)
- **JDK Developer Kit** Used for Java Runtime Environment. (Link: https://www.oracle.com/java/technologies/downloads/#jdk21-windows)

## How it Works

Parci reads files from a specified directory and organizes them based on an ID number found in the file names. It then categorizes these files into folders corresponding to their respective cities.

## Features

- **ID-based File Organization:** Parci extracts ID numbers from file names and uses them to categorize files.
- **City-based Categorization:** Files are placed in folders according to their associated city.
- **Desktop Application:** Parci provides a user-friendly interface for file organization tasks.

## Files

The program follows a file-naming convention: XXXXXXXX_XXXXX_XXX_XXX, where the files are sorted accordingly. It's configured to extract the second entry, and the user inputs the city corresponding to the ID.

## Usage

1. **Download and Install:**
   - Clone this repository to your local machine.
   - Compile and run the Java program.
   
2. **Set Up:**
   - Specify the directory containing the files to be organized.
   - Choose the destination directory for the organized files.
     
3. **Input the Sort Entries:**
   - By pressing the manage button input the entries manually or by csv.
   - Also by default, the program skips the first line in the csv which is usually the header
  
4. **Select File Type:**
   - Specify the file type that you want to sort (ex. .doc, .pdf, .txt, etc..)
  
3. **Run the Program:**
   - Click on the "Sort" button to initiate the organization process.

4. **Review Results:**
   - Check the destination directory for the organized files, categorized by city.

## Requirements

- Java Development Kit (JDK) 21 or higher

## Contributing

Contributions are welcome! If you have ideas for improvements, please create an issue or submit a pull request.
