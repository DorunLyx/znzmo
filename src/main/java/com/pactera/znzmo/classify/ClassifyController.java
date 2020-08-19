package com.pactera.znzmo.classify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.util.DataUtils;
import com.pactera.znzmo.vo.classify.ClassifyAddParam;
import com.pactera.znzmo.vo.classify.ClassifyDetailsVO;
import com.pactera.znzmo.vo.classify.ClassifyQueryDetailsParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryParam;
import com.pactera.znzmo.vo.classify.ClassifyUpdateParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ClassController
 * @Description：分类管理API
 * @author liyongxu 
 * @date 2020年8月12日 下午5:01:08 
 * @version 1.0.0 
 */
@Api(tags = "分类管理API", value = "分类管理API")
@RestController
@RequestMapping(value = "/classify")
public class ClassifyController extends BaseController{
	
	@Autowired
	private TbClassService tbClassService;
	
	public static final Logger logger = LoggerFactory.getLogger(ClassifyController.class);

	/**
	 * @Title: getClassifyTree 
	 * @Description: 分类树
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月19日 下午4:36:23 
	*/
	@ApiOperation(value = "分类树", httpMethod = "POST", notes = "分类树")
    @RequestMapping(value = "/getClassifyTree", method = {RequestMethod.POST})
    public JsonResp getClassifyTree() {
		Supplier<List<Map<String, Object>>> businessHandler = () -> {
			try {
				List<Map<String, Object>> list = new ArrayList<>();
				List<TbClass> tbClassList = tbClassService.selectByPid(0L);
				if(DataUtils.isNotEmpty(tbClassList)) {
					for (TbClass tbClass : tbClassList) {
						Map<String, Object> map = new LinkedHashMap<>();
						map.put("id", tbClass.getId());
						map.put("name", tbClass.getName());
						map.put("pid", tbClass.getPId());
						map.put("pname", tbClass.getPName());
						map.put("leve", tbClass.getLevel());
						map.put("sort", tbClass.getSort());
						map.put("children", getChildren(tbClass.getId()));
						list.add(map);
					}
				}
		        return list;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getChildren 
	 * @Description: 递归处理
	 * @param id
	 * @return List<Object>
	 * @author liyongxu
	 * @date 2020年8月19日 下午4:24:31 
	*/
	public List<Object> getChildren(Long id) {
		List<Object> list = new ArrayList<>();
        List<TbClass> treeMenu = tbClassService.selectByPid(id);
        for (TbClass tbClass : treeMenu) {
        	Map<String, Object> map = new LinkedHashMap<>();
        	map.put("id", tbClass.getId());
        	map.put("name", tbClass.getName());
        	map.put("leve", tbClass.getLevel());
        	map.put("pid", tbClass.getPId());
        	map.put("pname", tbClass.getPName());
        	map.put("sort", tbClass.getSort());
            map.put("children", getChildren(tbClass.getId()));
            list.add(map);
        }
        return list;
    }
	
	/**
	 * @Title: getClassifyList 
	 * @Description: 分类列表查询
	 * @param classifyQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月19日 下午4:37:19 
	*/
	@ApiOperation(value = "分类列表查询", httpMethod = "POST", notes = "分类列表查询")
	@RequestMapping(value = "/getClassifyList", method = {RequestMethod.POST})
	public JsonResp getClassifyList(
			@ApiParam(name="ClassifyQueryParam", value="分类列表筛选参数", required=false)@RequestBody ClassifyQueryParam classifyQueryParam) {
		Supplier<IPage<ClassifyDetailsVO>> businessHandler = () -> {
			try {
				List<ClassifyDetailsVO> classifyDetailsVOList = new ArrayList<ClassifyDetailsVO>();
				Page<TbClass> page = new Page<TbClass>(classifyQueryParam.getPageNo(), classifyQueryParam.getPageSize());
		        IPage<ClassifyDetailsVO> classifyDetailsPage =  new Page<ClassifyDetailsVO>(classifyQueryParam.getPageNo(), classifyQueryParam.getPageSize());
		        IPage<TbClass> iPage = tbClassService.selectClassifyList(page, classifyQueryParam);
				for (TbClass tbClass : iPage.getRecords()) {
					ClassifyDetailsVO classifyDetailsVO = new ClassifyDetailsVO();
					classifyDetailsVO.setClassifyName(tbClass.getName());
					classifyDetailsVO.setPId(tbClass.getPId());
					classifyDetailsVO.setPName(tbClass.getPName());
					classifyDetailsVO.setLevel(tbClass.getLevel());
					classifyDetailsVO.setSort(tbClass.getSort());
					classifyDetailsVOList.add(classifyDetailsVO);
				}
				classifyDetailsPage.setRecords(classifyDetailsVOList);
				classifyDetailsPage.setCurrent(iPage.getCurrent());
				classifyDetailsPage.setPages(iPage.getPages());
				classifyDetailsPage.setSize(iPage.getSize());
				classifyDetailsPage.setTotal(iPage.getTotal());
		        return classifyDetailsPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addClassify 
	 * @Description: 分类新增
	 * @param classifyAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:13:54 
	*/
	@ApiOperation(value = "分类新增", httpMethod = "POST", notes = "分类新增")
    @RequestMapping(value = "/addClassify", method = {RequestMethod.POST})
	public JsonResp addClassify(
			@ApiParam(name="classifyAddParam", value="分类新增参数", required=false)@RequestBody ClassifyAddParam classifyAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbClassService.addClassify(classifyAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getClassifyInfo 
	 * @Description: 分类详情
	 * @param classifyQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:23:04 
	*/
	@ApiOperation(value = "分类详情", httpMethod = "POST", notes = "分类详情")
    @RequestMapping(value = "/getClassifyInfo", method = {RequestMethod.POST})
    public JsonResp getClassifyInfo(
    		@ApiParam(name="classifyQueryDetailsParam", value="分类详情参数", required=false)@RequestBody ClassifyQueryDetailsParam classifyQueryDetailsParam) {
		Supplier<ClassifyDetailsVO> businessHandler = () ->{
			try {
				TbClass tbClass = tbClassService.getById(classifyQueryDetailsParam.getClassifyId());
				ClassifyDetailsVO classifyDetailsVO = new ClassifyDetailsVO();
				classifyDetailsVO.setClassifyName(tbClass.getName());
				classifyDetailsVO.setPId(tbClass.getPId());
				classifyDetailsVO.setPName(tbClass.getPName());
				classifyDetailsVO.setLevel(tbClass.getLevel());
				classifyDetailsVO.setSort(tbClass.getSort());
				return classifyDetailsVO;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteClassify 
	 * @Description: 分类编辑
	 * @param classifyUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:25:50 
	*/
	@ApiOperation(value = "分类编辑", httpMethod = "POST", notes = "分类编辑")
	@RequestMapping(value = "/updteClassify", method = {RequestMethod.POST})
	public JsonResp updteClassify(
			@ApiParam(name="classifyUpdateParam", value="分类编辑参数", required=false)@RequestBody ClassifyUpdateParam classifyUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbClassService.updteClassify(classifyUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: delectClassify 
	 * @Description: 分类删除
	 * @param classifyQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:31:33 
	*/
	@ApiOperation(value = "分类删除", httpMethod = "POST", notes = "分类删除")
    @RequestMapping(value = "/delectClassify", method = {RequestMethod.POST})
    public JsonResp delectClassify(
    		@ApiParam(name="classifyQueryDetailsParam", value="分类详情参数", required=false)@RequestBody ClassifyQueryDetailsParam classifyQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbClassService.delectClassify(classifyQueryDetailsParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
