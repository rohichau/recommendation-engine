# recommendation-engine
A simple MVP for recommendation engine

#Steps to test the logic:

1.) Open RecommendationEngineTest.java on the path /src/test/java/com/gameberry/sample/recommendation/engine

2.) Run the Junit test that test the usage of recommendation engine 

# Class Flow explanation:

1.) The project is a typical SpringBoot project with controller layer omitted
    for simplification as we are not concerned with API usage as per the problem statement.

2.) The service layer consists of RecommendationService which is responsible for handling
    the core logic of calculating Restaurant Recommendation for a user.
    
3.) The models package contains all the Objects used in our design.

4.) The utils package consists of Listener objects as we are using *Observer Pattern* 
    here to listen to any changes to any user or restaurant object and pre-compute the
    recommendations so that we can provide them faster.
    
    RestaurantListener and UserListener are registered as listeners to User and 
    Restaurant class and any change in those objects are propagated to the listeners
    via the notifyListeners() call.
    
5.) The Recommendation Engine class acts as a stateful engine which maintains the current users
    and their recommendations. 
    
6.) If we would have used a DB then instead of using Observer pattern and stateful engine
    we would have persisted the states to the DB and would have handled the changes
    in user or restaurant object as and when they come through the API.
