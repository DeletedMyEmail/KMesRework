# KMesRework

<!-- ABOUT THE PROJECT -->
## About The Project

![Messenger Home Screen](/src/main/resources/images/homescreen.png?raw=true)

Out of boredom and a school project, I started writing a messenger.
The following things were important to me:

* a good-looking JavaFX frontend to learn something about the lib 
* a pleasant user experience
* security (password hashing and RSA + AES for messages)
* a good grade

<!-- GETTING STARTED -->
## Getting Started

Download and run the [server jar](out/artifacts/KMesReworkServer_stable_1_0_3_jar/KMesReworkServer_stable-1.0.3.jar) if port 4242 is suitable for your server.
Otherwise, compile the [SocketAcceptor](src/main/java/server/SocketAcceptor.java) yourself.

Running a client is equaly simple. If you want to use the default server just run the [client jar](out/artifacts/KMesReworkClient_stable_1_0_3_jar/KMesReworkClient_stable-1.0.3.jar).
If you want to use a differen self-hosted server define port and ip in [ClientBackend](src/main/java/client/ClientBackend.java).

**Note:** the default server ip in [ClientBackend](src/main/java/client/ClientBackend.java) is defined as **134.122.74.216** and the default port is **4242**.

https://user-images.githubusercontent.com/88390464/189905622-2bff0615-a9ca-40bd-abb1-c13f890c0eba.mp4

## Recent Changes

- Changed server structure to improve performance
- Deleted debug methods
- Added comments
- Specify a port on server start
