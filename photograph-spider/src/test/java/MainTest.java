import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.model.HProxy;
import com.nihaov.photograph.spider.service.ISpiderService;
import com.nihaov.photograph.spider.util.HProxyFactory;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nihao on 18/1/25.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@ActiveProfiles("test")
public class MainTest {
    @Resource
    private ISpiderService spiderService;
    @Resource
    private ISpiderDAO spiderDAO;

    @Test
    public void dqwdq(){
        List<SpiderImgPO> list = spiderDAO.selectByFlag(0, new RowBounds(0, 10));
        for(SpiderImgPO spiderImgPO : list){
            spiderService.handler(spiderImgPO);
        }
    }
    @Test
    public void dwefewf(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000000000;i++){
                    System.out.println("正在运行:"+i);
                }
            }
        });
        System.out.println(thread.getState());
        System.out.println(thread.isAlive());
        thread.start();
        System.out.println(thread.getState());
        System.out.println(thread.isAlive());
        System.out.println(thread.getState());
        System.out.println(thread.isAlive());
        System.out.println(thread.getState());
        System.out.println(thread.isAlive());
    }
}
