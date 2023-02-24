import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Weather {
	public static void main(String[] args) throws Exception {
		java.util.Scanner sc = new java.util.Scanner(System.in);
		System.out.println("Hello User");
		System.out.println("Please Enter the City Name");
		String city = sc.next();
		// String city = "Dhanbad";

		String url = "https://api.openweathermap.org/data/2.5/weather?q=";
		String apiKey = "";				// Please Mention your API key
		String utility = ",in&appid=";
		city = city.toLowerCase();
		url = url + city + utility + apiKey;
		// System.out.println(url);

		callRequest(url);

	}

	public static void callRequest(String url) throws IOException, InterruptedException {
		var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();

		var client = HttpClient.newBuilder().build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// System.out.println(response.body());

		// Getting Objects from the JSON file

		try {
			System.out.println("==================Weather=========================");

			JSONParser parser = new JSONParser();
			Object object = parser.parse(response.body());
			// System.out.println(object);
			JSONObject mainObject = (JSONObject) object;
			// System.out.println(mainObject);

			// -------------Getting the City Name------------------------
			String city = (String) mainObject.get("name");
			System.out.println("City Name : " + city);

			Long cityId = (Long) mainObject.get("id");
			System.out.println("City ID " + cityId);

			Long cityTimeZone = (Long) mainObject.get("timezone");
			System.out.println("City Time Zone " + cityTimeZone);

			Long cityCode = (Long) mainObject.get("cod");
			System.out.println("City Code (Weather)" + cityCode);

			// ---------City Weather------------------------------
			JSONArray weather = (JSONArray) mainObject.get("weather");
			// System.out.println(weather);
			JSONObject extractWeather = (JSONObject) weather.get(0);

			// Getting ID of the weather
			Long weatherId = (Long) extractWeather.get("id");
			System.out.println("Weather ID " + weatherId);

			// Getting Icon of Weather
			var icon = extractWeather.get("icon");
			System.out.println("Icon " + icon);

			// Getting main of the weather
			var main = extractWeather.get("main");
			System.out.println("Weather Main " + main);

			// Getting Description of the Clouds
			String descritpion = (String) extractWeather.get("description");
			System.out.println("Description " + descritpion);

			// -----------Co-Ordinates-Of-City---------------
			JSONObject coor = (JSONObject) mainObject.get("coord");
			Double coorLongitude = (Double) coor.get("lon");
			Double coorLatitude = (Double) coor.get("lat");
			System.out.println("Longtitute " + coorLongitude + " Latitude " + coorLatitude);

			// Temperature :
			JSONObject temperature = (JSONObject) mainObject.get("main");
			Double temp = (Double) temperature.get("temp");
			Double temp_feelsLike = (Double) temperature.get("feels_like");
			Double temp_min = (Double) temperature.get("temp_min");
			Double temp_max = (Double) temperature.get("temp_max");
			Long pressure = (Long) temperature.get("pressure");
			Long humidity = (Long) temperature.get("humidity");
			Long sea_level = (Long) temperature.get("sea_level");
			Long groundLevel = (Long) temperature.get("grnd_level");

			System.out.println("Temperature (In Kelvin) " + temp);

			// ------ Wind Status-------------
			JSONObject wind = (JSONObject) mainObject.get("wind");
			Double windSpeed = (Double) wind.get("speed");
			Long windDegree = (Long) wind.get("deg");
			Double windGust = (Double) wind.get("gust");

			// ------------Clouds Status------------
			JSONObject clouds = (JSONObject) mainObject.get("clouds");
			Long all = (Long) clouds.get("all");
			System.out.println("Clouds " + all);

			// Sunrise and Sunset
			JSONObject sys = (JSONObject) mainObject.get("sys");
			String country = (String) sys.get("country");
			Long sunrise = (Long) sys.get("sunrise");
			Long sunset = (Long) sys.get("sunset");

			System.out.println("Country Code " + country);
			System.out.println("Sunrise " + sunrise);
			System.out.println("Sunset " + sunset);

			System.out.println("================================================");

		} catch (Exception e) {
			// System.error("Invalid City Name");
			System.out.println("Error, Please Enter The correct City");
			// e.printStackTrace();
		}
	}
}
