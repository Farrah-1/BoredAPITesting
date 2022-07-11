package com.sparta.fw.apitesting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.fw.apitesting.dtos.BoredAPIResponse;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BoredAPITests {
    private BoredAPIResponse response;

    @BeforeEach
    void setup() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            response = mapper.readValue(new URL("http://www.boredapi.com/api/activity"), BoredAPIResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested

    @DisplayName("Status code")
    class StatusCodeChecker {
        @Test
        @DisplayName("Testing the status code")
        void testingTheStatusCode() {

            ObjectMapper mapper = new ObjectMapper();
            BoredAPIResponse response = null;
            try {
                response = mapper.readValue(new URL("http://www.boredapi.com/api/activity"), BoredAPIResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(response.getActivity());
            System.out.println(response.getParticipants());
        }
    }

    @Nested

    @DisplayName("Response checks")
    class ResponseChecker {


        @Test
        @DisplayName("Testing the Response for URL under abilities")
        void testingTheResponseURLAbilities() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon/pikachu"));//tree instead of DTO(Pojo) because we are looking at a specific section,less memory
                System.out.println(jsonNode
                        .get("abilities")
                        .get(0)
                        .get("ability")
                        .get("url")
                        .asText()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Test
        @DisplayName("Testing the Response for name under game indices")
        void testingTheResponseNameGameIndicies() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon/pikachu"));
                System.out.println(jsonNode
                        .get("game_indices")
                        .get(0)
                        .get("version")
                        .get("url")
                        .asText()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("Testing the Response for base experience")
        void testingTheResponseBaseExperience() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon/pikachu"));
                System.out.println(jsonNode
                        .get("base_experience")
                        .asText()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("Testing the Response for name under form")
        void testingTheResponseForNameForm() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon/pikachu"));
                System.out.println(jsonNode
                        .get("forms")
                        .get(0)
                        .get("name")
                        .asText()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("Testing the Response for game index under game indices")
        void testingTheResponseForGameIndexGameIndicies() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(new URL("https://pokeapi.co/api/v2/pokemon/pikachu"));
                System.out.println(jsonNode
                        .get("game_indices")
                        .get(0)
                        .get("game_index")
                        .asInt()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Test
    @DisplayName("HTTP Methods")
    void httpMethods() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = null;
        HttpResponse<String> httpResponse;
        try {
            httpRequest = HttpRequest.newBuilder(new URI("http://www.boredapi.com/api/activity")).build();
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(httpResponse.headers().firstValue("server").toString());

    }

    @Nested

    @DisplayName("Check for value types")
    class CheckForValueTypes {

        @Test
        @DisplayName("Check price type")
        void checkPriceType() {

            Assertions.assertSame(response.getPrice().getClass(), Double.class);
        }

        @Test
        @DisplayName("Check activity type")
        void checkActivityType() {
            Assertions.assertSame(response.getActivity().getClass(), String.class);
        }

        @Test
        @DisplayName("Check link type")
        void checkLinkType() {
            Assertions.assertSame(response.getLink().getClass(), String.class);
        }

        @Test
        @DisplayName("Check types type")
        void checkTypesType() {
            Assertions.assertSame(response.getType().getClass(), String.class);
        }

        @Test
        @DisplayName("Check keys type")
        void checkKeysType() {
            Assertions.assertSame(response.getKey().getClass(), String.class);
        }

        @Test
        @DisplayName("Check Accessibility type")
        void checkAccessibilityType() {
            Assertions.assertSame(response.getAccessibility().getClass(), Double.class);
        }

    }

    @Nested

    @DisplayName("Check Validity - Character Lengths")
    class CheckValidityForLength {

        @Test
        @DisplayName("Check correct length in activity")
        void textInActivity() {

            Assertions.assertTrue(response.getActivity().length() > 1, "There is text in activity!");

        }

        @Test
        @DisplayName("Check correct length in Accessibility")
        void numberInAccessibility() {

            Assertions.assertTrue(response.getAccessibility() <= 1, "There is a number in Accessibility!");

        }

        @Test
        @DisplayName("Check correct length in price")
        void numberInPrice() {

            Assertions.assertTrue(response.getPrice() <= 1);

        }


        @Test
        @DisplayName("Key is 7 digits long")
        void keySevenDigits() {
            Assertions.assertEquals(7, Integer.valueOf(response.getKey().length()));
        }
    }

    @Nested

    @DisplayName("Check Validity - fields not null")
    class CheckValidity {

        @Test
        @DisplayName("Check if activity is not null")
        void activityNotNull() {

            Assertions.assertNotNull(response.getActivity());

        }

        @Test
        @DisplayName("Check if Accessibility is not null")
        void accessibilityNotNull() {

            Assertions.assertNotNull(response.getAccessibility());

        }

        @Test
        @DisplayName("Check if price is not null")
        void priceNotNull() {

            Assertions.assertNotNull(response.getPrice());

        }

        @Test
        @DisplayName("Check if link is not null")
        void linkNotNull() {

            Assertions.assertNotNull(response.getLink());

        }

        @Test
        @DisplayName("Check if type is not null")
        void typeNotNull() {

            Assertions.assertNotNull(response.getType());

        }

        @Test
        @DisplayName("Check if key is not null")
        void keyNotNull() {

            Assertions.assertNotNull(response.getKey());

        }

        @Test
        @DisplayName("Check if participants is not null")
        void participantsNotNull() {

            Assertions.assertNotNull(response.getParticipants());

        }
    }

    @Nested

    @DisplayName("Check Value not out of range")
    class CheckValueNotOutOfRange {

        @Test
        @DisplayName("Check if participant is not out of range")
        void participantNotOutOfRange() {
            Assertions.assertTrue(response.getParticipants() >= 1);
        }

        @Test
        @DisplayName("Check if participant is not negative")
        void participantNotNegative() {
            Assertions.assertFalse(response.getParticipants() <= 0);
        }

        @Test
        @DisplayName("Check if price is not negative")
        void priceNotOutOfRange() {
            Assertions.assertFalse(response.getPrice() <= -1);
        }

        @Test
        @DisplayName("Check if price is not above 1 - Max Capacity")
        void priceNotOutOfRangeMAX() {
            Assertions.assertFalse(response.getPrice() >= 1);
        }

        @Test
        @DisplayName("Check if key is less than 9999999")
        void keyNotOutOfRangeMAX() {
            Assertions.assertTrue(response.getPrice() <= 9999999);
        }

    }
}
