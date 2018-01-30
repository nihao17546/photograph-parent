package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.po.ImagePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by nihao on 17/12/11.
 */
public interface IDataDAO {
    List<IMGPO> selectImgPagination(RowBounds rowBounds);
    int batchInsertImage(@Param("list")List<ImagePO> list);
    int insert(ImagePO item);
    List<IMGPO> selectImg(@Param("list") List<Long> list, RowBounds rowBounds);
    List<Long> selectExist();
    List<ImagePO> selectImagePagination(RowBounds rowBounds);
    int updateCompress(@Param("id") Long id,
                       @Param("compressSrc") String compressSrc);
    int updateCompressAndSrc(@Param("id") Long id,
                             @Param("compressSrc") String compressSrc,
                             @Param("src") String src);
}
