package com.laji.mapper;

import com.laji.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BrandMapper {

    /**
     * 查看所有
     */
    List<Brand> selectAll();

    /**
     * 查看详情，根据id查询
     */

    Brand selectById(int id);

    /**
     * 条件查询
     *  *参数接收
     *      1、散装参数：如果传入的产生是单个元素，需要使用@Param("sql参数占位符名称")
     *      2、对象参数
     *      3、map集合参数
     */


    /*
    List<Brand> selectByCondition(@Param("status") int status, @Param("companyName") String companyName, @Param("brandName") String brandName);


    List<Brand> selectByCondition(Brand brand);



     */

    List<Brand> selectByCondition(Map map);




    /**
     * 单条件动态查询
     * @return
     */
    List<Brand> selectByConditionSingle(Brand brand);


    /**
     * 添加注册
     * @param brand
     */
    void add(Brand brand);


    /**
     * 修改
     * @param brand
     * @return
     */
    int update(Brand brand);


    /**
     * 传入一个删除id这一行数据
     * @param id
     * @return
     */
    int deleteById(int id);


    /**
     * 传入一个id【】数组删除多行数据
     * @param ids
     * @return
     * 关于@param这个标签起到给参数重新定义名称的作用
     *      单个参数：单个参数又可以是如下类型
     *          POJO 类型可直接使用不会转化为map集合
     *          Map 集合类型 可直接使用
     *          Collection 集合类型 有必要重命名
     *          List 集合类型 有必要重命名
     *          Array 类型 有必要重命名
     *
     *      多个参数：
     *          User select(@Param("username") String username,@Param("password") String password) 有必要重命名
     *
     * 详情看getNameParams类，这个类就是mybatis负责封装传入参数的类
     * mybatis会主动将Collection、List、Array、多个参数，封装成一个Map集合
     *     *默认:array=数组、array=元素值、List=列表，这种种形式
     *     *此处使用@param注解把map集合key值的名称array改为ids
     *     如果没有改名称则在sql语句中collection="ids"要改为collection="array"这辨识度不高且有几个数组时不以区分
     */
    int deleteByIds(@Param("ids") int[] ids);
}

