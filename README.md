# CA1
Chat-Server/Client + Project Web Server - A 3rd semester computer science project. 



##Protocol used
**Convention** 

1. All commands must be in one line and end with a newline ``\n`` character<sup>1</sup>
2. No names with ``#`` and no messages with ``#``

In the text below the values inside ``[]`` is meant to be replaced by a variable. So ``LOGIN#[USERNAME]`` would be ``LOGIN#Lars`` if your user name is "Lars".

### Client -> Server
These messages should arrive _to_ the server _from_ the client.

| Command | Description | Definition | Examples |
| ------- | ----------- | ---------- | -------- |
| Login   | Logs in the client by adding it to the list of participants in the chat room | ``LOGIN#[USERNAME]`` | ``LOGIN#Lars`` |
| Message | Sends a message to either everyone the chat room (``ALL``) or a single client (in this case ``USERNAME`` is the **receiver**) | ``MSG#[USER|ALL]#[MESSAGE]`` | ``MSG#Lars#Hej``, ``MSG#ALL#Hej`` |

Logging out a client should be discovered by the server when the TCP connection breaks. That should trigger a ``DELETE`` message sent from the server to all the remaining clients.

### Server -> Client
These messages should arrive _to_ the client _from_ the server.

| Command | Description | Definition | Examples |
| ------- | ----------- | ---------- | -------- |
| Ok | Successful login followed by list of active user names | ``OK#[USER1]#[USER2]#...`` | ``OK#Lars#Jens#Donald`` |
| Fail | Failed login if user name is already taken or connection error | ``FAIL`` | ``FAIL`` |
| Update client list | Update all clients when a single new user logs in | ``UPDATE#[USERNAME]`` | ``UPDATE#Lars`` |
| Forward message | Forwards message from a client (``USERNAME`` is now the **sender**) | ``MSG#[USERNAME]#[MESSAGE]`` | ``MSG#Jens#Hej`` |
| Delete user from chat | Tells all clients that a user has disconnected | ``DELETE#[USERNAME]`` | ``DELETE#Lars`` |
