# CSC360-AU2520215

Repository for **CSC360: Computer Graphics and Digital Image Processing — Monsoon 2026**, containing course notes, classwork, homework, programming exercises, and project implementations.

## Repository Structure

```text
CSC360-AU2520215/
├── .gitignore
├── README.md
├── code/
│   ├── moving_triangle/
│   │   ├── README.md
│   │   ├── media/
│   │   │   ├── FollowCursor.mp4
│   │   │   └── TriangleZoom.mp4
│   │   └── src/
│   │       ├── FollowCursor.java
│   │       └── Zoom.java
│   └── square/
│       ├── README.md
│       ├── media/
│       │   └── square.png
│       ├── pom.xml
│       └── src/
│           ├── main/
│           │   └── java/com/github/varunkarthic/App.java
│           └── test/
│               └── java/com/github/varunkarthic/AppTest.java
├── notes/
    ├── 06-Aug-2026.md
    └── ...
└── reflections/
    ├── 06-Aug-2026.md
    └── ...
```

Maven creates a `target/` directory inside a project during compilation, testing, and packaging. Because it contains generated build output rather than source code, it is intentionally omitted from this structure and ignored by Git.

## Notes

Course notes are stored in the `notes/` directory as Markdown files using the following naming convention:

```text
DD-Mmm-YYYY.md
```

For example:

```text
06-Aug-2026.md
```

## Code and Projects

Programming exercises and project implementations are stored in the `code/` directory. The current projects use two layouts:

* `moving_triangle/` contains standalone Java Swing programs compiled directly with `javac`.
* `square/` is an Apache Maven project that follows the standard `src/main/java` and `src/test/java` directory layout.

For Maven projects, `pom.xml` defines the project configuration and dependencies, application code belongs in `src/main/java`, and tests belong in `src/test/java`. The generated `target/` directory must not be committed.

## Reflections

Class reflections are stored separately in the `reflections/` directory using the same date-based filenames as the notes.

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
