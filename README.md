# Whack-A-Rabbit (CS304 Final Project)

This repository is dedicated to the CS304 final project, a game developed using JOGL (Java OpenGL).

## Project Overview

Whack-A-Rabbit is a game where the player uses a virtual hammer to "whack" rabbits that randomly pop up in different locations. The objective is to score as many points as possible within the given time limit. The game is designed to be both fun and challenging, incorporating real-time graphics and interactive gameplay.

### Objectives

- Test the player’s reflexes and accuracy.
- Provide an engaging and entertaining experience using JOGL.

## Features and Mechanics

### Features:

1. **Interactive Gameplay:**
    - Rabbits appear at random positions on the game board.
    - Players use the mouse to click on the rabbits to "whack" them.

2. **Dynamic Difficulty:**
    - The frequency of rabbit appearances increases as the game progresses.

3. **Score Tracking:**
    - Points are awarded for each successful "whack."
    - A missed click reduces the player’s score.

4. **Time-Limited Rounds:**
    - Each round lasts for a fixed duration, e.g., 60 seconds.
    - Players aim for the highest score within the time limit.

5. **Graphical Feedback:**
    - Whacked rabbits disappear with an animation.
    - Visual effects are displayed when a rabbit is hit or missed.

6. **Game Over Screen:**
    - Displays the final score and an option to restart the game.

### Mechanics:

- **Rabbit Movement:**
    - Rabbits randomly "pop up" in predefined positions for a short duration before disappearing.
- **Player Input:**
    - Players use mouse clicks to interact with the game.
- **Collision Detection:**
    - Detects if the player clicks on a rabbit to determine a "hit" or "miss."

## How to Run the Game

1. Clone this repository:
   ```bash
   git clone https://github.com/abanoub-refaat/CS304_FinalProject.git
   ```

2. Ensure you have Java and JOGL properly installed on your system.

3. Compile and run the game using your preferred IDE or terminal:
   ```bash
   javac -cp jogl-all.jar:. *.java
   java -cp jogl-all.jar:. Main
   ```

4. Enjoy the game!

## Technologies Used

- **Language:** Java
- **Graphics Library:** JOGL (Java OpenGL)
- **Development Environment:** VS Code, IntelliJ IDEA (or any preferred IDE)

## Development Status

- **Initial Planning:** Completed
- **Current Focus:** Implementing rabbit spawning mechanics and collision detection
- **Upcoming Milestones:**
    - Integrating score tracking
    - Adding animations and graphical effects
    - Designing the game over screen

## Contributing

Contributions are welcome! If you’d like to contribute:

1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes and push to your fork:
   ```bash
   git commit -m "Add feature-name"
   git push origin feature-name
   ```
4. Open a pull request with a description of your changes.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

Stay tuned for more updates as the game evolves!
