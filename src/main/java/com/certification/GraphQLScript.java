package com.certification;

import static io.restassured.RestAssured.given;

import org.junit.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {

//		query

		int characterId = 587;

		String response = given().log().all().header("Content-type", "application/json").body(
				"{\"query\":\"query ($characterId: Int!, $episodeId: Int!, $name: String, $episode: String!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId: 1) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: $name}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: $episode}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"
						+ characterId + ",\"episodeId\":475,\"episode\":\"The War of the Damned \"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String characterName = js.getString("data.character.name");
		Assert.assertEquals(characterName, "Bear");

//		Mutations

		String newCharacter = "Bear";

		String mutationResponse = given().log().all().header("Content-type", "application/json").body(
				"{\"query\":\"mutation ($locationName: String!, $characterName: String!, $episodeName: String!) {\\n  createLocation(location: {name: $locationName, type: \\\"Southzone\\\", dimension: \\\"234\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Macho\\\", status: \\\"Live\\\", species: \\\"fantasy\\\", gender: \\\"Male\\\", image: \\\"png\\\", originId: 244, locationId: 775}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date: \\\"1950 june\\\", episode: \\\"Prime\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: [842]) {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"Medellin\",\"characterName\":\""
						+ newCharacter + " \" ,\"episodeName\":\"Spartacus the warm of\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();

		System.out.println(mutationResponse);

	}
}
