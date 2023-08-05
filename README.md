# LAB02
LAB02 - JAVA 

## Contact

Phan Bá Quang - [@Facebook](https://www.facebook.com/profile.php?id=100009292291898) - 21424082@student.hcmus.edu.vn - 0396509615<br>



<!-- PROJECT LOGO -->
<br />
<div>
<h3 align="center">Java Chat Application -     Multiple users chat application project</h3>

  <p align="center">
    <br />
    Teacher: Nguyễn Đức Huy
    <br />
    Teacher: Nguyễn Văn Khiết
    <br />
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#idea">Idea</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
<a id="about-the-project"></a>

## About The Project

[![Product Name Screen Shot][product-screenshot]]()

Project chat application, uses Java Swing to create the GUI. Project's features include:

- Registering and logging in
- Chatting with other users (online) at the same time
- Allows users to send and receive files

The project uses multithreading techniques to allow multiple users to chat and send file at the same time. Uses Socket
and IO streams to communicate with other users.

<a id="built-with"></a>

### Built With

Used only Java core:

- Java 8
- Java Swing
- Java IO
- Java Thread
- Java File
- Java Socket

<!-- GETTING STARTED -->
<a id="getting-started"></a>

## Getting Started


### Prerequisites

[Install JDK, JRE and configure JAVA_HOME environment variable.](https://youtu.be/IJ-PJbvJBGs)

<a id="installation"></a>

### Server side
Use a **Thread** to handle multiple incoming clients, and it is called **ClientHandlerThread**.
The **ClientHandlerThread** includes: **Socket**, **DataInputStream**, **DataOutputStream** for sending and receiving data;
**username** and **password** to identify the user; **isLogin** to check if the user is logged in.

Each client will be handled by a separate thread, these threads will be stored in a **HashMap** and the key will be the **username**.

### User side
The main **Thread** will handle sending messages and files to other users.
Use a **Thread** to handle multiple incoming messages and files, and it is called **Receiver**.
The **Receiver** includes: DataInputStream for receiving data.

Other clients will be stored in a **HashMap** and the key will be the **username**.

Normal message will be sent as normal **String**, and file will be sent as **byte** array.
Text is sent using **writeUTF** and file is sent using **write**.
But the problem is the **Socket** is allowing only 65482 bytes at once so
the file needs to be sent in chunks.

<!-- CONTACT -->
<a id="contact"></a>




<p align="right">(<a href="#top">back to top</a>)</p>