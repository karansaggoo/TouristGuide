# TouristGuide
## Introduction
The "Tourist Guide" Android application is designed to help travelers explore new places based on their current location. The app provides recommendations for various types of places, such as cafes, restaurants, hospitals, and more. Users can access detailed information, directions, and reviews for these places, enhancing their travel experience.

## Features
- Location-Based Recommendations: Get recommendations for nearby places based on your current location.
- Detailed Information: View descriptions, ratings, reviews, and photos for recommended places.
- Map Integration: Visualize recommended places on a map for easy navigation and planning.
- Directions: Receive step-by-step directions to your selected destinations.
- Reviews and Trip Planning: Leave reviews for places and access weather information for trip planning.
- Manual Location Selection: Choose a location on the map to find relevant places.
- Reservations: Make reservations at various establishments for added convenience.

## Installation
1. Clone this repository to your local machine using `https://github.com/karansaggoo/TouristGuide.git`
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.

## Usage
1. Launch the app on your Android device.
2. Allow location access to receive accurate recommendations.
3. Browse through recommended places based on your location.
4. Select a place to view detailed information, reviews, and photos.
5. Use the map to navigate to your chosen destination.
6. Leave reviews and ratings for places you visit.
7. Access weather information for effective trip planning.
8. Make reservations at establishments for a smoother travel experience.

 ## Screens
1. Sign up screen: Users can create an account to login into the application if they do not have an account. Users can fill in their email, password, and username to create an account. We are using firebase authentication to create an account.This screen is common to both types of users. Users can sign up as a normal user or a guide as well.

2. Login: Users can login into the application by email and password. We are using firebase authentication to Login into an account. If a user does not have account. he/she can navigate to the sign up screen to create an account.Depending upon the type of user, they will be navigated to either normal user home screen or guide home screen.

3. Home screen: Home Screen will allow users to choose from given categories. A search bar will also be provided to the user to search for a particular activity.When a user clicks on any category, another activity will be opened that contains the list of searched items. Home Screen also contains a button that displays the current location of the user. When the user clicks on this button, the current location will be opened on the map.Floating action button will also be provided on home screen to initiate the process of booking a guide.Following is the home screen for a normal user.


4. List and detail Screen: When the user selects any category, then the list screen is shown. List screen shows all the places depending on the category chosen by the user. When the user selects any place from the list, then the detail screen is opened. The detail screen displays the information regarding the place such as name, rating, timings. There will also be two buttons on the detail screen One is add review and another is view review.
                    

5. Location Helper: This file contains all the code related to the location. Home screen calls the function defined in this file to extract the location of the device. This file implements the fusedLocationProviderClient to extract the location of the user. When the user opens the app for the first time, a dialog box is opened to ask the user about the location permissions so that this app can use the current location of the device to fetch the data.

 
6. RetrofitInstance and IAPIResponse: These files are used to extract the data from the api. Retrofit is a third-party library used to fetch data from the APIs. Retrofit turns HTTP API into a Java or Kotlin interface. IAPIResponse file is an interface that defines the annotations on the interface methods and its parameters indicate how a request will be handled.
API: API is the acronym for application programming interface — a software intermediary that allows two applications to talk to each other. APIs are an accessible way to extract and share data within and across organizations
 We are using google place API for this project. The Places API is a service that accepts HTTP requests for location data through a variety of methods. It returns formatted location data and imagery about establishments, geographic locations, or prominent points of interest.


7. Wishlist: Users can add any place in the wishlist by swiping it to the left. Users can view their wishlist and delete the items any time. Wishlist is maintained in firebase and fetches data when the user navigates to the wishlist screen.
       


8. View Review Screen: When the user clicks on the view review button on the place detail screen, a new screen opens that displays all the reviews of that particular placeThe reviews are fetched from the api and are stored in the form of an array. Then, this array is passed to the view review screen. The view preview screen is designed by using comose. Composable functions are used to display the list of reviews.

9. Add Review: Users can add reviews related to any place. There will also be an option to add review on the place detail screen.When users click on that button, add review screen opens where users can add review for that  particular place.

10. Map: There will also be a screen that shows the current location on map to the users. There is a button on the home screen which when clicked will open the map activity that displayed]s the map.
 
11. Search Guide Screen: When  users click on FAB on the home screen, they will be navigated to the search guide screen. Users can enter any location to search for a guide for that particular place. Depending on that list of guides will be provided to the user.
              
12. Booking screen: When a user clicks on any guide on the search screen, the booking screen will open. It will ask the user for some information such as payment method, number of persons and other details.When user clicks on the booking button, booking will be confirmed and mail will be sent to the user.


13. Booking History: This screen shows the list of all the bookings done by the user. Users can also delete history or cancel bookings.When a user clicks on any booking, its details will be opened. If booking is due, it warns the user regarding cancellation of that booking otherwise it will delete the record.

14. Guide Profile: Guide Profile screen will show the profile of the guide. Guides will be able to see their details like profile picture, name, contact information, description and an update button for updating information.  

15. Guide Profile update: Guide will also be able to update their profile information. When guides open their profile , there will be an option to update their profile information. When they select this option, a new screen will be opened that will allow guides to update their profile information such as email, name, phone,location, price and description.


16. User Review Screen: When the user clicks on the user review button on the menu button , a new screen opens that displays all the reviews of that particular user .The reviews are fetched from the firestore database for a particular user.


17. Forget Screen: If the user forgets his/her password ,there is an option for resetting password that is used to generate a new password . User has to click on the forget password.When screen opens , there is a text field in which the user has to type his/her register email and just click on the reset password.An email will be sent to user for resetting password


 18. Chat: Once booking is done by user, there is an option for chatting with guide .User can ask any question related to the tour booking


## Contributing
Contributions to the "Tourist Guide" app are welcome! If you find a bug, have an enhancement idea, or want to add new features, please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature: `git checkout -b feature-name`
3. Make your changes and commit them: `git commit -m 'Add new feature'`
4. Push to the branch: `git push origin feature-name`
5. Create a pull request explaining your changes.

## License
This project is licensed under the [MIT License](LICENSE).

## Contact
For any questions or inquiries, please contact us at [karanversingh42@gmail.com](mailto:karanversingh42@gmail.com).






