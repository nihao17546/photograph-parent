package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.*;
import com.nihaov.photograph.dao.IMGDAO;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.service.IUserService;
import com.nihaov.photograph.web.util.BaiduUtils;
import com.nihaov.photograph.web.util.QINIUUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nihao on 17/8/25.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private QINIUUtils qiniuUtils;
    private final String qiniuPrefixUrl = "http://ovstg74bg.bkt.clouddn.com/";
    @Resource
    private IMGDAO imgdao;
    @Resource
    private IUserService userService;
    @Resource
    private BaiduUtils baiduUtils;

    @RequestMapping(value = "/look")
    @ResponseBody
    public String look(@RequestParam(value = "file",required = true) MultipartFile multipartFile,
                         @RequestParam(value = "word", required = true) String word,
                         @RequestParam(value = "pos", required = true) Integer pos,
                         @RequestParam(value = "size", required = true) Integer size,
                         @RequestParam(value = "color", required = true) String color,
                         @RequestParam(value = "family", required = false) String family,
                         @RequestParam(value = "type", required = true) Integer type,
                         @RequestParam(value = "uid", required = true) Long uid){
        DataResult dataResult = new DataResult();
        String today = SimpleDateUtil.shortFormat(new Date()).replaceAll("-","");
        String sourcePath = "/mydata/ftp/look/source/" + today + "/" + UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
        String outPath = "/mydata/ftp/look/out/" + today;
        File sourceFile = new File(sourcePath);
        if(!sourceFile.getParentFile().exists()){
            sourceFile.getParentFile().mkdirs();
        }
        File outFile = new File(outPath);
        if(!outFile.exists()){
            outFile.mkdirs();
        }
        try{
            String filePath = null,fileName = null;
            FontText fontText = new FontText(word, pos, color, size, family);
            Thumbnails.of(multipartFile.getInputStream()).size(500,500).toFile(sourceFile);
            ImageIcon imgIcon = new ImageIcon(sourcePath);
            Image img = imgIcon.getImage();
            if(type == 1){//固定文字
                fileName = UUID.randomUUID().toString() + ".png";
                filePath = outPath + "/" + fileName;
                BufferedImage bufferedImage = ImageUtils.drawTextInImg(img, fontText, 0);
                FileOutputStream out = new FileOutputStream(filePath);
                ImageIO.write(bufferedImage, "png", out);
                out.close();
            }
            else{
                fileName = UUID.randomUUID().toString() + ".gif";
                filePath = outPath+ "/" + fileName;
                AnimatedGifEncoder e = new AnimatedGifEncoder();
                BufferedImage bufferedImage1 = ImageUtils.drawTextInImg(img, fontText, 10);
                BufferedImage bufferedImage2 = ImageUtils.drawTextInImg(img, fontText, 0);
                e.start(filePath);
                e.setRepeat(0);
                e.addFrame(bufferedImage1);
                e.setDelay(500);
                e.addFrame(bufferedImage2);
                e.setDelay(500);
                e.finish();
            }

            //上传文件到七牛
            QINIUUtils.Result result = qiniuUtils.upload(filePath, fileName);
            if(result.getRet() == 1){
                dataResult.setCode(200);
                dataResult.setMessage("操作成功");
                dataResult.setResult(qiniuPrefixUrl + result.getMsg());
                //数据库存储
                IMGPO imgpo = new IMGPO();
                imgpo.setTitle("用户上传图片" + uid);
                imgpo.setCompressSrc((String)dataResult.getResult());
                imgpo.setSrc((String)dataResult.getResult());
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                imgpo.setWidth(width);
                imgpo.setHeight(height);
                imgpo.setSavePath("http://fdfs.nihaov.com/look/out/" + today + fileName);
                int r = imgdao.insertPic(imgpo);
                if(r == 1){
                    userService.favo(uid ,imgpo.getId());
                }
            }
            else{
                dataResult.setCode(500);
                dataResult.setMessage(result.getMsg());
                return JSON.toJSONString(dataResult);
            }
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMessage("系统错误");
            logger.error("图片处理错误",e);
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/face")
    @ResponseBody
    public String face(@RequestParam(value = "file",required = true) MultipartFile multipartFile,
                       @RequestParam(value = "uid", required = true) Long uid,
                       @RequestParam(value = "current", required = true) String current){
        DataResult dataResult = new DataResult();
        String today = SimpleDateUtil.shortFormat(new Date()).replaceAll("-","");
        String fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
        String sourcePath = "/mydata/ftp/face/" + today + "/" + uid + "/" + fileName;
        File file = new File(sourcePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            QINIUUtils.Result result = qiniuUtils.upload(sourcePath, "uid" + uid + fileName);
            if(result.getRet() == 1){
                String pic = qiniuPrefixUrl + result.getMsg();
                //数据库存储
                IMGPO imgpo = new IMGPO();
                imgpo.setTitle("用户上传照片" + uid);
                imgpo.setCompressSrc(pic);
                imgpo.setSrc(pic);
                ImageIcon imgIcon = new ImageIcon(sourcePath);
                Image img = imgIcon.getImage();
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                imgpo.setWidth(width);
                imgpo.setHeight(height);
                imgpo.setSavePath("http://fdfs.nihaov.com/face/" + today + "/" + uid + "/" + fileName);
                imgdao.insertPic(imgpo);
                //图像识别
                String image = BaseUtil.getBase64(multipartFile.getInputStream(), false);
                BaiduUtils.Detect detect = baiduUtils.detect(image);
                if(detect == null){
                    dataResult.setCode(500);
                    dataResult.setMessage("抱歉图像识别错误，请稍后再试。");
                }
                else{
                    Map<String,Object> map = new HashMap<>();
                    map.put("pic", pic);
                    map.put("face", detect);
                    dataResult.setCode(200);
                    dataResult.setMessage("success");
                    dataResult.setResult(map);
                }
            }
            else{
                dataResult.setCode(500);
                dataResult.setMessage(result.getMsg());
                return JSON.toJSONString(dataResult);
            }
        } catch (Exception e) {
            logger.error("上传face失败", e);
            dataResult.setCode(500);
            dataResult.setMessage("抱歉，服务器异常，请稍后再试。");
        }
        return JSON.toJSONString(dataResult);
    }
}
