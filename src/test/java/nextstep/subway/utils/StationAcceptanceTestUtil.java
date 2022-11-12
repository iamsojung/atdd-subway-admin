package nextstep.subway.utils;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class StationAcceptanceTestUtil {
    private static final String BASE_PATH = "/stations";
    private static final String NAME = "name";
    private static final String ID = "id";

    private StationAcceptanceTestUtil() {
    }

    public static void 지하철역_생성(String stationName, HttpStatus httpStatus) {
        Map<String, String> params = new HashMap<>();
        params.put(NAME, stationName);

        given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(BASE_PATH)
            .then().log().all()
            .statusCode(httpStatus.value());
    }

    public static long 지하철역을_생성후_지하철_ID를_반환(String stationName, HttpStatus httpStatus) {
        Map<String, String> params = new HashMap<>();
        params.put(NAME, stationName);

        return given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(BASE_PATH)
            .then().log().all()
            .statusCode(httpStatus.value())
            .extract().jsonPath().getLong(ID);
    }

    public static void 지하철역_제거(Long stationId, HttpStatus httpStatus) {
        given().log().all()
            .pathParam(ID, stationId)
            .when().delete(BASE_PATH + "/{id}")
            .then().log().all()
            .statusCode(httpStatus.value());
    }

    public static List<String> 지하철목록을_조회후_지하철_역명_리스트_반환(HttpStatus httpStatus) {
        return given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get(BASE_PATH)
            .then().log().all()
            .statusCode(httpStatus.value())
            .extract().jsonPath().getList(NAME, String.class);
    }

    public static void 지하철_목록_검증_입력된_지하철역이_존재(List<String> actualNames, String... expectNames) {
        assertThat(actualNames).contains(expectNames);
    }

    public static void 지하철_목록_검증_입력된_지하철역이_존재하지_않음(List<String> returnStationNames, String... stationNames) {
        assertThat(returnStationNames).doesNotContain(stationNames);
    }
}
