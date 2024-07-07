import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

import org.testng.annotations.Test;

import java.io.File;

public class JiraTest {
    @Test
    public void createBugAndAttachScreenShot()
    {
        //create bug
        RestAssured.baseURI="https://gp-testing.atlassian.net/";

        String response = given()
                .header("Content-Type","application/json").body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"screen shot attached not correct\",\n" +
                        "\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}").header("Authorization", "Basic Z2FuZXNoYS5wMTk5NkB5YWhvby5jb206QVRBVFQzeEZmR0YwWUJRc1BWN2F6SGY4RVdlQ3Q1dl9ubkwxX0JadkxWdWxuNkZkTnIwSkg5aXFGSnhOZTNQTGtVYzRzNGxPWWFobzE4TVNQaGJrckhEcWVEaFduQkV0Y1VXTDJXd1pHbXY2ZnM0SjEwNHFJYWQ2SGZmaG51Y09hZm4yZktTZGdDdFdZNjlnT0NqS0FrZnVYWk1kZklPSEthTHdXN3J4TzFmamRoTWpiMnBHU08wPUI2NTg4MkEy")
                .when().post("rest/api/3/issue").then().assertThat().statusCode(201).log().all()
                .extract().response().asString();


        //GET ID

        JsonPath js=new JsonPath(response);
        String id = js.getString("id");
        System.out.println(id);

        //attach file

        given().header("Authorization", "Basic Z2FuZXNoYS5wMTk5NkB5YWhvby5jb206QVRBVFQzeEZmR0YwWUJRc1BWN2F6SGY4RVdlQ3Q1dl9ubkwxX0JadkxWdWxuNkZkTnIwSkg5aXFGSnhOZTNQTGtVYzRzNGxPWWFobzE4TVNQaGJrckhEcWVEaFduQkV0Y1VXTDJXd1pHbXY2ZnM0SjEwNHFJYWQ2SGZmaG51Y09hZm4yZktTZGdDdFdZNjlnT0NqS0FrZnVYWk1kZklPSEthTHdXN3J4TzFmamRoTWpiMnBHU08wPUI2NTg4MkEy")
            .header("X-Atlassian-Token","no-check")
    //.pathParam("key",id).
                .multiPart("file",new File("C:/Users/prasa/Downloads/AAYQAQSOAAgAAQAAAAAAAB-zrMZEDXI2T62PSuT6kpB6qg.png")).
               when() .post("rest/api/3/issue/"+id+"/attachments")
                       .then().assertThat().statusCode(200);


    }
}
