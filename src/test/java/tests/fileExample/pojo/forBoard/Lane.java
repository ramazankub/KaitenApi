package tests.fileExample.pojo.forBoard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lane {
    private String title;
    private int type;

    public Lane(
            @JsonProperty("title") String title,
            @JsonProperty("type") int type) {
        this.title = title;
        this.type = type;
    }
}


