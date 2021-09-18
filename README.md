Points of Interest

A points of interest is a place that has some tourist value - it might be a museum, a gallery, a landmark, or something not man-made like a simple beautiful view that is worth seeing off the side of the road or pleasant picnic spot. It can be any place anyone might consider fascinating and would like to share with the rest of the world.

The Points of Interest Project is a REST API for the management of POIs. It's goal is to store POIs and allow for their easy access to users. Each POI is identified by its latitude and longitude coordinates and can have a description, images and videos (only video link support planned for now). POIs are categorized and can be found by various criteria like the nature of the POI, its purpose, and how accessible it is. POIs can also be found by the coordinates of a point and a radius in km so that users can easily view all the POIs around them. In order to post or find POIs a user must be registered and have a user profile. Each user profile can also have a description, images and videos as well as check in and post comments on POIs and on other user profiles. There is also planned support for user PMs that is currently not implemented.

All API enpoints are documented with Swagger2 and can be explored at /swagger-ui/.

The API is protected with Spring Security and JWT authentication and authorization. There's an /authenticate enpoint for JWT initialization.

The API is currently in development but a large portion of the code base is already usable. Further development is planned to proceed alongside front-end devlopment.
