import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class oAuth {

    public static void main(String str[]){

    /** Google has restricted the Automation framework for OAuth code
       WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();**/
// Hit this URL manually on browser and copy below...
        //https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php

        String url =
                "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWglD8Ts9BV37Fys4UidM69ceW6ZC6wFoQZrh31xyq8LVYh9ePgWH_I7VaSxum7MOQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=2&prompt=consent#";

        String partialcode=url.split("code=")[1];
        String code=partialcode.split("&scope")[0];
        System.out.println(code);

        String response =
                given()
             .urlEncodingEnabled(false) //Not incode the special characters '%$^&*!'
                 .queryParams("code",code)
                        .queryParams("client_id"
                                , "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParams("grant_type", "authorization_code")
                        .queryParams("state", "verifyfjdss")
                        .queryParams("session_state"
                                , "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                        // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

                        .queryParams("redirect_uri"
                                , "https://rahulshettyacademy.com/getCourse.php")

                        .when().log().all()
                        .post("https://www.googleapis.com/oauth2/v4/token").asString();

// System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        System.out.println(accessToken);

        String getresponse=    given().contentType("application/json").
                queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .asString();
        System.out.println(getresponse);
        //Further splitting the Response
        String api01 = getresponse.split("\"api\":")[1];
        String api02 = api01.split("\"mobile\":")[0];
        System.out.println("API Only "+api02);

    }
}
