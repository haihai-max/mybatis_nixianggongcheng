package testNixiang;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import haihai.utils.haihai.bean.Emp;
import haihai.utils.haihai.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestNiXiang {

    //利用逆向工程生成的代码，进行一些对数据库的操作
    @Test
    public void testCRUD() throws Exception {

        //加载mybatis核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        //获取一个sqlSessionFactory对象，以便去sqlSession对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        //获取一个sqlSession对象，以便一个实例化的EmpMapper对象
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //获取一个实例化的EmpMapper对象，以便对数据库进行操作
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);

        //使用empExample对象可以对数据库进行多条件操作
        /*EmpExample empExample=new EmpExample();

        //创建Criteria对象，以便使用Criteria内部的方法，实现对数据库的一些已经生成的操作
        EmpExample.Criteria c1 = empExample.createCriteria();
        EmpExample.Criteria c2 = empExample.createCriteria();

        //查询eid为5的记录，相当于调用了已经生成了的sql语句
        c1.andEidEqualTo(5);
        //也可以通过c1的其他方法再添加查询条件，c1内部的条件相当于and;

        //还可以 empExample.or(c2);添加或的查找，相当于or

        //通过mapper，调用相关方法，取出按照empExample操作的结果
        List<Emp> list = mapper.selectByExample(empExample);

        //打印取出来的结果
        for (Emp emp : list){
            System.out.println(emp);
        }*/

        //使用pageHelper进行分页，第一个参数是页码，第二个是页面大小
        PageHelper.startPage(1, 2);

        //查出mapper所对应的表的所有记录，比如mapper这里对应的就是emp表
        List<Emp> emps = mapper.selectAll();

        //pageinfo可以存储分页的一些信息，比如页面大小，是否是第一页等
        PageInfo<Emp> pageInfo = new PageInfo<>(emps);

        //打印出分页的一些信息
        System.out.println(pageInfo);
        System.out.println("++++++++++++++++++++++++++++++");

        //打印取出来的结果
        for (Emp emp : emps) {
            System.out.println(emp);
        }

    }

    //调用mybatis逆向工程配置文件，生成代码
    @Test
    public void testMBG() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configfile = new File("mbg.xml");
        //输出这个文件对象的绝对地址。
        //System.out.println(configfile.getAbsolutePath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configfile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
