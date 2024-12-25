
# Timer App with Process Management

## Project Overview

This Java-based console application provides functionality to monitor and manage running processes on a computer. It includes a timer feature that can terminate specified processes after a set countdown. The application is designed to enhance productivity by enabling process blocking and notifications.

## Features

1.  **Timer-based Process Termination**:
    
    -   Set a countdown (up to 120 minutes) to terminate a specified process.
        
    -   Play audio reminders when nearing the end of the countdown.
        
2.  **Process Monitoring**:
    
    -   Lists all currently running processes.
        
    -   Dynamically checks and displays the status of processes.
        
3.  **Blocklist Management**:
    
    -   Automatically monitors and terminates processes on a predefined blocklist (e.g., Steam, Battlenet).
        
4.  **Notifications**:
    
    -   Audio reminders to notify the user when a countdown is nearing completion.
        
    -   Customizable reminders based on the duration of the countdown.
        

## How to Run

1.  **Clone the Repository**:
    
    ```
    git clone <repository_url>
    ```
    
2.  **Compile the Project**: Use your preferred IDE or run the following command in the terminal:
    
    ```
    javac -d bin src/com/github/Nifena/*.java
    ```
    
3.  **Run the Application**:
    
    ```
    java -cp bin com.github.Nifena.Main
    ```
    

## Dependencies

-   **Java 8 or higher**
    
-   **Audio Files**:
    
    -   Place an audio file named `bell-ring.wav` in the directory `src/main/Resources/`.
        

## Usage

1.  Run the application.
    
2.  View the list of currently running processes.
    
3.  Enter the name of the process you want to terminate.
    
4.  Set a timer for the countdown.
    
5.  Optionally, terminate the countdown early by entering `s` in the console.
    

## Configuration

### Blocklist

You can modify the predefined blocklist by editing the `Blocklist` class:

```
private final Set<String> blockedApps = Set.of("Battlenet", "Steam");
```

Replace or add application names as needed.

### Audio Notifications

To change the notification sound:

1.  Replace `bell-ring.wav` in `src/main/Resources/` with your desired `.wav` file.
    
2.  Ensure the file name matches the one in the `MusicPlayer` class.
    

## Future Enhancements
    
-   Dynamic blocklist management via a configuration file.
    
-   Timer pause and resume functionality.
        
-   Integration with a database for advanced process tracking.
    


