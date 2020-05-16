package timbo.homebrew.ziroomcrawler.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import timbo.homebrew.ziroomcrawler.domain.Room;

@Mapper
@Repository
public interface RoomMapper {

    @Select("SELECT * FROM ROOM WHERE ID = #{id}")
    Room findOne(long id);

    @Insert("INSERT INTO ROOM(ID, NAME, PRICE, PHOTO, DESCRIPTION, APARTMENT_TYPE, PRICE_UNIT, PHOTO_ALT, SALE_CLASS, DETAIL_URL, SALE_STATUS, CAN_SIGN_TIME, CAN_SIGN_LONG, RESBLOCK_ID, RESBLOCK_NAME, AGENT_END_DATE)" +
            " VALUES(#{id}, #{name}, #{price}, #{photo}, #{description}, #{apartmentType}, #{priceUnit}, #{photoAlt}, #{saleClass}, #{detailUrl}, #{saleStatus}, #{canSignTime}, #{canSignLong}, #{resblockId}, #{resblockName}, #{agentEndDate})")
    int save(Room room);

    @Delete("DELETE FROM ROOM WHERE ID = #{id}")
    int deleteOne(long id);

}
