package timbo.homebrew.ziroomcrawler.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Room {

    private long id;
    private String name;
    private int price;
    private String photo;

    // rewrite properties

    @JsonProperty("desc")   // desc - postgres reserved keyword
    private String description;
    @JsonProperty("apartment_type")
    private int apartmentType;
    @JsonProperty("price_unit")
    private String priceUnit;
    @JsonProperty("photo_alt")
    private String photoAlt;
    @JsonProperty("sale_class")
    private String saleClass;
    @JsonProperty("detail_url")
    private String detailUrl;
    @JsonProperty("sale_status")
    private int saleStatus;
    @JsonProperty("can_sign_time")
    private long canSignTime;
    @JsonProperty("can_sign_long")
    private long canSignLong;
    @JsonProperty("resblock_id")
    private String resblockId;
    @JsonProperty("resblock_name")
    private String resblockName;
    @JsonProperty("agent_end_date")
    private long agentEndDate;


    private Date createTime;

}
