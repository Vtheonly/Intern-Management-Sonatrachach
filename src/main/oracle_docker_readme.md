# README: Setting Up the Oracle Database via Docker

## The Problem: No Oracle Database Natively ("Womp Womp")

This project requires an Oracle database backend. However, installing and managing a full Oracle Database instance directly on Debian-based Linux distributions like Ubuntu can be complex, resource-intensive, and may not be easily available or desirable for development environments. We needed a reliable and isolated way to run the required Oracle database.

## The Solution: Docker to the Rescue!

We opted to use **Docker** to run an Oracle database instance inside a container. This approach provides several advantages:

1.  **Isolation:** The database runs entirely within its container, not affecting the host operating system (Ubuntu).
2.  **Repeatability:** Using configuration files (`docker-compose.yml`), we can easily recreate the exact same database environment.
3.  **Cleanliness:** No Oracle installation cluttering the host system.
4.  **Availability:** Leverages pre-built Oracle images available on Docker Hub.

We specifically used **Docker Compose** to define and manage the Oracle container service, making the setup process declarative and simpler.

## Prerequisites

Before starting, ensure you have the following installed on your Ubuntu system:

1.  **Docker Engine:** The core Docker runtime.
2.  **Docker Compose:** The tool for defining and running multi-container Docker applications (we use it here even for a single container).

*(We confirmed these were installed on your system. If not, standard installation procedures for Ubuntu should be followed.)*

## Configuration Files

Two key pieces are needed for this setup:

1.  **`docker-compose.yml`:** This file defines the Oracle database service, including the image to use, port mappings, environment variables (like the password), and volume mounts.
2.  **`./sql/` Directory:** This directory contains `.sql` script(s) responsible for initializing the database schema (creating tables, inserting initial data, etc.) the first time the container starts.

## `docker-compose.yml` Breakdown

This file orchestrates the Oracle container setup:

```yaml
version: '3.8' # Optional in newer versions, defines syntax version

services:
  oracle-db: # Name of our database service within Compose
    image: gvenzl/oracle-xe # The specific Oracle Express Edition image used
    container_name: oracle-db-container # A fixed name for the running container
    ports:
      - "1521:1521" # Maps host port 1521 to container port 1521
    environment:
      ORACLE_PASSWORD: rootroot # Sets the SYSTEM/SYS user password inside the container
    volumes:
      # Mounts local SQL scripts into the container's special init directory
      - ./sql:/container-entrypoint-initdb.d
      # Creates/Uses a named volume for persistent database files
      - oracle_data:/opt/oracle/oradata

volumes:
  oracle_data: # Declares the named volume for persistence
```

**Key Parts Explained:**

*   **`image: gvenzl/oracle-xe`**: Specifies the pre-built Docker image containing Oracle XE 21c.
*   **`ports: - "1521:1521"`**: Allows your Java application (running on the host) to connect to `localhost:1521` and reach the Oracle instance inside the container.
*   **`environment: ORACLE_PASSWORD: rootroot`**: **Crucial.** Sets the default `SYSTEM` user password inside the container. This *must* match the password used in the Java `oracleConnector.java`.
*   **`volumes:`** (under `services`):
    *   `./sql:/container-entrypoint-initdb.d`: This is the **magic** for initialization. It takes your local `./sql` directory and makes its contents available inside the container at `/container-entrypoint-initdb.d`. The `gvenzl/oracle-xe` image contains startup logic that **automatically executes any `.sql` files found in this directory ONLY when the database is first created** (i.e., when the `oracle_data` volume is empty).
    *   `oracle_data:/opt/oracle/oradata`: This ensures **persistence**. It links a Docker-managed volume named `oracle_data` to the location where Oracle stores its data files inside the container. If the container is stopped and restarted, the data remains intact in this volume.

## SQL Initialization (`./sql/` directory)

*   This directory must contain your SQL script(s) (e.g., `requstes.sql`, `insertion.sql`, or a single combined file).
*   These scripts should contain all the `CREATE TABLE ...;` and `INSERT INTO ...;` statements needed to set up the initial database structure and data.
*   **Important:** The scripts are executed automatically by the container *only on the first run* when the `oracle_data` volume is empty. They are typically run in alphabetical order.

## Running the Setup

1.  Navigate to the directory containing the `docker-compose.yml` file in your terminal:
    ```bash
    cd /path/to/your/project/main/
    ```
2.  Execute the Docker Compose command:
    ```bash
    # Use sudo if your user isn't in the docker group
    sudo docker-compose up -d
    ```
    This command reads the `docker-compose.yml` file, downloads the image if needed, creates the `oracle_data` volume (if it doesn't exist), starts the `oracle-db-container`, and (on the first run) executes the initialization scripts found in `./sql/`.

## Java Application Connection

For the Java application (`oracleConnector.java`) to connect to this Dockerized database, the following details are essential:

*   **JDBC URL:** `jdbc:oracle:thin:@localhost:1521/XE`
    *   `localhost:1521`: Connects via the mapped port on the host.
    *   `/XE`: **Crucial!** This is the Oracle **Service Name** provided by the `gvenzl/oracle-xe` image setup. This needed to be changed from the original `/sh`.
*   **Username:** `system`
*   **Password:** `rootroot` (Must match `ORACLE_PASSWORD` in `docker-compose.yml`)

## Troubleshooting & Resetting

During the setup, we encountered issues with the SQL initialization scripts:

1.  **Diagnosis:** Errors in the SQL scripts (like missing tables, syntax errors, incorrect functions) often prevent tables from being created. The best way to diagnose was to manually execute the script *inside* the running container using `docker exec`:
    ```bash
    # Copy script inside (optional, makes command easier)
    sudo docker cp ./sql/your_script.sql oracle-db-container:/tmp/your_script.sql
    # Execute manually and watch for ORA- errors
    sudo docker exec -it oracle-db-container sqlplus system/rootroot@//localhost:1521/XE @/tmp/your_script.sql
    ```
2.  **Reset Process:** If the SQL scripts were corrected *after* the initial failed run, the database needed to be reset to force re-initialization:
    *   Stop and remove the container: `sudo docker-compose down`
    *   **Crucially, remove the data volume** to simulate a first run: `sudo docker volume rm <volume_name>` (e.g., `sudo docker volume rm main_oracle_data` - check name with `sudo docker volume ls`). **Warning: This deletes all data.**
    *   Start again: `sudo docker-compose up -d`

We also resolved Java connection issues:

1.  **`ORA-12514`:** Fixed by changing the JDBC URL service name from `/sh` to `/XE`.
2.  **`NullPointerException`:** Fixed automatically once the initial connection succeeded.
3.  **`IllegalArgumentException: Table or column does not exist`:** Fixed by modifying Java database metadata lookup methods (`getSelectableOptions`, `isColumnExist`) to use **UPPERCASE** table/column names, matching how Oracle stores unquoted identifiers.

## Conclusion

By using Docker Compose and the `gvenzl/oracle-xe` image's initialization features, we successfully created a consistent, isolated, and automatically initialized Oracle database environment for the project, overcoming the lack of a native Oracle installation on the development machine. The Java application now connects seamlessly to this containerized database.
