### School of Computing
### CA326 Year 3 Project Proposal Form
**Project Title**: Fantasy Premier League Point Predictor 
**Student 1 Name**: Stefano Puzzuoli &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;**ID Number**: 17744421  
**Student 2 Name**: Immanuel Idelegbagbon &nbsp; &nbsp;	**ID Number**: 17393433  
**Staff Member Consulted**: Dr Mark Roantree  

1.	**Description** – The project will consist of an Android application that, founded on the Fantasy Football statistics of the past and current season match fixtures of the Premier League, will formulate a score prediction for each individual player, on each individual matchday, throughout the football year.

    For each upcoming matchday, the application will allow the user to visualize an ordered list of all the players of the Premier League, associating to each one their predicted score, assisting each Fantasy Football manager in their decisions when setting up their weekly line-up. 
    
    The application will calculate predictions for each player in the league by making use of a machine learning algorithm that will analyse data from the past games, perform confirmed calculations, and return a score prediction. In view of the fact that the algorithm will be utilizing known datasets (referred to as training datasets) to make predictions, it will be in the set of supervised machine learning algorithms, and more specifically in the regression set, as a result of requiring a prediction that is not limited to a discrete or categorical output. The specific model that will be implemented is the Bagging with Random Forests algorithm, which will receive data divided in a training set (70% of overall data) and a test set (30% of overall data) to make the score predictions. Python 3 will be the programming language used to deal with all the calculations involving the machine learning algorithm and data manipulation.  

    In order to perform such operations in an automated manner on each matchday, the application will utilise Jenkins, allowing continuous integration and facilitating technical aspects of continuous delivery whenever necessary.  

    Since dealing with such a large dataset, the statistics of the match fixtures that will allow the machine learning algorithm to try make accurate predictions will be stored in a cloud database, particularly in Google Cloud Platform, a suite of cloud computing services. Such a platform includes Cloud SQL, a fully-managed database service which will make it easier to set up, maintain, manage, and administer a MySQL relational database on Google Cloud Platform, which will contain our data.  

    The application will be developed in Android Studio, the official integrated development environment for the Android operating system and the programming language used for the user interface and interface background tasks will be Java, due to the many advantages that it offers in Android app development.  

    In addition to the functionalities and tools described above, one of the main focuses of the project will also be to make the application usable by everyone, including people with accessibility needs. When developing the app, accessibility will be strongly considered throughout each step, allowing all users of the final product to have the best experience possible.  
    
2.	**Division of Work**  
    * Stefano Puzzuoli:
      * User requirements gathering
      * User interface interactivity/background tasks
      * Machine learning (Bagging with Random Forests algorithm): 1st step in algorithm – create multiple models with data sets created using the Bootstrap sampling method. 
      * Cloud database management
      * Continuous integration and continuous delivery
      
    * Immanuel Idelegbagbon
      * Dataset retrieval 
      * User Interface
      * Machine learning (Bagging with Random Forests algorithm): 2nd step in algorithm – create multiple models using the Bootstrap sampling method on the different generated training sets.
      * Accessibility features
      * Testing

3.	**Programming language(s)** – Python 3, Java
4.	**Programming tool(s)** – Android Studio, Jenkins, MySQL, Gitlab
5.	**Learning Challenges** – Machine learning, Data and file cloud storage
6.	**Hardware / software platform** – PC, Linux, Android devices, Android Studio, Google Cloud Platform
7.	**Special hardware / software requirements** – None.

