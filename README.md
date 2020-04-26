# Team-62New
Team 62 - MCIT 591
Team Members: 
Henry Chapman (hchapm@seas.upenn.edu) - Graphing Part
Jeri Shelly (jshelly@seas.upenn.edu) - Data Retrieval
Grace Park (gracepa@seas.upenn.edu) - User Interface

Project Idea: User Interface for Graphing a Live and Historical Stock Indices We created a simple GUI that will displays historical. A user can open the application, then select a variety of financial indices or individual securities. The user also selects the date range they would like to view. They have the option of selecting a day, month, week or year.

The user can opt to display a trendline in the graph by selecting the "trendline" check box. 

The graph displays a red line of the prices move in a negative direction and a green line if they move in a positive direction.

Limitations:
Due to free API restrictions, the user can only make 5 calls per minute to retrieve data. Each stock display counts as one call. Every trendline selection counts as one call. Therefore, if the user selected "VOO" with a trendline, that would count as two calls. An error message will appear if the user makes too many API calls. 

How to get it running:
1. Download the .Zip file from this GitHub Repository.
2. Open the project in Eclipse. 
 - File -> Open Projects From File System -> Navigate to Directory -> Click Open 
 - Go to a JUnit Test, hover over a test, then click Import JUnit 5 or JUnit 4.  
3. Run the project from UserInterface.java.

To Use the App:
1. Select the Index want to view.
2. Select if you want to view a trendline or not. 
3. Select if which time period you want. 
4. Click the OK Button.
