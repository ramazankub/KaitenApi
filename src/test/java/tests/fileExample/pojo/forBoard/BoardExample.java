package tests.fileExample.pojo.forBoard;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class BoardExample {
    private String title;
    private List<Column> columns;
    private List<Lane> lanes;
    private String description;
    private int top;
    private int left;
    private int default_card_type_id;
    private boolean first_image_is_cover;
    private boolean reset_lane_spent_time;
    private boolean automove_cards;
    private boolean backward_moves_enabled;
    private boolean auto_assign_enabled;
    private int sort_order;
    private int external_id;

    public BoardExample(String title, List<Column> columns, List<Lane> lanes, int sort_order, int default_card_type_id) {
        this.title = title;
        this.columns = columns;
        this.lanes = lanes;
        this.sort_order = sort_order;
        this.default_card_type_id = default_card_type_id;
    }
}

