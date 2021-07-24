import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    @Test
    public void test1(){
        String config = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(config);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames) {
            System.out.println(name);

        }
        DicTypeDao dicTypeDao = (DicTypeDao) applicationContext.getBean("dicTypeDao");

        List<DicType> dicTypes = dicTypeDao.selectDicTypes();
//        User user = new User();
//        user.setLoginAct("ls");
//        user.setLoginPwd("123123");
//        User u  = userDao.login(user);
//        List<User> userList = userDao.findUser();
//        for (User user: userList) {
//            System.out.println(user);
//        }
//        if (u != null){
//            System.out.println(u);
//        }
//        System.out.println(u.getExpireTime().compareTo(DateTimeUtil.getSysTime()));
//        String ips = "192.168.1.1,192.168.1.2,127.0.0.1";
//        System.out.println(ips.contains(u.getAllowIps()));

    }
    @Test
    public void time() throws JsonProcessingException {
        String msg = "用户密码错误";
        User user = new User();
        user.setLoginAct("ls");
        user.setLoginPwd("123123");
        Map<String,String> map = new HashMap<>();
        map.put("msg",msg);
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(map));
    }

    @Test
    public void test02(){
        System.out.println(UUIDUtil.getUUID());
        System.out.println(DateTimeUtil.getSysTime());
    }
}
