package tests.fileExample.pojo.forBoard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Column {
    private String title;
    private int type;

    @JsonCreator
    public Column(
            @JsonProperty("title") String title,
            @JsonProperty("type") int type) {
        this.title = title;
        this.type = type;
    }
}
