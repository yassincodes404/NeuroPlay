# CustomLibs — Modular Spring Boot Monorepo & Personal Library Platform

A clean, modern Spring Boot-based architecture that makes all modules reusable as libraries, allows you to run all features via a single Spring Boot application, supports easy unit and integration testing, and automatically publishes artifacts to GitHub Packages.

## 🎯 Overview

CustomLibs is designed to be your personal ecosystem of reusable Java/Spring Boot components. Instead of duplicating utility code or configuration across multiple microservices, you write it once here in a module, test it, and publish it as a dependency for all your other projects to consume.

### Key Features
- **BOM (Bill of Materials):** Centralized version management for all your libraries.
- **Global Starter:** A single dependency (`customlibs-starter`) that pulls in your entire ecosystem.
- **Isolated Feature Modules:** Small, focused libraries (`libs/core`, `libs/auth`, `libs/user`).
- **Integration Testing App:** A main Spring Boot application (`testing/integration-app`) to test how all modules work together.
- **Automated CI/CD:** GitHub Actions automatically tests your code and publishes artifacts to GitHub Packages.
- **Docker Support:** Ready-to-use Dockerfile to package and run the integration app.

## 🏗️ Architecture & Modules

```text
CustomLibs/
│ docker-compose.yml
│ docker/
│   └ Dockerfile
│ pom.xml                      <-- Parent POM (packaging = pom)
│
├─ libs/                       <-- 📦 Feature modules and libraries
│   ├─ core/                   <-- Pure Java utilities (e.g., JwtUtil)
│   ├─ auth/                   <-- Spring Boot auth services/controllers
│   └─ user/                   <-- Spring Boot user services/controllers
│
├─ starters/                   <-- 🚀 Aggregator dependencies
│   └─ customlibs-starter/     <-- Drop-in dependency for the full ecosystem
│
├─ bom/                        <-- 📋 Bill of Materials
│   └─ customlibs-bom/         <-- Centralized version management
│
├─ testing/                    <-- 🧪 Integration applications
│   └─ integration-app/        <-- Main Spring Boot @SpringBootApplication 
│
└─ .github/
    └─ workflows/              <-- CI and Publishing Action workflows
```

## 🚀 Quick Start (Local Execution)

If you just want to run the whole platform to see if the endpoints are working together:

### Using Docker Compose (Recommended if Maven is not installed locally)
```bash
# Build the whole project, run tests, and start the application on port 8080
docker-compose up --build -d

# View logs
docker-compose logs -f

# Stop containers
docker-compose down
```

### Using Local Maven
```bash
# Build the entire platform
mvn clean package

# Run the integration application
java -jar testing/integration-app/target/application-1.0.0-SNAPSHOT.jar
```

## 👨‍💻 Development Workflow

### How to add a new library/module
1. **Create the module folder:** e.g., `libs/billing/`
2. **Create a `pom.xml`:**
```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>com.customlibs</groupId>
    <artifactId>custom-libs</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <relativePath>../../pom.xml</relativePath>
  </parent>
  <artifactId>billing</artifactId>
  
  <dependencies>
    <!-- Add dependencies like libs/core or spring-boot-starter-web -->
  </dependencies>
</project>
```
3. **Register the module:** Open the root `pom.xml` and add `<module>libs/billing</module>` to the `<modules>` list.
4. **Write your code:** Add your services under `libs/billing/src/main/java/com/customlibs/billing/`.
5. **Add to BOM & Starter:**
   - Add version mapping in `bom/customlibs-bom/pom.xml`.
   - Add it as a dependency in `starters/customlibs-starter/pom.xml`.
6. **Integrate:** Add it as a dependency in `testing/integration-app/pom.xml` to test it running with everything else.

### Iterating on a module locally
While developing a specific module, you don't need to rebuild the entire repository or publish it immediately.

You can work entirely inside `libs/your-module/`:

1. Write your Java classes.
2. Write unit tests in `libs/your-module/src/test/java/`.
3. If using an IDE (IntelliJ, Eclipse, VSCode), simply run the tests directly from the IDE interface.
4. Alternatively, use Maven to compile and test just that module:
```bash
mvn clean test -pl libs/your-module
```
Once you are satisfied with the module's behavior, you can integrate it into the `testing/integration-app` and run the full Spring Boot application to manually verify its REST endpoints or integration logic.

## 🧪 Testing Strategy

Because this is a multi-module project, you have fine-grained control over how you test.

### 1. Test a single module (Fastest)
If you are only working on `libs/core`, you only need to run tests for that module:
```bash
mvn clean test -pl libs/core
```

### 2. Test the entire platform
To ensure no module broke another (e.g., core changes breaking auth), run tests from the root directory:
```bash
mvn clean test
```

### 3. Integration Testing
The `testing/integration-app` module is specifically designed to boot up a real Spring Context containing all your `libs/*` beans. You can write `@SpringBootTest` classes inside `testing/integration-app/src/test/java/` to verify HTTP flows and database interactions across different modules.

## 📦 Publishing & Using in Other Projects

The ultimate goal of this repository is to let you use your custom modules seamlessly in completely separate Spring Boot applications.

### 1. Automated Publishing (GitHub Packages)
This repository is configured with GitHub Actions (`.github/workflows/publish.yml`).

- Every time you push to the `main` branch, the CI pipeline automatically tests your code and publishes a `1.0.0-SNAPSHOT` artifact to your GitHub Packages registry.
- When you are ready for a stable release, tag a commit (e.g., `git tag v1.0.0` -> `git push origin v1.0.0`) and the pipeline will publish the stable `1.0.0` version.

### 2. Authentication Setup (Settings.xml)
To download these packages in another project, Maven needs to authenticate with your GitHub account.

1. Generate a Classic Personal Access Token (PAT) on GitHub with the `read:packages` scope.
2. In your home directory (e.g., `~/.m2/` or `C:\Users\YOUR_NAME\.m2\`), create or edit `settings.xml`:
```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>YOUR_GITHUB_USERNAME</username>
      <password>ghp_YOUR_PERSONAL_ACCESS_TOKEN</password>
    </server>
  </servers>
</settings>
```

### 3. Using your libs in a new Project
In your brand-new, completely separate Spring Boot project, open its `pom.xml`:

**Step A: Define the repository so Maven knows where to look:**
```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/YOUR_GITHUB_USERNAME/custom-libs</url>
  </repository>
</repositories>
```

**Step B: Import the BOM (Optional but Recommended):** 
This locks the versions of all your custom libs so you don't have to specify them individually.
```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.customlibs</groupId>
      <artifactId>customlibs-bom</artifactId>
      <version>1.0.0-SNAPSHOT</version> <!-- Or stable 1.0.0 -->
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

**Step C: Add the dependencies!**
You can import the entire ecosystem at once using the starter:
```xml
<dependencies>
  <dependency>
    <groupId>com.customlibs</groupId>
    <artifactId>customlibs-starter</artifactId>
  </dependency>
</dependencies>
```

Or, you can pick and choose exactly what you need (because of the BOM, no `<version>` tag is necessary):
```xml
<dependencies>
  <dependency>
    <groupId>com.customlibs</groupId>
    <artifactId>auth</artifactId>
  </dependency>
</dependencies>
```

That's it! Spring Boot will automatically scan the imported beans if they are in the same base package, and you can `@Autowire` your `AuthService` or `JwtUtil` directly into your new application's controllers.
