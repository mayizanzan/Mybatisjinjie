package com.laji.test;

import com.laji.mapper.BrandMapper;
import com.laji.pojo.Brand;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//加了@test这个会自动引进
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试传入参数==》mapper代理获得参数==》mapper对象执行方法==》同时sql语句执行==》返回数据==》测试方法接收==》输出
 * 此期间需要传入数据和接受数据的集合、列表、元素、实体对象为载体
 */
public class MyBatisTest {

    @Test
    public void testSelectAll() throws IOException {
        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象,这三行代码是固定执行的，而且美执行添加一个测试方法
        //如果就创建一个sqlSessionFactory是很浪费内存的，所以可以写一个工具类让这行代码执行
        //把它放在工具类静态代码块中，编写返回sqlSessionFactory的方法，在这里在调用就行
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        List<Brand> selectA = brandMapper.selectAll();
        System.out.println(selectA);
        //五、释放资源
        sqlSession.close();
    }

    @Test
    public void testSelectById() throws IOException {

        //接收用户请求参数,浏览器传来
        int id =1;

        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        Brand selectA = mapper.selectById(id);
        System.out.println(selectA);
        //五、释放资源
        sqlSession.close();
    }


    /**
     *
     *  不知道哪里出错了，输出结果为空列表
     */
    @Test
    public void testSelectByCondition() throws IOException {

        //接收用户请求参数,浏览器传来
        int status = 1;
        String companyName = "华为";
        String brandName = "华为";


        //处理参数，模糊查询select * from table where col1 like '%参数%'
        companyName ="%" + companyName + "%";
        brandName = "%" + brandName+ "%";



        /*
        采用第二种方法时，需要封装对象(新建对象然后把参数传进去)
        Brand brand=new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);
         */


        //第三种方法
        Map map=new HashMap();
        //map.put("status",status);    //动态条件查询，能查询的前提下，参数传入个数可选
        map.put("companyName",companyName);
        map.put("brandName",brandName);


        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        /*
        第一种方法
        List<Brand> brands = brandMapper.selectByCondition(status, companyName, brandName);
         */
        /*
        第二种方法
        List<Brand> brands=brandMapper.selectByCondition(brand);
         */
        List<Brand> brands=brandMapper.selectByCondition(map);

        System.out.println(brands);
        //五、释放资源
        sqlSession.close();
    }






    @Test
    public void selectByConditionSingle() throws IOException {

        //接收用户请求参数,浏览器传来
        int status = 1;
        String companyName = "华为";
        String brandName = "华为";


        //处理参数，模糊查询select * from table where col1 like '%参数%'
        companyName ="%" + companyName + "%";
        brandName = "%" + brandName+ "%";

        //封装对象
        Brand brand=new Brand();
        brand.setStatus(status);
        brand.setCompanyName(companyName);
        brand.setBrandName(brandName);

        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        List<Brand> brands=brandMapper.selectByConditionSingle(brand);

        System.out.println(brands);
        //五、释放资源
        sqlSession.close();
    }


    /**
     * 添加注册
     * 值得注意的一点是mybatis，会自动开启事务但会关闭提交事务，所以想要事务成功提交，1、手动提交事务。2、开启自动提交
     * @throws IOException
     */
    @Test
    public void add() throws IOException {
        String companyName = "波导手机";
        String brandName = "波导";
        int status=1;
        int ordered=100;
        String description="手机中的战斗机";


        //封装对象
        Brand brand=new Brand();
        brand.setBrandName(brandName);
        brand.setCompanyName(companyName);
        brand.setOrdered(ordered);
        brand.setStatus(status);
        brand.setDescription(description);

        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        /*
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        自动提交
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        brandMapper.add(brand);

        //手动提交事务
        sqlSession.commit();

        //五、释放资源
        sqlSession.close();
    }


    /**
     * 根据id修改全部字段数据
     * @throws IOException
     */
    @Test
    public void update() throws IOException {
        String companyName = "波导手机";
        String brandName = "波导";
        int status=1;
        int ordered=100;
        String description="手机中的战斗机";
        //传入id
        int id=5;

        //封装对象
        Brand brand=new Brand();
        brand.setBrandName(brandName);
        brand.setCompanyName(companyName);
        brand.setOrdered(ordered);
        brand.setStatus(status);
        brand.setDescription(description);
        brand.setId(id);

        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        /*
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        自动提交
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        int c=brandMapper.update(brand);
        System.out.println(c);

        //手动提交事务
        sqlSession.commit();

        //五、释放资源
        sqlSession.close();
    }


    /**
     * 即删除id这一行数据
     * @throws IOException
     */
    @Test
    public void deleteById() throws IOException {

        //传入id
        int id=1;


        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        /*
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        自动提交
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        int c=brandMapper.deleteById(id);
        System.out.println(c);

        //手动提交事务
        sqlSession.commit();

        //五、释放资源
        sqlSession.close();
    }


    /**
     * 传入一个id【】数组删除多行数据
     * @throws IOException
     */
    @Test
    public void deleteByIds() throws IOException {

        //传入id
        int[] ids={1,2,3};


        //一、加载Mybatis的核心配置文件，获取sqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //二、获取sqlSession对象，用来执行sql语句
        /*
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        自动提交
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //三、获取UserMapper代理对象
        BrandMapper brandMapper = sqlSession.getMapper(BrandMapper.class);
        //四、执行sql语句
        int c=brandMapper.deleteByIds(ids);
        System.out.println(c);

        //手动提交事务
        sqlSession.commit();

        //五、释放资源
        sqlSession.close();
    }
}
