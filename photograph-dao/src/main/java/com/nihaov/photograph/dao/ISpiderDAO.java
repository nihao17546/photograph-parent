package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.Image2TagPO;
import com.nihaov.photograph.pojo.po.ImagePO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.pojo.po.TagPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by nihao on 18/1/25.
 */
public interface ISpiderDAO {
    int insert(SpiderImgPO spiderImgPO);
    SpiderImgPO selectBySrc(@Param("src") String src);
    Long selectCountByFlag(@Param("flag") Integer flag);
    List<SpiderImgPO> selectByFlag(@Param("flag") Integer flag,
                                   RowBounds rowBounds);
    int insertImg(ImagePO imagePO);
    TagPO selectTagByName(@Param("name") String name);
    int insertTag(TagPO tagPO);
    int insertImage2Tag(Image2TagPO image2TagPO);
    int updateFlag(@Param("id") Long id,
                   @Param("flag") Integer flag);
    List<ImagePO> selectByFromAndTo(@Param("from") Integer from,
                                    @Param("to") Integer to);
    List<String> selectTagNameByImageId(@Param("imageId") Long imageId);
}
