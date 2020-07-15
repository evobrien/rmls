# Luas App for RIM


The three use cases are satisfied:

Given I am a LUAS passenger
When I open the app from 00:00 – 12:00
Then I should see trams forecast from Marlborough LUAS stop towards Outbound

Given I am a LUAS passenger
When I open the app from 12:01 – 23:59
Then I should see trams forecast from Stillorgan LUAS stop towards Inbound

Given I am on the stop forecast info screen
When I tap on the refresh button
Then the forecast data should be updated

The refresh button is located in the top right hand corner of the action bar
The app works in portrait and landscape.

Notes:
1) Design: Simple MVVM design with a repository layer linked directly to the network
2) XML parsing done with SimpleXml (JAXB is typically what is recommended but with the data here SimpleXml is relatively simple)
3) Dependency Injection is done using Hilt, the new Dagger library from Google. 
The dependencies are included in one app module for simplicity as there are not that many. The viewmodel specific depenencies could be put in a fragment scoped module, however there were issues with that
4)Unit tests are included for the view model and it's backing components

Possible Improvements:
1) Integration tests could be added - I didn't add them due to time limitations
2) Data processing could be improved in the viewmodel to deal more robustly with errors. Currently it is fairly simple.
3) A database layer using Room & Sqlite could be added possibly. I didn't add it here because I thought it would be redundant as the data really needs to be up to date all the time
4) Selection of different stations from a dropdown built into the action bar. This was outside the scope of the project however
