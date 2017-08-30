package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.AnimatedGifEncoder;
import com.nihaov.photograph.common.utils.FontText;
import com.nihaov.photograph.common.utils.ImageUtils;
import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.pojo.vo.DataResult;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by nihao on 17/8/25.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/pic")
    @ResponseBody
    public String pic(@RequestParam(value = "file",required = true) MultipartFile multipartFile,
                      HttpServletRequest request){
        DataResult dataResult = new DataResult();
        String today = SimpleDateUtil.shortFormat(new Date()).replaceAll("-","");
        String sourcePath = "/mydata/ftp/look/source/"
                + today
                + "/"
                + UUID.randomUUID().toString()
                + "-"
                + multipartFile.getOriginalFilename();
        try {
            Thumbnails.of(multipartFile.getInputStream()).size(200,200).toFile(sourcePath);
            dataResult.setCode(200);
            dataResult.setResult(sourcePath);
        } catch (IOException e) {
            dataResult.setCode(500);
            dataResult.setMessage("系统错误");
            logger.error("上传图片错误",e);
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping(value = "/look")
    @ResponseBody
    public String look(@RequestParam(value = "file",required = true) MultipartFile multipartFile,
                         @RequestParam(value = "word", required = true) String word,
                         @RequestParam(value = "pos", required = true) Integer pos,
                         @RequestParam(value = "size", required = true) Integer size,
                         @RequestParam(value = "color", required = true) String color,
                         @RequestParam(value = "family", required = true) String family,
                         @RequestParam(value = "type", required = true) Integer type){
        DataResult dataResult = new DataResult();
        String today = SimpleDateUtil.shortFormat(new Date()).replaceAll("-","");
        String sourcePath = "/mydata/ftp/look/source/"
                + today
                + "/"
                + UUID.randomUUID().toString()
                + "-"
                + multipartFile.getOriginalFilename();
        String outPath = "/mydata/ftp/look/out/"
                + today;
        File sourceFile = new File(sourcePath);
        if(!sourceFile.getParentFile().exists()){
            sourceFile.getParentFile().mkdirs();
        }
        File outFile = new File(outPath);
        if(!outFile.exists()){
            outFile.mkdirs();
        }
        try{
            String fileName = null;
            FontText fontText = new FontText(word, pos, color, size, family);
            Thumbnails.of(multipartFile.getInputStream()).size(200,200).toFile(sourceFile);
            if(type == 1){//固定文字
                fileName = UUID.randomUUID().toString() + ".png";
                BufferedImage bufferedImage = ImageUtils.drawTextInImg(sourcePath, fontText, 0);
                FileOutputStream out = new FileOutputStream(outPath + "/" + fileName);
                ImageIO.write(bufferedImage, "png", out);
                out.close();
            }
            else{
                fileName = UUID.randomUUID().toString() + ".gif";
                AnimatedGifEncoder e = new AnimatedGifEncoder();
                e.setDelay(200);
                e.start(outPath+ "/" + fileName);
                BufferedImage bufferedImage1 = ImageUtils.drawTextInImg(sourcePath, fontText, 2);
                BufferedImage bufferedImage2 = ImageUtils.drawTextInImg(sourcePath, fontText, -2);
                e.addFrame(bufferedImage1);
                e.addFrame(bufferedImage2);
            }
            dataResult.setCode(200);
            dataResult.setMessage("操作成功");
            dataResult.setResult("http://fdfs.nihaov.com/look/out/" + fileName);
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMessage("系统错误");
            logger.error("图片处理错误",e);
        }
        return JSON.toJSONString(dataResult);
    }
}
