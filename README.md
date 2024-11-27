# FitPeoAutomation
Language: Java
Automation Tool: Selenium
Framework: JUnit
Report: HTML

Automation Script Source Code
The source code of the automation script is included in this document. Please refer to the file FitPeoTest.java in the project folder. The script uses Selenium WebDriver for browser automation, ExtentReports for HTML reporting, and JUnit 5 for test assertions.

**Setup Instructions**
**Prerequisites**
Java Development Kit (JDK): Install JDK 11 or later.
Maven: Ensure Apache Maven is installed and configured.
IDE: Use any IDE (e.g., IntelliJ IDEA, Eclipse, or Visual Studio Code).
Browser Driver:
Install the ChromeDriver compatible with your Chrome browser version.
Add the ChromeDriver executable path to the system's PATH environment variable or provide it programmatically.
Dependencies
The required dependencies are managed using Maven. Add the pom.xml

**How to Run the Script**

Step 1: Clone or Download the Repository
Clone the project repository or copy the files into your development environment.

Step 2: Import the Project as a Maven project
Open the project folder in your IDE.

Step 3: Build the project with Maven build
Let Maven download all the required dependencies

Step 4: Run the project as Junit test
Right-click or select run as and select as JUnit Test

Step 5: View logs in the console.
After the script completes execution:

Step 6: View the HTML detailed report of the execution
Navigate to the test-Reports/ directory in the project folder.
Open FitPeoReport.html in a browser to view the test execution report.

**Highlight of Project**
Dynamic Web Elements
The script uses the following strategies to handle dynamic web elements:

Explicit Waits:
Used WebDriverWait to ensure elements are loaded before interaction.

JavaScript Executor:
Used to scroll and interact with elements not directly visible.

Actions Class:
Used for simulating complex user interactions like adjusting sliders.

**Best Practices Followed**
Reusability:
Methods like navigateToHomePage, adjustSlider, and validateTotalRecurringReimbursement are modular and reusable.

Error Handling:
Exception handling is implemented with meaningful logs and assertions.

Reporting:
Integrated with ExtentReports for detailed and visually appealing test reports.

Dynamic Locators:
Used dynamic locators to handle changing web element attributes effectively.

Testing Notes
The script has been tested thoroughly on the FitPeo application.

Verified with Chrome browser versions >=114.
Test cases handle page loads, slider adjustments, text box updates, checkbox selections, and validation of reimbursement values.

**Future Enhancements**
Add support for cross-browser testing.
Integrate with CI/CD pipelines using Jenkins or GitHub Actions.
Parameterize test data for scalability and maintainability.
Migrate to Cucumber Framework to achieve multiuser testing and user friendly.
