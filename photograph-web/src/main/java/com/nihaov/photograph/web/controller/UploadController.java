package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.AnimatedGifEncoder;
import com.nihaov.photograph.common.utils.FontText;
import com.nihaov.photograph.common.utils.ImageUtils;
import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.web.util.QINIUUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private QINIUUtils qiniuUtils;
    private final String qiniuPrefixUrl = "http://ovstg74bg.bkt.clouddn.com/";

    @RequestMapping(value = "/look",method = RequestMethod.POST)
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
            String filePath = null,fileName = null;
            FontText fontText = new FontText(word, pos, color, size, family);
            Thumbnails.of(multipartFile.getInputStream()).size(500,500).toFile(sourceFile);
            if(type == 1){//固定文字
                fileName = UUID.randomUUID().toString() + ".png";
                filePath = outPath + "/" + fileName;
                BufferedImage bufferedImage = ImageUtils.drawTextInImg(sourcePath, fontText, 0);
                FileOutputStream out = new FileOutputStream(filePath);
                ImageIO.write(bufferedImage, "png", out);
                out.close();
            }
            else{
                fileName = UUID.randomUUID().toString() + ".gif";
                filePath = outPath+ "/" + fileName;
                AnimatedGifEncoder e = new AnimatedGifEncoder();
                BufferedImage bufferedImage1 = ImageUtils.drawTextInImg(sourcePath, fontText, 5);
                BufferedImage bufferedImage2 = ImageUtils.drawTextInImg(sourcePath, fontText, -5);
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
}
