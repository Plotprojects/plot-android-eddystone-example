# Plot External Region Trigger example with Eddystone Beacons
This project exemplifies how to use the Plot External Region Trigger feature using Eddystone beacons. In this example, we did all the required steps from the plugin integration guide. If you want to know more about the Plot Projects plugin, go to [the Plot Projects website](https://www.plotprojects.com).

This guide assumes you have access to [our dashboard](https://admin.plotprojects.com/) and you have a public token. Otherwise, [contact sales](https://content.plotprojects.com/schedule-demo/) for a demo and more information. More information about integrating the Plot Plugin into your Android app is in the [integration section of our documentation](https://www.plotprojects.com/documentation/#android-integration).

## How to use
1. Set your Eddystone Beacon(s) in the Plot Projects Dashboard.
2. Set your credentials in the plotconfig.json file. 
3. Edit the Eddystone namespace set in the MainActivity.java to match yours.
4. Build and run the app.

### Note
This example project actively searches for beacons both while on foreground and background. For testing purposes, you can press
the search button to force a beacon search.

## Links

* [Plot Projects website](https://www.plotprojects.com)
* [Plugin Documentation](https://www.plotprojects.com/documentation)
* [Schedule a demo](https://content.plotprojects.com/schedule-demo/)
* [Eddystone](https://developers.google.com/beacons/)
