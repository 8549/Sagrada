# Socket comunication protocol

## Format
The messages are simple strings with the folllowing format: 
```

<TYPE><blank><HEADERr><blank><DATA><blank><END>

````
Where the parameters have these meanings:
* TYPE --> this could be *request*, *answer* *update*
* HEADER --> the purpose of the message and can be *login*, *init*, *move*, *tool*, *update*, *end*
* DATA --> the content of the message
* END --> end of the message

At the beginning of the connection, the client must send a *request login*. The server will then answer with the response of the login procedure and, after the room is full and ready to start the match, it  pushes the data to the client to init the GUI.

During the game, the server decides who is must make a move and send a *request move* to the right client, it will answer with a *answer move ...* message, which contains all the useful data to process the move. If the move is correct, the server send an *update* message to all client that are observing it, otherwise resends the *request move* to the client. If even this time the same client doesn't make a valid move, it will be kicked out of the match.

After all 10 turns, the server end the game and sends the scores to the clients.

Except for the very first message, the client will continuosly listen for server requests.


LOGIN PHASE:

<pre>
+---------+                   +---------+
| Client  |                   | Server  |
+---------+                   +---------+
     |                             |
     | request login username      |
     |---------------------------->|
     |                             | ----------------------\
     |                             |-| processing username |
     |                             | |---------------------|
     |                             |
     |       answer login accepted |
     |<----------------------------|
     |                             |
     |       update init_game_data |
     |<----------------------------|
     |                             |
     | received                    |
     |---------------------------->|
     |                             |</pre>
     
     
     
TURN PHASE:
<pre>
                        +---------+                         +---------+
                        | Client  |                         | Server  |
                        +---------+                         +---------+
                             |                                   |
                             |                      request move |
                             |<----------------------------------|
---------------------------\ |                                   |
| processing move from gui |-|                                   |
|--------------------------| |                                   |
                             |                                   |
                             | answer move <move type>           |
                             |---------------------------------->|
                             |                                   |
                             |                 request move type |
                             |<----------------------------------|
                             |                                   |
                             | answer move <move data>           |
                             |---------------------------------->|
                             |                                   | ----------------\
                             |                                   |-| checking move |
                             |                                   | |---------------|
                             |                                   |
                             |              update move accepted |
                             |<----------------------------------|
                             |                                   |
                             |      update notifyAll move <data> |
                             |<----------------------------------|
                             |                                   |</pre>
                                                                   


