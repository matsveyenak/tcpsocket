# A simple TCP/IP client-server implementation in Java

## **Description**
This is a simple TCP/IP client and server program that is written in Java. It uses a Socket API that allows to start listening for incoming data on the selected port. After receiving incoming command (the client must preliminary passed an authentication process), the server will load all the cars and insurance offers for a specific user, and send them in response. The client, after receiving this data, must write them to the console in a convinient way for the user. 

To pull data, a following PostreSQL database is used, as well as a pure JDBC driver to establish connection to the database from a server side.

<img src="/screenshots/dbscheme.png" height="300">

The server supports multiple connections from clients by createing a new thread to handle every incoming connection.

## **Installation**

This program can be run in different Java IDEs (f.e. Intellij IDEA or Eclipse) or just in a console mode. Also a PostgreSQL JDBC driver is needed to be in a classpath. 
To create a PostreSQL relational database, download a SQL script that is presented in the repository.

## **Use cases**

<img src="/screenshots/client_menu.jpg" height="200">
<img src="/screenshots/insurance_offer.jpg" height="200">
<img src="/screenshots/multiple_offers.jpg" height="200">
<img src="/screenshots/multiple_clients.jpg" height="200">

Made by Aliaksei Matsveyenak.
