# Sagrada
Final project for Software Engineering course

Group 31

Team members:

- Martina Bellini (focus on model, game logic)
- Filippo Maria Benati (focus on UI)
- Nicolò Brunello (focus on network layer)

# Implemented requisites
We aim at the 30 mark so we implemented full rules + CLI + GUI + RMI + Socket + 1 F.A.
We chose to implement the Dynamic Scheme Cards F.A.
## Dynamic Scheme Cards
We chose to store the scheme cards in a JSON file with the following structure:

    [
      {
        "front": {
          "name": "Front name",
          "difficulty": 6,
          "patternConstraints": [
            [4, null, null, "green", "red", "blue"],
            [null, 3, null, 6, null, 5],
            ["blue", "yellow", null, "purple", null],
            ["red", null, null, null, null]
          ]
        },
        "back": {
          "name": "Back name",
          "difficulty": 3,
          "patternConstraints": [
            [4, null, null, null, 2, 1],
            [null, "red", "blue", null, null, null],
            [3, null, 3, "green", 5],
            [null, "red", "blue", 1, null]
          ]
        }
      }
     ]

Additionally, all the other cards of the game (Tool Cards, Public and Private Objective Cards) are stored in JSON and
can be easily extended or created anew.

# Usage
Both the client and the server JARs accept command line arguments and gracefully fall back to predefined constants, or
ask the user to supply the needed information.
## Server usage
The server JAR accepts two CLI arguments, the first being the connection timeout and the second being the turn timeout.
One or two or none can be specified, the server will state which values it will use upon launch. The values are assumed
in milliseconds.
## Client usage
The client JAR accept one CLI argument, the preferred UI mode. It has to be one of `rmi` or `gui` (case insensitive). If
there's no argument, or a wrong value is supplied, the client asks the user (on the command line) which mode they prefer
until a valid choice is entered.