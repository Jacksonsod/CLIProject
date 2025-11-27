# CLIProject
Command-line interface system built in Java with Oracle DB integration. Supports dynamic command management, authenticated user access, and secure client-server communication.

- All commands are executed on the server, not the client.
- Only authenticated users can connect and run commands.
- Commands are stored in an Oracle database, allowing dynamic updates without modifying Java code.
- The system supports multiple clients, session-based access control, and modular command dispatching.
# TEAM MEMBERS
  1. BIMENYIMANA Jackson
  2. UMUTONI Angella
  3. IMENA Kenny
  4. UWIZEYIMANA Laetitia
  5. TUYISENGE Sebarera Patrick
# TECHNOLOGIES 
1. Oracle database for data management
2. Java for developing backend
# IDE USED
1. IntellijIDEA
# TASK ASSIGNMENTS
1. BIMENYIMANA Jackson assigned to user handling
2. UMUTONI Angella assigned to help
3. IMENA Kenny assigned to system
4. UWIZEYIMANA Laetitia assigned to file operation
5. TUYISENGE S Sebarera Patrick assigned to monitoring and diagnostics

## Setup

- **Java**: JDK 17+ (JDK 25 currently used during development).
- **Database**: MySQL with a database named `commandline`.
- **JDBC driver**: `mysql-connector-java-8.0.28.jar` placed under `CLI/lib/`.

### Database

- Import the provided SQL schema into MySQL, for example `commandline.sql` in the project root.
- In `CLI/db/DBConnection.java`, configure the database URL and credentials:

  ```java
  private static final String URL = "jdbc:mysql://<HOST>:3306/commandline";
  private static final String USER = "root";
  private static final String PASSWORD = "";
  ```

  Replace `<HOST>` with the IP or hostname of the MySQL server.

## Building

From the project root `e:\CLIProject` on Windows PowerShell:

1. **Compile all sources to `out/classes`:**

   ```powershell
   cd e:\CLIProject

   $srcDir = "CLI"
   $outDir = "out\classes"
   $lib   = "CLI\lib\mysql-connector-java-8.0.28.jar"

   if (!(Test-Path $outDir)) { New-Item -ItemType Directory -Path $outDir -Force | Out-Null }

   javac -d $outDir -cp $lib (Get-ChildItem -Path $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName })
   ```

2. **(Optional) Build runnable JAR:**

   ```powershell
   powershell -ExecutionPolicy Bypass -File .\build-jar.ps1
   ```

   This produces `CLIUserManager.jar` in `e:\CLIProject` using `manifest.mf` (with `Main-Class: app.Main`).

## Running

### Run from compiled classes (development)

From the project root:

```powershell
cd e:\CLIProject
java -cp "out\classes;CLI\lib\mysql-connector-java-8.0.28.jar" app.Main
```

### Run from JAR (distribution)

After `build-jar.ps1` succeeds, from the project root:

```powershell
java -jar CLIUserManager.jar
```

Required structure next to the JAR:

- `CLIUserManager.jar`
- `CLI\lib\mysql-connector-java-8.0.28.jar`

## Manual Testing Checklist

- **Environment**
  - Verify Java installation:

    ```powershell
    java -version
    javac -version
    ```

  - Verify database connectivity (from a client that can reach the DB server):

    ```bash
    mysql -h <HOST> -P 3306 -u root -p
    ```

- **Application startup**
  - Start the app, cancel the IP prompt → console shows "No IP entered. Exiting...".
  - Start the app with an invalid IP → error about not reaching the server.
  - Start the app with a valid DB host → GUI window opens; console logs DB connection.

- **User management** (via GUI terminal)
  - `create user` → follow dialog; user is stored in DB.
  - `login` → authenticate created user; window title shows active user.
  - `list users` → list of users from DB.
  - `change password <newPassword>` → subsequent login requires new password.
  - `logout` → session ends; `exit` → app closes.

- **File and folder operations**
  - `create folder testFolder`, `list contents testFolder` → folder created and listed.
  - `create file notes.txt`, `write file notes.txt Hello`, `read file notes.txt` → file content is persisted.
  - `rename folder testFolder testFolder2`, `copy folder testFolder2 testFolder3`, `delete folder testFolder3` → operations reflected on disk.

- **Monitoring and system commands**
  - `history` → shows previously entered commands.
  - `log 10`, `status`, `memory` → log output, session status, JVM memory info.
  - `clear`, `uptime`, `date`, `time` → clear terminal and show system or session timing.

## Troubleshooting

- **Communications link failure**
  - Ensure MySQL is running and reachable at the configured `<HOST>:3306`.
  - Confirm the `commandline` database exists and schema is imported.
  - Check `DBConnection.URL`, `USER`, and `PASSWORD` values.

- **`ClassNotFoundException: app.Main`**
  - Ensure classes are compiled into `out\classes`.
  - Use the `java -cp "out\classes;CLI\lib\mysql-connector-java-8.0.28.jar" app.Main` command.

- **`jar` or `jpackage` not recognized**
  - Install a full JDK (not just a JRE) and make sure its `bin` directory is on the system `PATH`.

