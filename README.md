# Timer App with Process Management

## Project Overview

This Java-based console application provides functionality to monitor and manage running processes on a computer. It includes a timer feature that can terminate specified processes after a set countdown. The application is designed to enhance productivity by enabling process blocking and notifications.

## Features

1. **Timer-based Process Termination**:
   - Set a countdown (0-120 minutes) to terminate a specified process
   - Play audio reminders when nearing the end of the countdown
   - Pause, resume, or stop the countdown during execution

2. **Process Monitoring**:
   - Lists all currently running processes on startup
   - Dynamically checks and displays the status of processes
   - Cross-platform support (Windows, macOS, Linux)

3. **Blocklist Management**:
   - Automatically monitors and terminates processes on a predefined blocklist in the background
   - Default blocked applications: Steam, Battle.net
   - Runs as a daemon thread for continuous monitoring

4. **Notifications**:
   - Audio reminders to notify the user when a countdown is nearing completion
   - Customizable reminder intervals based on countdown duration:
     - 10-20 min: 1-minute warning
     - 20-30 min: 2-minute warning
     - 30-40 min: 3-minute warning
     - 40-50 min: 4-minute warning
     - 50+ min: 5-minute warning

## System Requirements

- **Java Development Kit (JDK) 23** or higher
- **Apache Maven** (for building)
- Supported operating systems:
  - Windows
  - macOS
  - Linux

## Dependencies

- Jackson Databind 2.18.2 (for JSON processing)
- JUnit Jupiter 5.11.4 (for testing)
- Mockito 5.15.2 (for testing)

## Installation

1. **Clone the Repository**:
```bash
   git clone <repository_url>
   cd Timer
```

2. **Build with Maven**:
```bash
   mvn clean package
```

   Or compile manually:
```bash
   javac -d bin src/main/java/com/github/Nifena/*.java
```

3. **Add Audio File**:
   - Place an audio file named `bell-ring.wav` in `src/main/Resources/`
   - The file must be in WAV format

## Usage

1. **Run the Application**:
```bash
   mvn exec:java -Dexec.mainClass="com.github.Nifena.Main"
```
   
   Or if compiled manually:
```bash
   java -cp bin com.github.Nifena.Main
```

2. **Follow the prompts**:
   - View the list of currently running processes
   - Enter the name of the process you want to terminate (without .exe on Windows)
   - Set a timer for the countdown in minutes (0-120)

3. **During countdown, you can**:
   - Type `s` and press Enter to **stop** the countdown
   - Type `p` and press Enter to **pause** the countdown
   - Type `c` and press Enter to **continue** the countdown

4. **Background monitoring**:
   - The blocklist runs automatically in the background
   - Blocked applications will be terminated immediately if detected

## Configuration

### Modifying the Blocklist

Edit the `Blocklist.java` file:
```java
private final Set<String> blockedApps = Set.of("Battlenet", "Steam", "YourApp");
```

Add or remove application names as needed (case-sensitive).

### Customizing Audio Notifications

1. Replace `bell-ring.wav` in `src/main/Resources/` with your desired `.wav` file
2. Update the file path in `MusicPlayer.java` if you change the filename:
```java
   player("src/main/Resources/your-sound.wav");
```

### Adjusting Reminder Intervals

Modify the `reminder()` method in `MusicPlayer.java` to customize when reminders play.

## Project Structure
```
Timer/
├── src/
│   ├── main/
│   │   ├── java/com/github/Nifena/
│   │   │   ├── Main.java
│   │   │   ├── Blocklist.java
│   │   │   ├── MusicPlayer.java
│   │   │   └── ProcessInfoExtractor.java
│   │   └── Resources/
│   │       └── bell-ring.wav
│   └── test/
│       └── java/com/github/Nifena/
│           └── MusicPlayerTest.java
├── pom.xml
└── README.md
```

## Testing

Run tests with Maven:
```bash
mvn test
```

## Troubleshooting

- **Process not found**: Ensure you enter the exact process name (without .exe extension on Windows)
- **Audio not playing**: Verify that `bell-ring.wav` exists in `src/main/Resources/` and is a valid WAV file
- **Permission errors**: Run with administrator/sudo privileges if needed to terminate system processes

## Future Enhancements

- Dynamic blocklist management via configuration file or GUI
- Improved pause/resume functionality with proper thread synchronization
- Integration with a database for process usage tracking and statistics
- GUI interface for easier interaction
- Custom notification sounds per process
- Process usage history and analytics
- Scheduled automatic blocking periods

## License

This project is for educational and experimental purposes only.
No license is specified — all rights reserved unless otherwise stated.
