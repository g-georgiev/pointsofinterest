# Points of Interest

A points of interest (or a POI) is a place that has some tourist value - it might be a museum, a gallery, a landmark, or something not man-made like a simple beautiful view that is worth seeing off the side of the road or pleasant picnic spot. It can be any place anyone might consider fascinating and would like to share with the rest of the world.

# The Points of Interest Project

The Points of Interest Project is a REST API for the management of POIs. It's goal is to store POIs and allow for their easy access to users. Each POI is identified by its latitude and longitude coordinates and can have a description, images and videos (only video link support planned for now). POIs are categorized and can be found by various criteria like the nature of the POI, its purpose, and how accessible it is. POIs can also be found by the coordinates of a point and a radius in km so that users can easily view all the POIs around them. In order to post or find POIs a user must be registered and have a user profile. Each user profile can also have a description, images and videos as well as check in and post comments on POIs and on other user profiles. There is also planned support for user PMs that is currently not implemented.

# Used technologies
* Spring Boot
* Spring Security with JWT
* Swagger2, accessible at /api/swagger-ui/
* PostgreSQL
* https://github.com/JavadocMD/simplelatlng

The API is currently in development but a large portion of the code base is already usable. Further development is planned to proceed alongside front-end development.

# Installation
1. Download the project.
2. Run "./mvnw spring-boot:run" in the project folder.
