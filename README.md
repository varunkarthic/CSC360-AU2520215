# CSC360-AU2520215

Repository for **CSC360: Computer Graphics and Digital Image Processing — Monsoon 2026**, containing course notes, classwork, homework, programming exercises, and project implementations.

## Repository Structure

```text
CSC360-AU2520215/
├── .gitignore
├── README.md
├── code/
│   ├── project_name/
│   │   ├── README.md
│   │   ├── media/
│   │   │   └── ...
│   │   └── src/
│   │       └── ...
│   └── project_name/
│       ├── README.md
│       ├── media/
│       │   └── ...
│       ├── pom.xml
│       └── src/
│           ├── main/
│           │   └── java/com/github/varunkarthic/App.java
│           └── test/
│               └── java/com/github/varunkarthic/AppTest.java
├── notes/
    ├── 060826.md
    └── ...
└── reflections/
    ├── 060826.md
    └── ...
```

Maven creates a `target/` directory inside a project during compilation, testing, and packaging. Because it contains generated build output rather than source code, it is intentionally omitted from this structure and ignored by Git.

## Notes and Reflections

Course notes and class reflections are stored separately in the `notes/` and `reflections/` directories. Both use the following date-based naming convention:

```text
DDMMYY.md
```

For example:

```text
060826.md
```

The table below provides direct links to the notes and reflection for each class session. Session 1 is marked `N/A` because no dated documentation is currently available for it.

| Class Date | Session | Notes | Reflection | Summary |
|---|---:|---|---|---|
| N/A | 1 | N/A | N/A | No dated notes or reflection are currently available. |
| Aug 6, 2026 | 2 | [Open](notes/060826.md) | [Open](reflections/060826.md) | Covered SSH and HTTPS, image-generation requirements, and raster versus vector graphics. |
| Aug 13, 2026 | 3 | [Open](notes/130826.md) | [Open](reflections/130826.md) | Introduced Maven, standard Java project structure, `pom.xml`, dependencies, plugins, and the build lifecycle. |
| Aug 18, 2026 | 4 | [Open](notes/180826.md) | [Open](reflections/180826.md) | Analysed square geometry by calculating vertices from a centre point and side length. |
| Aug 20, 2026 | 5 | [Open](notes/200826.md) | [Open](reflections/200826.md) | Analysed Java inheritance, overriding, `super`, primitive shapes, and the boundary method for triangles. |
| Aug 25, 2026 | 6 | [Open](notes/250826.md) | [Open](reflections/250826.md) | Covered Maven project configuration, Swing thread safety, processes, threads, and responsive GUI design. |
| Aug 27, 2026 | 7 | [Open](notes/270826.md) | [Open](reflections/270826.md) | Covered Maven local repositories, Java packaging, dependencies, CI/CD, character encoding, testing, and JavaFX. |

## Code and Projects

Programming exercises and project implementations are stored in the `code/` directory. The current projects use two layouts:

* `moving_triangle/` contains standalone Java Swing programs compiled directly with `javac`.
* `square/` is an Apache Maven project that follows the standard `src/main/java` and `src/test/java` directory layout.

| Project | Folder | Summary |
|---|---|---|
| Moving Triangle | [Open](code/moving_triangle/) | Standalone Java Swing programs that animate a triangle by following cursor movement and zooming. |
| Square | [Open](code/square/) | Maven-based Java Swing project that draws a blue square outline using `Graphics2D`. |

For Maven projects, `pom.xml` defines the project configuration and dependencies, application code belongs in `src/main/java`, and tests belong in `src/test/java`. The generated `target/` directory must not be committed.

Each project includes a dedicated `README.md` containing:

* Implementation logic and approach
* Technical documentation
* Description of individual programs or modules
* Relevant setup and execution instructions
* Images, screenshots, or videos demonstrating execution, where applicable

Supporting files and media may be organized into additional subdirectories as required.

## Course Information

* **Course:** CSC360 — Computer Graphics and Digital Image Processing
* **Semester:** Monsoon 2026
* **Enrollment Number:** AU2520215
