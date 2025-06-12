Trading App
A Spring Boot application for trading, created by abubakar0221.
Overview
This project is a simple Spring Boot application that provides REST endpoints for a trading app. It includes a welcome page and can be extended with additional features.
Live Demo

Static Site (Custom Domain): Visit the project’s static site at https://abubakar0221/trading/.
Spring Boot App: The app is deployed on Render at https://trading-app.onrender.com/welcome (replace with your actual Render URL after deployment).

GitHub Pages Links
Access the static pages of this project directly in your browser:



Home Page (https://abubakar0221/trading/)
About Page (https://abubakar0221/trading/about.html)
Forgot Password Page (https://abubakar0221.github.io/trading/forgot.html)
Contact Page (https://abubakar0221/trading/contact.html)
FAQ Page (https://abubakar0221/trading/faq.html)
dashboard here:https://abubakar0221.gtihub.io/trading/demo/src/main/resources/static/User_dashboard2.html

Prerequisites

Java 17 or higher
Maven (included as mvnw)
Git
Visual Studio Code (recommended) with the following extensions:
Spring Boot Extension Pack (by VMware)
Java Extension Pack (by Microsoft)



Setup Instructions

Clone the Repository:git clone https://github.com/abubakar0221/trading.git


Open in VS Code:
Open VS Code and select File > Open Folder.
Choose the trading folder.


Install Dependencies:
Open a terminal in VS Code (Terminal > New Terminal).
Run:mvn clean install




Run the Application:
Using Spring Dashboard:
Click the Spring Boot icon on the left sidebar.
Right-click trading under Apps and select Run.


Using Terminal:mvn spring-boot:run




Access the App:
Open a browser and go to http://localhost:8080/welcome.
You should see: Welcome to the Trading App!.



Features

Welcome Endpoint: Access the welcome page at /welcome.
More features can be added by extending the TradingApplication.java file.

Troubleshooting

Port 8080 in Use:
Create or edit src/main/resources/application.properties:server.port=8081


Run again and use http://localhost:8081/welcome.


Dependencies Not Downloaded:
Run mvn clean install in the terminal.


Java Version Mismatch:
Ensure Java 17 is installed:java -version


If not, install JDK 17 from oracle.com/java/technologies/downloads/.



Contributing
Feel free to fork this repository, make changes, and submit a pull request. For major changes, please open an issue first to discuss what you would like to change.
License
This project is licensed under the MIT License - see the LICENSE file for details.
Contact
For questions, reach out to abubakar0221 on GitHub.
