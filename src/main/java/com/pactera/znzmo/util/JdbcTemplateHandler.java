/*
 * File name: JdbcTemplateHandler.java
 * Product:lionking
 * Version: 1.0
 * Copyright 2009-2015 Pactera Technology. Co. Ltd. All Rights Reserved.
 */
package com.pactera.znzmo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;


/**
 * Jdbctemplate辅助类
 * @ClassName：JdbcTemplateHandler
 * @Description：Jdbctemplate辅助类
 * @author pactera
 * @date 2018年10月19日 下午5:25:08
 * @version 1.0.0
 */
@Component
public class JdbcTemplateHandler {

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;



	/**
	 * 自定义通用分页方法
	 */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
	public Page getPageData(String sql, int pageNo, int pageSize) throws Exception {
        //查询总数sql
        String countSql = "select count(*) from (" + sql + ") cnt";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);

        //查询分页sql
        String pageSql = sql + " limit " + (pageNo - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql);

        //检查数据从首页查询
        if(pageNo!=1 && resultMapList.isEmpty()){
            pageNo =1;
            String tmpPageSql = sql + " limit " + (pageNo - 1) * pageSize + "," + pageSize;
            resultMapList  = jdbcTemplate.queryForList(tmpPageSql);
        }

        //分页对象
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize);
        Page page = new Page(resultMapList, pageRequest, totalCount);

        System.out.println(sql);

        return page;
	}*/

    /**
     * 自定义通用分页方法--不自动跳转到首页
     * @author wangdongting
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
    public Page getPageDataNoHomePage(String sql, int pageNo, int pageSize) throws Exception {


        //查询总数sql
        String countSql = "select count(*) from (" + sql + ") cnt";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);

        //查询分页sql
        String pageSql = sql + " limit " + (pageNo - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql);

        //分页对象
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize);
        Page page = new Page(resultMapList, pageRequest, totalCount);

        System.out.println(sql);


        return page;
    }*/

    /**
     * 自定义通用分页方法
     * 适用于Oralce
     */
   /* @SuppressWarnings({ "rawtypes", "unchecked" })
    @Transactional(readOnly = true)
    public Page getPageDataByOracle(String sql, int pageNo, int pageSize) throws Exception {



        String countSql = "select count(*) from (" + sql + ") cnt";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);

        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        int startrow = (pageNo - 1) * pageSize;

        String runsql = "select * from (select a.*, rownum r from (" + sql + ") a where rownum <= " + (startrow + pageSize) + ") where r > " + startrow;


        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        maps = jdbcTemplate.queryForList(runsql);


        PageRequest pageRequest = buildPageRequest(pageNo, pageSize);
        Page pageView = new Page(maps, pageRequest, totalCount);


        return pageView;
    }*/

    /**
     * @Title: getPageData
     * @Description: SQL Server 分页方法,
     * 	需要将order by排序参数数据传入orderParamStr,sql中不能添加order by排序
     * 	order by排序字段如果使用了别名需要传入别名
     * @param dataSql 业务sql
     * @param pageNo 分页页码
     * @param pageSize 分页大小
     * @param orderParamStr 排序参数，此处必填，格式：PayformName ASC, PayFormGUID：所需排序的字段
     * @return Page
     * @author LiuGuiChao
     * @date 2018年11月29日 下午6:57:11
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
	public Page getPageData(String dataSql, int pageNo, int pageSize, String orderParamStr) {

    	String countSql = "SELECT COUNT(*) FROM (" + dataSql + ") CNT";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);

        //查询分页SQL
        StringBuilder pageSql = new StringBuilder();
        pageSql.append(" SELECT * FROM ");
        pageSql.append(" ( ");
    	pageSql.append(" SELECT *, ");
    	pageSql.append(" ROW_NUMBER () OVER ( ORDER BY ").append(orderParamStr).append(") AS Row");
    	pageSql.append(" FROM ( ");
    	pageSql.append(dataSql);
    	pageSql.append(" ) AS t1 ");
        pageSql.append(" ) AS t2 ");
        //计算分页算法
        pageSql.append("  WHERE t2.Row BETWEEN (").append(pageSize).append(" * (").append(pageNo-1).append("))+1 ").append("AND (").append(pageSize).append(" * ").append(pageNo).append(")");
        System.out.println(pageSql.toString());

        List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql.toString());

        Page page = new Page();
        page.setTotal(totalCount);
        page.setRecords(resultMapList);
        page.setSize(pageSize);
        page.setCurrent(pageNo);

        //分页对象
        return page;
    }

    /**
     * @Title: getPageData
     * @Description: SQL Server 分页方法,待验证
     * @param dataSql 业务sql
     * @param pageNo 分页页码
     * @param pageSize 分页大小
     * @return Page
     * @author LiuGuiChao
     * @date 2018年11月29日 下午6:57:11
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
	public Page getMybatisPageData(String dataSql, int pageNo, int pageSize) {

        //分离order by
        String orderInfo = "";
        String sql = "";

        boolean contentorder = dataSql.contains("order by");
        boolean contentORDER = dataSql.contains("ORDER BY");
        String countSQL = "";
        if(contentorder) {
        	orderInfo = dataSql.substring(dataSql.lastIndexOf("order by"));
        	String[] split = dataSql.split(orderInfo);
        	sql = split[0];
        	countSQL = split[0];
        }else if(contentORDER) {
        	orderInfo = dataSql.substring(dataSql.lastIndexOf("order by"));
        	String[] split = dataSql.split(orderInfo);
        	sql = split[0];
        	countSQL = split[0];
        }else {
        	orderInfo = "ORDER BY CURRENT_TIMESTAMP";
        	sql = dataSql;
        	countSQL = dataSql;
        }
        boolean contentselect = dataSql.contains("select");
        if(contentselect) {
        	String[] split = sql.split("select");
        	sql = split[1];
        }else {
        	String[] split = sql.split("SELECT");
        	sql = split[1];
        }

        boolean contentdistinct = dataSql.contains("distinct");
        boolean contentDISTINCT = dataSql.contains("DISTINCT");
        if(contentdistinct) {
        	String[] split = sql.split("distinct");
        	sql = split[1];
        }else if(contentDISTINCT){
        	String[] split = sql.split("DISTINCT");
        	sql = split[1];
        }

        //查询分页SQL
        StringBuilder pageSql = new StringBuilder();
        pageSql.append("WITH query AS (SELECT ");
        if(contentdistinct) {
        	pageSql.append("distinct");
        }else if(contentDISTINCT){
        	pageSql.append("DISTINCT");
        }
        pageSql.append(" TOP 100 PERCENT ROW_NUMBER() OVER (");
    	pageSql.append(orderInfo).append(") as __row_number__, ").append(sql);

    	//计算分页算法
        pageSql.append(") SELECT * FROM query WHERE __row_number__ BETWEEN (");
        pageSql.append(pageSize).append(" * (").append(pageNo-1).append("))+1 ");
        pageSql.append("AND (").append(pageSize).append(" * ").append(pageNo).append(") ORDER BY __row_number__");
        System.out.println(pageSql.toString());

        List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql.toString());

        //totalCount
    	String countSql = "SELECT COUNT(1) FROM (" + countSQL + ") TOTAL";
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalCount", totalCount);
        resultMapList.add(map);

        Page page = new Page();
        page.setTotal(totalCount);
        page.setRecords(resultMapList);
        page.setSize(pageSize);
        page.setCurrent(pageNo);

        //分页对象
        return page;
    }

    /**
	 * 自定义通用分页方法
	 */
    /**
     * @Title: getSellPageData
     * @Description: 获取销售系统分页
     * @param countSql
     * @param pageSql
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception Page
     * @author pactera
     * @date 2018年11月27日 下午4:19:15
    */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @Transactional(readOnly = true)
	public Page getPageData(String countSql, String pageSql, int pageNo, int pageSize) throws Exception {

    	//查询总数SQL
        Long totalCount = jdbcTemplate.queryForObject(countSql, Long.class);
        //查询分页SQL
        List<Map<String, Object>> resultMapList  = jdbcTemplate.queryForList(pageSql);

        //分页对象
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize);
        Page page = new Page(resultMapList, pageRequest, totalCount);

        return page;
	}*/

	/**
	 * 创建分页请求.
	 */
	/*@Transactional(readOnly = true)
	public PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, null);
	}*/

	/**
	 *
	 * 原生SQL查询方法
	 *
	 * @param sql nativeSQL
	 *
	 * @return List<Map<String, Object>>
	 * @author lijing
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> queryForMapList(String sql) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		System.out.println(sql);
		list = jdbcTemplate.queryForList(sql);
		return list;
	}

	/**
	 * 原生SQL查询一条记录
	 *
	 * @param sql nativeSQL
	 * @return
	 * @throws Exception
	 * @author lijing
	 */
	@Transactional(readOnly = true)
	public Object queryForObject(String sql) throws Exception {
		Object obj = (Object) jdbcTemplate.queryForObject(sql, Object.class);
		return obj;
	}
	public int updateForSql(String sql) throws Exception {
		int num = jdbcTemplate.update(sql);
		return num;
	}
	/**
	 *
	  * 原生sql查询一条记录
	  * @param sql
	  * @return
	  * @throws Exception
	  * @author  wangting　2016年3月29日
	  *
	 */
	public Map<String,Object> queryForMap(String sql)throws Exception{
	    Map<String,Object> map=jdbcTemplate.queryForMap(sql);
	    return map;
	}

	/**
	 *
	  * 原生sql查询，返回list<String>
	  * @param sql
	  * @return
	  * @throws Exception
	  * @author  wangchaojie　2016年7月18日
	  *
	 */
	public List<String> queryForList(String sql) throws Exception{
	    List<String> list = jdbcTemplate.queryForList(sql, String.class);
	    return list;
	}

	/**
	  * 批量更新方法
	  * @param sql
	  * @param batchArgs
	  * @return
	  * @throws Exception
	  * @author  wangshuai　2016年5月11日
	 */
	public int[] batchUpdate(String sql,List<Object[]>batchArgs) throws Exception{
	    return jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	/**
	 *
	  * 执行sql语句
	  * @param sql
	  * @throws Exception
	  * @author   wangting　2016年5月25日
	  *
	 */
	public void execute(String sql) throws Exception{
	    jdbcTemplate.execute(sql);
	}
}
