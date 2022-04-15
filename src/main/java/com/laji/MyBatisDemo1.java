package com.laji;

import com.laji.mapper.BrandMapper;
import com.laji.pojo.Brand;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatisDemo1 {
    public static void main(String[] args)throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        BrandMapper mapper = session.getMapper(BrandMapper.class);
        List<Brand> selectA = mapper.selectAll();
        System.out.println(selectA);
        session.close();
    }
}
