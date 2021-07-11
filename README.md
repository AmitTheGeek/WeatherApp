# WeatherApp

A mobile app to fetch weather data for cities.


## Fragment

- Search Fragment: Display recent searched city weather data.
- Favourite Fragment: Shows weather data for list of cities that has been marked favvourite.
- Setting: Enable/Disable Dark Mode


## Screenshots

<p align="center">
  <img width="25%" src="https://user-images.githubusercontent.com/13044058/125206327-509e5600-e2a4-11eb-99d5-2e24dea54bfb.png">
  <img width="25%" src="https://user-images.githubusercontent.com/13044058/125206329-5431dd00-e2a4-11eb-8c63-701c08e2dcdc.png">
  <img width="25%" src="https://user-images.githubusercontent.com/13044058/125206331-57c56400-e2a4-11eb-8828-4211a1c96afd.png">
</p>


## Important Android Components & Libraries Used

Databinding : To bind UI components to XML layout.

Room : For querying database.

ViewModel : For managing UI-related data in a lifecycle conscious way.

LiveData : For holding observable data.

RecyclerView : To display list of items.

RxJava : To delegate task to worker thread.

Navigation : For navigation to different fragments elegantly

Retrofit : For fetching data from remote server


## Architecture

The project uses MVVM architecture.

![mvvm-arch](https://user-images.githubusercontent.com/13044058/123699994-88a5a200-d87d-11eb-9c56-6542f7af938a.png)
