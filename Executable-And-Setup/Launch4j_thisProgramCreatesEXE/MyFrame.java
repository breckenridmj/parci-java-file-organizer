import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.nio.file.StandardCopyOption;

// useful layout help
// https://www.youtube.com/watch?app=desktop&v=8pEXo1oVWvU
// https://www.youtube.com/watch?v=Kmgo00avvEw

public class MyFrame extends JFrame {

    // global variables
    JTextField pathText;
    JTextField outputText;
    JButton pathButton;
    JButton outputButton;
    ImageIcon image;
    JTextArea logArea;
    JComboBox<String> fileSelector;

    // Hash Map that holds target number and city
    private Map<Integer, NumberStringPair> numberCityMap = new HashMap<>();


    MyFrame(){

        // Create JFrameGUI
        this.setTitle("Parci                                                            v1.0-beta release"); //set the Title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of app
        this.setResizable(false); //prevent frame resizing
        this.setSize(520,435); //sets frame dimensions
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        image = new ImageIcon("./images/parcy.png"); //create image icon
        this.setIconImage(image.getImage()); //change icon of frame

        // Create Different Panels
        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(new Color(123, 50, 250));
        bannerPanel.setBounds(0,0,520,80);
        bannerPanel.setLayout(null);
        this.add(bannerPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(123, 50, 250));
        controlPanel.setBounds(0,80,170,290);
        controlPanel.setLayout(null);
        this.add(controlPanel);

        JPanel logPanel = new JPanel();
        logPanel.setBackground(new Color(123, 50, 250));
        logPanel.setBounds(100,50,420,320);
        logPanel.setLayout(null);
        this.add(logPanel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(123, 50, 250));
        footerPanel.setBounds(0,370,520,35);
        footerPanel.setLayout(null);
        this.add(footerPanel);

        // Add footer Text
        JLabel credits = new JLabel();
        credits.setText("© 2024 Malcolm Breckenridge. All rights reserved.");

        Font labelFont = new Font("Arial", Font.BOLD, 8);
        credits.setForeground(Color.WHITE);
        credits.setFont(labelFont);
        credits.setBounds(170,0,510,20);
        footerPanel.add(credits);

        // Console log Text Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(231,231,231));
        Font f = new Font("Arial", Font.BOLD, 12);
        logArea.setFont(f);
        logArea.append("Console Log:\n");
        logArea.setBounds(0,0,335,270);

        // Create JScrollPanel over logpanel
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(70,40,330,270);
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        scrollPane.setBorder(border);
        logPanel.add(scrollPane);
        logArea.setCaretPosition(0);  // Sets horizontal scroll bar to the left position

        // Button to manage target inputs
        JButton manageButton = new JButton("MANAGE");
        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageTargetNumbers(numberCityMap);

            }
        });
        manageButton.setBounds(25,50,120,25);
        manageButton.setBorderPainted(true);
        manageButton.setBorder(border);
        controlPanel.add(manageButton);

        // Button to start file search
        JButton sortButton = new JButton("SORT");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortFiles(numberCityMap);
            }
        });
        sortButton.setBounds(25,10,120,25);
        sortButton.setBorderPainted(true);
        sortButton.setBorder(border);
        controlPanel.add(sortButton);

        // Button to select sort directory
        pathButton = new JButton("SOURCE");
        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPath();
            }
        });
        pathButton.setBounds(25,5,120,25);
        pathButton.setBorderPainted(true);
        pathButton.setBorder(border);
        bannerPanel.add(pathButton);

        // Button to select output directory
        outputButton = new JButton("OUTPUT");
        outputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputPath();
            }
        });
        outputButton.setBounds(25,40,120,25);
        outputButton.setBorderPainted(true);
        outputButton.setBorder(border);
        bannerPanel.add(outputButton);

        // Search Directory Text Field
        pathText = new JTextField();
        pathText.setBounds(170, 5, 330, 25);
        pathText.setBackground(new Color(231,231,231));
        pathText.setBorder(border);
        bannerPanel.add(pathText);

        // Output Directory Text Field
        outputText = new JTextField();
        outputText.setBounds(170, 40, 330, 25);
        outputText.setBackground(new Color(231,231,231));
        outputText.setBorder(border);
        bannerPanel.add(outputText);

        // Dropdown Box (JComboBox)
        String[] type = {".pdf", ".doc", ".docx", ".rtf", ".wpd", ".txt", ".csv", ".xml", ".xls", ".xlsx"};
        fileSelector = new JComboBox<>(type);
        fileSelector.setEditable(false);
        fileSelector.setBackground(new Color(231,231,231));
        fileSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fileSelector.getSelectedItem();
                System.out.println("File type changed to: " + fileSelector.getSelectedItem());
                logArea.append("File extension changed to:  " + "\"" + fileSelector.getSelectedItem() +"\" \n");
            }
        });
        fileSelector.setBounds(25,90,120,25);
        //fileSelector.setBorderPainted(true);
        //fileSelector.setBorder(border);
        controlPanel.add(fileSelector);

    }

    // Print Hash Map Entries Function
    private void printHashMapValues(Map<Integer, NumberStringPair> map) {
        for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()) {
            int key = entry.getKey();
            NumberStringPair pair = entry.getValue();
            int intValue = pair.getNumber();
            String stringValue = pair.getCity();
            System.out.println("Key: " + key + ", IntValue: " + intValue + ", StringValue: " + stringValue);
        }
    }

    // Source Path Function
    private String selectPath() {
        // Create a JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Set the file chooser to select directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the dialog to select a directory
        int result = fileChooser.showOpenDialog(this);

        String path;

        // Check if a directory was selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected directory
            File selectedDirectory = fileChooser.getSelectedFile();
            logArea.append("Selected source directory: " + selectedDirectory.getAbsolutePath() + "\n");
            System.out.println("Selected source directory: " + selectedDirectory.getAbsolutePath());

            // Set the selected directory path to the pathText field
            path = selectedDirectory.getAbsolutePath();
            if (pathText != null) { // Assuming pathText is defined somewhere accessible
                // Customize Text
                Font f = new Font("Arial", Font.BOLD, 12);
                pathText.setFont(f);
                pathText.setText(path);
            }
            return path;
        } else {
            // Handle case where no directory is selected
            System.out.println("No Source Directory Selected!");
            return null;
        }
    }

    // Output Path Function
    private String outputPath(){
        // Create a JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Set the file chooser to select directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the dialog to select a directory
        int result = fileChooser.showOpenDialog(this);

        String path;

        // Check if a directory was selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected directory
            File selectedDirectory = fileChooser.getSelectedFile();
            logArea.append("Selected output directory: " + selectedDirectory.getAbsolutePath() + "\n");
            System.out.println("Selected output directory: " + selectedDirectory.getAbsolutePath());

            // Set the selected directory path to the pathText field
            path = selectedDirectory.getAbsolutePath();
            if (outputText != null) { // Assuming pathText is defined somewhere accessible
                // Customize Text
                Font f = new Font("Arial", Font.BOLD, 12);
                outputText.setFont(f);
                outputText.setText(path);
            }
            return path;
        } else {
            // Handle case where no directory is selected
            System.out.println("No Output Directory Selected!");
            return null;
        }
    }

    // Manage Inputs Window Function
    private void manageTargetNumbers(Map<Integer, NumberStringPair> numberCityMap) {

        //variables
        JTextArea inputArea;

        // Hash Map that holds target number and city
        //Map<Integer, NumberStringPair> numberCityMap = new HashMap<>();

        //Create JFrame
        JFrame manageFrame = new JFrame("Manage Search");
        manageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manageFrame.setSize(300, 400); //default 300, 200
        manageFrame.setResizable(false); //prevent frame resizing
        manageFrame.setLayout(new BorderLayout());
        manageFrame.setLocationRelativeTo(null);
        manageFrame.setVisible(true);

        //Imaga Icon
        image = new ImageIcon("./images/parcy.png"); //create image icon
        manageFrame.setIconImage(image.getImage()); //change icon of frame

        // TextField
        inputArea = new JTextArea();
        inputArea.setEditable(false);
        inputArea.setBackground(new Color(231,231,231));
        Font f = new Font("Arial", Font.BOLD, 12);
        inputArea.setFont(f);
        inputArea.append("Number      City\n");

        // Add entries to the HashMap
        // numberCityMap.put(1, new NumberStringPair(7884, "New York"));
        // numberCityMap.put(2, new NumberStringPair(6777, "London"));
        // numberCityMap.put(3, new NumberStringPair(4343, "Paris"));
        // numberCityMap.put(4, new NumberStringPair(9090,  "Tokyo"));
        // numberCityMap.put(5, new NumberStringPair(1670, "Sydney"));

        // Display HashMap Values in TextField
        for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()){
            NumberStringPair pair = entry.getValue();
            inputArea.append(pair.getNumber() + "             " + pair.getCity() + "\n");
        }

        // Button Panel
        JPanel managebuttonPanel = new JPanel(new GridLayout(4, 1));
        managebuttonPanel.setBackground(Color.WHITE);

        // Add Item Button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // Create a JFrame that has an input for number and city
                JFrame inputFrame = new JFrame();
                inputFrame.setTitle("Add");
                inputFrame.setSize(300,200);
                // inputFrame.setLocationRelativeTo(null);
                inputFrame.setResizable(false);

                // Sets the Frame Location
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int frameWidth = 300; // Adjust this as needed
                // int frameHeight = 200; // Adjust this as needed
                int x = screenSize.width - frameWidth;
                inputFrame.setLocation(x -210, 320);
                inputFrame.setVisible(true);

                //Imaga Icon
                image = new ImageIcon("./images/parcy.png"); //create image icon
                inputFrame.setIconImage(image.getImage()); //change icon of frame

                // Create Panel that holds the input text fields
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(null);

                // Create Text Fields
                JTextField numText = new JTextField();
                JTextField cityText = new JTextField();

                // Create JLabels
                JLabel numLabel = new JLabel("Identifier:");
                JLabel cityLabel = new JLabel("City:");

                // Set bounds for JLabels
                numLabel.setBounds(20,23,60,40);
                cityLabel.setBounds(50,63,60,40);

                // Set bounds for Text Fields
                numText.setBounds(90, 32, 150, 25);
                cityText.setBounds(90, 72, 150, 25);

                // Create Text Field Border Outline
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
                Border compounBorder = BorderFactory.createCompoundBorder(border, margin);
                numText.setBorder(compounBorder);
                cityText.setBorder(compounBorder);
                numText.setBackground(new Color(231,231,231));
                cityText.setBackground(new Color(231,231,231));

                // Add Labels and Text Fields to JPanel
                inputPanel.add(numLabel);
                inputPanel.add(numText);
                inputPanel.add(cityLabel);
                inputPanel.add(cityText);

                // Submit button
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){

                        // Get the String and integer input from the text field
                        String numInput = numText.getText();
                        String cityInput = cityText.getText();

                        // If input exists and input is not null then add input to the hash map
                        if (numInput != null && isInteger(numInput) && cityInput !=null && ! cityInput.isEmpty()) {
                            int newNumber = Integer.parseInt(numInput);
                            String newCity = cityInput;

                            // Create a new string pair to check against the NumberStringPair
                            NumberStringPair pairToAdd = new NumberStringPair(newNumber,newCity);

                            boolean pairExists = false;

                            // Check if the pairToAdd is in the Hashmap
                            for (NumberStringPair pair: numberCityMap.values()){
                                if (pair.equals(pairToAdd)){
                                    pairExists = true;
                                    JOptionPane.showMessageDialog(null, "Input Already Exists!");
                                    break;
                                }
                            }

                            if (!pairExists){

                                //generate a new key
                                int newKey = 1;
                                while (numberCityMap.containsKey(newKey)){
                                    newKey++;
                                }

                                numberCityMap.put(newKey, pairToAdd);
                                inputArea.append(newNumber + "            " + newCity + "\n");
                                inputFrame.dispose(); // close input frame
                                JOptionPane.showMessageDialog(null, "New Entry Added!");
                                System.out.println("Entry Added: " + newNumber + "    " + newCity);
                                logArea.append("Entry Added: " + newNumber + "    " + newCity + "\n");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Entry!");
                        }
                    }
                });

                // Add Text Panel and Button Panel to frame
                inputFrame.add(inputPanel, BorderLayout.CENTER);
                //submitButton.setPreferredSize(new Dimension(100, 50));
                inputFrame.add(submitButton, BorderLayout.SOUTH);

            }
        });

        // Remove Item Button
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // Create a JFrame that has an input for number and city
                JFrame removeFrame = new JFrame();
                removeFrame.setTitle("Remove");
                removeFrame.setSize(300,200);
                // removeFrame.setLocationRelativeTo(null);
                removeFrame.setResizable(false);
                removeFrame.setVisible(true);

                // Sets the Frame Location
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int frameWidth = 300; // Adjust this as needed
                // int frameHeight = 200; // Adjust this as needed
                int x = screenSize.width - frameWidth;
                removeFrame.setLocation(x -210, 320);
                removeFrame.setVisible(true);

                //Imaga Icon
                image = new ImageIcon("./images/parcy.png"); //create image icon
                removeFrame.setIconImage(image.getImage()); //change icon of frame

                // Create Panel that holds the input text fields
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(null);

                // Create Text Fields
                JTextField numText = new JTextField();

                // Create JLabels
                JLabel numLabel = new JLabel("Identifier:");

                // Set bounds for JLabels
                numLabel.setBounds(20,46,60,40);

                // Set bounds for Text Fields
                numText.setBounds(90, 54, 150, 25);

                // Create Text Field Border Outline
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
                Border compounBorder = BorderFactory.createCompoundBorder(border, margin);
                numText.setBorder(compounBorder);
                numText.setBackground(new Color(231,231,231));

                // Add Labels and Text Fields to JPanel
                inputPanel.add(numLabel);
                inputPanel.add(numText);

                // submit button
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){

                        // Get the String and integer input from the text field
                        String numInput = numText.getText();

                        // If input exists and input is not null then add input to the hash map
                        if (numInput != null && isInteger(numInput)) {

                            int newNumber = Integer.parseInt(numInput);
                            String newCity = "";

                            boolean numExists = false;
                            int keyToRemove = -1;

                            // Iterate over the map entries
                            for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()) {
                                // Check if the number matches 7884
                                if (entry.getValue().getNumber() == newNumber) {
                                    // Retrieve the key corresponding to the number
                                    keyToRemove = entry.getKey();
                                    numExists = true;
                                    break;
                                }
                            }

                            // get the city from the hash map that matches the number
                            // Iterate over the map entries to find the city corresponding to the new number
                            for (NumberStringPair pair : numberCityMap.values()) {
                                if (pair.getNumber() == newNumber) {
                                    newCity = pair.getCity();
                                    break;
                                }
                            }
                            // //Check
                            // System.out.println("The key to be removed is: " + keyToRemove);
                            // //print hash map in console
                            // for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()) {
                            //     int key = entry.getKey();
                            //     NumberStringPair pair = entry.getValue();
                            //     int intValue = pair.getNumber();
                            //     String stringValue = pair.getCity();
                            //     System.out.println("Key: " + key + ", IntValue: " + intValue + ", StringValue: " + stringValue);
                            // }

                            if (numExists){
                                numberCityMap.remove(keyToRemove);

                                // //print hash map in console
                                // for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()) {
                                //     int key = entry.getKey();
                                //     NumberStringPair pair = entry.getValue();
                                //     int intValue = pair.getNumber();
                                //     String stringValue = pair.getCity();
                                //     System.out.println("Key: " + key + ", IntValue: " + intValue + ", StringValue: " + stringValue);
                                // }

                                // Delete everything in the JTextField
                                inputArea.setText("");

                                // Display HashMap Values in TextField
                                for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()){
                                    NumberStringPair pair = entry.getValue();
                                    inputArea.append(pair.getNumber() + "            " + pair.getCity() + "\n");
                                }

                                removeFrame.dispose(); // close input frame
                                JOptionPane.showMessageDialog(null, "Entry with number " + newNumber + " removed!");
                                logArea.append("Entry Removed: " + newNumber + "    " + newCity + "\n");

                            } else {
                                JOptionPane.showMessageDialog(null, "Entry Not Found!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Entry!");
                        }

                    }});

                // Add Text Panel and Button Panel to frame
                removeFrame.add(inputPanel, BorderLayout.CENTER);
                //submitButton.setPreferredSize(new Dimension(100, 50));
                removeFrame.add(submitButton, BorderLayout.SOUTH);


            }
        });

        // Clear List Button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // Remove all key-value mappings from the HashMap
                numberCityMap.clear();
                // Delete everything in the JTextField and add header
                inputArea.setText("");
                inputArea.append("Number       City\n");
            }
        });

        // Import List Button
        JButton importButton = new JButton("Import");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // Hashmap values pre import
                System.out.println("Pre-Import hashmap: \n");
                printHashMapValues(numberCityMap);

                parseCSVFile();

                // Hashmap values after import
                System.out.println("Post-Import hashmap: \n");
                printHashMapValues(numberCityMap);

                // Delete everything in the JTextField and add header
                inputArea.setText("");
                inputArea.append("Number       City\n");

                // Display HashMap Values in TextField
                for (Map.Entry<Integer, NumberStringPair> entry : numberCityMap.entrySet()){
                    NumberStringPair pair = entry.getValue();
                    inputArea.append(pair.getNumber() + "            " + pair.getCity() + "\n");
                }
            }
        });

        // Scroll Panel
        JScrollPane scrollPane = new JScrollPane(inputArea);

        // Add buttons to Manage Panel
        managebuttonPanel.add(addButton);
        managebuttonPanel.add(removeButton);
        managebuttonPanel.add(clearButton);
        managebuttonPanel.add(importButton);

        // Add Panels to frame
        manageFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        manageFrame.getContentPane().add(managebuttonPanel, BorderLayout.SOUTH);

    }

    // CSV Parser
    private void parseCSVFile(){
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line = reader.readLine();

                // clear hash map list
                //numberCityMap.clear();

                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String city = parts[0].trim();
                        int number = Integer.parseInt(parts[1].trim());
                        numberCityMap.put(number, new NumberStringPair(number, city));
                        logArea.append("Entry Added: " + number + "    " + city + "\n");
                    } else {
                        System.err.println("Invalid line: " + line);
                        logArea.append("CSV ERROR: Invalid line: " + line + "\n");
                    }
                }
                System.out.println("CSV file parsed successfully.");
                logArea.append("CSV file parsed successfully. \n");
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error parsing CSV file: " + e.getMessage());
                logArea.append("CSV ERROR: Parsing file" + e.getMessage()+ "\n");
                e.printStackTrace();
            }
        }

    }

    // Sort Files Function
    private void sortFiles(Map<Integer, NumberStringPair> numberCityMap) {
        // Get the source path
        String source = pathText.getText();

        // Check if the input text for source dir is null or empty
        if (source != null && !source.isEmpty() ){
            System.out.println("This is the source path used in the sortFiles Function: " + source);
        } else {
            System.out.println("No source directory selected");
            logArea.append("WARNING: No source directory selected! \n");
        }

        // Get the Output path
        String output = outputText.getText();

        // Check if the input text for output dir is null or empty
        if (output != null && !output.isEmpty() ){
            System.out.println("This is the output path used in the sortFiles Function: " + output);
        } else {
            System.out.println("No output directory selected");
            logArea.append("WARNING: No output directory selected! \n");
        }

        // Get the file type
        String selection = (String) fileSelector.getSelectedItem();
        System.out.println(selection);

        // Check the size of the HashMap
        System.out.println("Number of entries in numberCityMap: " + numberCityMap.size());
        //logArea.append("Total number of entries to sort: " + numberCityMap.size() + "\n");

        //print hash map in console
        printHashMapValues(numberCityMap);

        // Create Folders Based on the list
        // Folders are name after the City Entries
        createFolder(output, numberCityMap);
        searchFilename(source,output,selection, numberCityMap);
        logArea.append("Total number of entries sorted: " + numberCityMap.size() + "\n");

        // Sort files based on City Entries and place them into their perspective folder
        // Grab filenames based on their number and place them into their City Categories
    }

    // Create Folders Function
    private void createFolder(String outputDirectory, Map<Integer, NumberStringPair> numberCityMap){

        // enhanced for loop creates folders and if it exists do nothing
        for (NumberStringPair pair: numberCityMap.values()){

            String city = pair.getCity();

            // folder path variable and creates folder object
            String folderPath = outputDirectory + File.separator + city;
            File folder = new File(folderPath);

            // Check if the folder does not exist
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    System.out.println("Folder created: " + folder.getAbsolutePath());
                    logArea.append("Folder created: " + folder.getAbsolutePath() + "\n");
                } else {
                    System.out.println("Failed to create folder " + folder.getAbsolutePath());
                    logArea.append("Failed to create folder " + folder.getAbsolutePath() + "\n");
                }
            } else {
                System.out.println("Folder already exists " + folder.getAbsolutePath());
                logArea.append("Folder already exists " + folder.getAbsolutePath() + "\n");
            }
        }
    }

    // Search Filename function
    private void searchFilename(String sourceDirectory, String outputDirectory, String fileType, Map<Integer, NumberStringPair> numberCityMap){

        //list all files in the directory and
        File directory = new File(sourceDirectory);
        File[] files = directory.listFiles();

        // Get all files in the current working directory
        // if the directory is not empty then iterate through the array
        if (files!= null){
            for(File file : files){
                // Check if the file is indeed a file and if it ends in .pdf
                if (file.isFile() && file.getName().endsWith(fileType)){
                    // Get filename
                    String filename = file.getName();

                    // Extract target number from the file name
                    int fileNumber = extractNumber(filename);

                    // Check if the number matches any of the target search numbers
                    for (NumberStringPair pair: numberCityMap.values()) {

                        Integer num = pair.getNumber();
                        String city = pair.getCity();
                        // if the filenumber matches the target number
                        // move the file into the corresponding directory
                        if (fileNumber == num) {
                            try {
                                Path sourcePath = Paths.get(sourceDirectory, filename);
                                Path destinationPath = Paths.get(outputDirectory + File.separator + String.valueOf(city), filename);
                                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("File: " + filename + " copied successfully from " + sourcePath + " to " + destinationPath);
                                logArea.append("File: " + filename + " copied successfully from " + sourcePath + " to " + destinationPath + "\n");
                            } catch (IOException e) {
                                System.err.println("Error copying file: " + e.getMessage());
                                logArea.append("SORT ERROR: Copy Failed" + e.getMessage() + "\n");
                                e.printStackTrace();
                            }

                        }
                    }
                }
                //System.out.println(file.getName());
            }
        }
    }

    // Extract the Number Identifier from the filename Function (Ex: 3453453_45343_GDF_Glkdfs)
    private static int extractNumber(String fileName) {
        // Extracts the number from the filename
        // Uses regular expression to match the number between underscores
        // Returns the parsed integer value
        String[] parts = fileName.split("_");
        if (parts.length >= 2) {
            String numberStr = parts[1];
            return Integer.parseInt(numberStr);
        } else {
            // Handle the case where the filename format is not as expected
            throw new IllegalArgumentException("Invalid filename format: " + fileName);
        }
    }

    // Helper method to check if a string is an integer
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    class NumberStringPair {
        private int number;
        private String city;

        public NumberStringPair(int number, String city) {
            this.number = number;
            this.city = city;
        }

        public int getNumber() {
            return number;
        }

        public String getCity() {
            return city;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            NumberStringPair other = (NumberStringPair) obj;
            return number == other.number && city.equals(other.city);
        }

        @Override
        public int hashCode() {
            return 31 * number + city.hashCode();
        }

        @Override
        public String toString() {
            return "Number: " + number + ", City: " + city;
        }
    }

    public static void main(String[] args) {
        // Schedule a task to be executed on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            // Implement the run() method of the Runnable interface
            public void run() {
                // Create an instance of the FileSearchGUI class
                MyFrame myFrame = new MyFrame();
                // Make the GUI visible
                myFrame.setVisible(true); // make frame visible
            }
        });
    }

}