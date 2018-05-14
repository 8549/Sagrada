# Socket comunication protocol

## Format
The messages are simple strings with the folllowing format: 
```

<SENDER><blank><HEADERr><blank><DATA><blank><END>

````
Where the parameters have these meanings:
* SENDER --> who is sending the message
* HEADER --> the purpose of the message and can be *login*, *init*, *move*, *tool*, *update*, *end*
* DATA --> the content of the message
* END --> end of the message

Here there is an example of comunication between **client** and **server**, let's suppose client's username is "foo":

c: ```client login foo end ```

s: ```server login accepted end```

c: ```foo init -- end ```

s: ```server init data1 data2 data3 data4 end```

Through these messages client does the login and receives game data.

