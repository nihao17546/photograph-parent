package com.nihaov.photograph.spider.service.impl;

import com.nihaov.photograph.common.utils.QiNiuUploadUtils;
import com.nihaov.photograph.dao.ISecretDAO;
import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.*;
import com.nihaov.photograph.spider.service.ISpiderService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by nihao on 18/1/26.
 */
@Service
public class SpiderServiceImpl implements ISpiderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ISpiderDAO spiderDAO;
    @Resource
    private ISecretDAO secretDAO;

    private SolrServer solrServer;
    @Value("#{configProperties['coreUrl']}")
    private String coreUrl;
    private String accessKey;
    private String secretKey;

    private String imgHost = "http://img.nihaov.com/";

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(coreUrl);
        Map<String,SecretPO> map = secretDAO.selectAll();
        accessKey = map.get("七牛accessKey").getValue().trim();
        secretKey = map.get("七牛secretKey").getValue().trim();
    }

    @Transactional
    @Override
    public void handler(SpiderImgPO spiderImgPO) {
        File file = new File(spiderImgPO.getSavePath());
        if(!file.exists()){
            logger.error("文件[{}]不存在", spiderImgPO.getSavePath());
            return;
        }
        int width = -1;
        int height = -1;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        QiNiuUploadUtils.Result result = QiNiuUploadUtils.upload(
                file, spiderImgPO.getSavePath().replaceAll("/","_"), "data", accessKey, secretKey
        );
        if(result.getRet() == 0){
            logger.error("文件[{}]上传失败", spiderImgPO.getSavePath());
            return;
        }
        ImagePO imagePO = new ImagePO();
        imagePO.setTitle(spiderImgPO.getTitle());
        imagePO.setSrc(imgHost + result.getMsg());
        imagePO.setCompressSrc(imgHost + result.getMsg() + "-suofang");
        imagePO.setWidth(width);
        imagePO.setHeight(height);
        imagePO.setCreatedAt(spiderImgPO.getCreatedAt());
        int a = spiderDAO.insertImg(imagePO);
        if(a != 1){
            logger.error("sql insert error[{}]", spiderImgPO.getSavePath());
            return;
        }

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",imagePO.getId());
        document.addField("image_title",imagePO.getTitle());
        document.addField("image_compress_src",imagePO.getCompressSrc());
        document.addField("image_src",imagePO.getSrc());
        document.addField("image_date",imagePO.getCreatedAt());
        document.addField("image_width",imagePO.getWidth());
        document.addField("image_height",imagePO.getHeight());

        if(spiderImgPO.getTag() != null && !spiderImgPO.getTag().equals("")){
            String tag = spiderImgPO.getTag();
            String[] tags = tag.split(",");
            for(String tagName : tags){
                TagPO tagPO = spiderDAO.selectTagByName(tagName);
                if(tagPO == null){
                    tagPO = new TagPO();
                    tagPO.setName(tagName);
                    tagPO.setType(1);
                    spiderDAO.insertTag(tagPO);
                    Image2TagPO image2TagPO = new Image2TagPO();
                    image2TagPO.setImageId(imagePO.getId());
                    image2TagPO.setTagId(tagPO.getId());
                    spiderDAO.insertImage2Tag(image2TagPO);
                }
                document.addField("image_tag",tagName);
            }
        }
        spiderDAO.updateFlag(spiderImgPO.getId(), 1);

        try {
            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
