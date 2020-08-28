package com.pactera.znzmo.personal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.model.TbThreedModel;
import com.pactera.znzmo.model.dao.TbThreedModelMapper;
import com.pactera.znzmo.personal.dao.TbDownloadRecordsMapper;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.personal.DownloadRecordsAddParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsQueryParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsVO;

/**
 * <p>
 * 下载记录表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-26
 */
@Service
public class TbDownloadRecordsServiceImpl extends ServiceImpl<TbDownloadRecordsMapper, TbDownloadRecords> implements TbDownloadRecordsService {

	@Autowired
	private TbThreedModelMapper tbThreedModelMapper;
	
	@Autowired
	private TbSuModelMapper tbSuModelMapper;
	
	@Autowired
	private TbDrawingSchemeMapper tbDrawingSchemeMapper;
	
	@Autowired
	private TbHdMappingMapper tbHdMappingMapper;
	
	@Autowired
	private TbDatabaseMapper tbDatabaseMapper;
	
	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Override
	public IPage<DownloadRecordsVO> selectDownloadRecordsPages(Page<TbDownloadRecords> page,
			DownloadRecordsQueryParam downloadRecordsQueryParam){
		QueryWrapper<TbDownloadRecords> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbDownloadRecords.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(downloadRecordsQueryParam.getUserId())) {
			queryWrapper.eq(TbDownloadRecords.CREATE_ID, downloadRecordsQueryParam.getUserId());
		}
		if(StringUtils.isNotEmpty(downloadRecordsQueryParam.getType())) {
			queryWrapper.eq(TbDownloadRecords.RE_TYPE, downloadRecordsQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(downloadRecordsQueryParam.getStartTime())) {
			queryWrapper.ge(TbDownloadRecords.UPDATE_TIME, downloadRecordsQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(downloadRecordsQueryParam.getEndTime())) {
			queryWrapper.le(TbDownloadRecords.UPDATE_TIME, downloadRecordsQueryParam.getEndTime() + " 23:59:59");
		}
		queryWrapper.orderByDesc(TbDownloadRecords.UPDATE_TIME);
		IPage<TbDownloadRecords> selectPage = baseMapper.selectPage(page,queryWrapper);
		return this.selectReInfo(selectPage, downloadRecordsQueryParam);
	}
	
	/**
	 * @Title: selectReInfo 
	 * @Description: 获取我的下载业务数据
	 * @param iPage
	 * @return IPage<ExamineListVO>
	 * @author liyongxu
	 * @date 2020年8月10日 上午11:02:08 
	*/
	private IPage<DownloadRecordsVO> selectReInfo(IPage<TbDownloadRecords> iPage, DownloadRecordsQueryParam downloadRecordsQueryParam) {
		List<DownloadRecordsVO> examineList = new ArrayList<DownloadRecordsVO>();
		IPage<DownloadRecordsVO> downloadRecordsPage =  new Page<DownloadRecordsVO>(downloadRecordsQueryParam.getPageNo(), downloadRecordsQueryParam.getPageSize());
		for (TbDownloadRecords tbDownloadRecords : iPage.getRecords()) {
			DownloadRecordsVO downloadRecordsVO = new DownloadRecordsVO();
			downloadRecordsVO.setExamineId(tbDownloadRecords.getId().toString());
			downloadRecordsVO = this.selectReInfoByType(downloadRecordsVO, tbDownloadRecords.getReId(), tbDownloadRecords.getReType());
			examineList.add(downloadRecordsVO);
		}
		downloadRecordsPage.setRecords(examineList);
		downloadRecordsPage.setCurrent(iPage.getCurrent());
		downloadRecordsPage.setPages(iPage.getPages());
		downloadRecordsPage.setSize(iPage.getSize());
		downloadRecordsPage.setTotal(iPage.getTotal());		
		return downloadRecordsPage;
	}
	
	/**
	 * @Title: selectReInfoByType 
	 * @Description: 根据业务id跟类型查询业务信息
	 * @author liyongxu
	 * @param downloadRecordsVO 
	 * @param reType 
	 * @param reId 
	 * @date 2020年8月10日 上午11:09:29 
	*/
	private DownloadRecordsVO selectReInfoByType(DownloadRecordsVO downloadRecordsVO, Long reId, Integer reType) {
		if(reType == ReTypeEnum.MODEL.getKey()) {
			TbThreedModel tbThreedModel = tbThreedModelMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbThreedModel.getMainGraph());
	        if(tbAttachment != null) {
	        	downloadRecordsVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			downloadRecordsVO.setCode(tbThreedModel.getCode());
			downloadRecordsVO.setPrimaryClassName(tbThreedModel.getPrimaryClassName());
			downloadRecordsVO.setClassName(tbThreedModel.getSecondaryClassName());
			downloadRecordsVO.setStyleName(tbThreedModel.getStyleName());
			downloadRecordsVO.setTitle(tbThreedModel.getTitle());
			downloadRecordsVO.setType(tbThreedModel.getType());
			downloadRecordsVO.setPrice(tbThreedModel.getPrice());
			downloadRecordsVO.setStatus(tbThreedModel.getStatus());
			downloadRecordsVO.setUploadUser(tbThreedModel.getCreateName());
			downloadRecordsVO.setUploadTime(DateUtils.localDateTimeToString(tbThreedModel.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.SUMODEL.getKey()) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbSuModel.getMainGraph());
	        if(tbAttachment != null) {
	        	downloadRecordsVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			downloadRecordsVO.setCode(tbSuModel.getCode());
			downloadRecordsVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
			downloadRecordsVO.setClassName(tbSuModel.getSecondaryClassName());
			downloadRecordsVO.setStyleName(tbSuModel.getStyleName());
			downloadRecordsVO.setTitle(tbSuModel.getTitle());
			downloadRecordsVO.setType(tbSuModel.getType());
			downloadRecordsVO.setPrice(tbSuModel.getPrice());
			downloadRecordsVO.setStatus(tbSuModel.getStatus());
			downloadRecordsVO.setUploadUser(tbSuModel.getCreateName());
			downloadRecordsVO.setUploadTime(DateUtils.localDateTimeToString(tbSuModel.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.DRAWING.getKey()) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbDrawing.getMainGraph());
	        if(tbAttachment != null) {
	        	downloadRecordsVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			downloadRecordsVO.setCode(tbDrawing.getCode());
			downloadRecordsVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
			downloadRecordsVO.setClassName(tbDrawing.getSecondaryClassName());
			downloadRecordsVO.setStyleName(tbDrawing.getStyleName());
			downloadRecordsVO.setTitle(tbDrawing.getTitle());
			downloadRecordsVO.setType(tbDrawing.getType());
			downloadRecordsVO.setPrice(tbDrawing.getPrice());
			downloadRecordsVO.setStatus(tbDrawing.getStatus());
			downloadRecordsVO.setUploadUser(tbDrawing.getCreateName());
			downloadRecordsVO.setUploadTime(DateUtils.localDateTimeToString(tbDrawing.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.HD.getKey()) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbHdMapping.getMainGraph());
	        if(tbAttachment != null) {
	        	downloadRecordsVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			downloadRecordsVO.setCode(tbHdMapping.getCode());
			downloadRecordsVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
			downloadRecordsVO.setClassName(tbHdMapping.getSecondaryClassName());
			downloadRecordsVO.setStyleName(tbHdMapping.getStyleName());
			downloadRecordsVO.setTitle(tbHdMapping.getTitle());
			downloadRecordsVO.setType(tbHdMapping.getType());
			downloadRecordsVO.setPrice(tbHdMapping.getPrice());
			downloadRecordsVO.setStatus(tbHdMapping.getStatus());
			downloadRecordsVO.setUploadUser(tbHdMapping.getCreateName());
			downloadRecordsVO.setUploadTime(DateUtils.localDateTimeToString(tbHdMapping.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.DATABASE.getKey()) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbDatabase.getMainGraph());
	        if(tbAttachment != null) {
	        	downloadRecordsVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			downloadRecordsVO.setCode(tbDatabase.getCode());
			downloadRecordsVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
			downloadRecordsVO.setClassName(tbDatabase.getSecondaryClassName());
			downloadRecordsVO.setStyleName(tbDatabase.getStyleName());
			downloadRecordsVO.setTitle(tbDatabase.getTitle());
			downloadRecordsVO.setType(tbDatabase.getType());
			downloadRecordsVO.setPrice(tbDatabase.getPrice());
			downloadRecordsVO.setStatus(tbDatabase.getStatus());
			downloadRecordsVO.setUploadUser(tbDatabase.getCreateName());
			downloadRecordsVO.setUploadTime(DateUtils.localDateTimeToString(tbDatabase.getCreateTime(), DateUtils.DATE_FORMAT));
		}
		return downloadRecordsVO;
	}

	@Override
	public void addDownloadRecords(DownloadRecordsAddParam downloadRecordsAddParam, SysUser user) {
		QueryWrapper<TbDownloadRecords> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbDownloadRecords.IS_VALID, IsValidEnum.YES.getKey())
			.eq(TbDownloadRecords.USER_ID, user.getId())
			.eq(TbDownloadRecords.RE_ID, downloadRecordsAddParam.getReId());
		TbDownloadRecords tbDownloadRecords = baseMapper.selectOne(queryWrapper);
		if(tbDownloadRecords != null) {
			tbDownloadRecords.setDownloadNum(tbDownloadRecords.getDownloadNum() + 1);
			tbDownloadRecords.setUpdateTime(LocalDateTime.now());
	        baseMapper.updateById(tbDownloadRecords);
		}else {
			TbDownloadRecords tbDownloadRecordsNew = new TbDownloadRecords();
			tbDownloadRecordsNew.setUserId(user.getId());
			tbDownloadRecordsNew.setUserName(user.getName());
			tbDownloadRecordsNew.setReId(Long.valueOf(downloadRecordsAddParam.getReId()));
			tbDownloadRecordsNew.setReType(downloadRecordsAddParam.getReType());
			tbDownloadRecordsNew.setDownloadNum(1);
			tbDownloadRecordsNew.setIsValid(IsValidEnum.YES.getKey());
			
			tbDownloadRecordsNew.setCreateId(user.getId());
			tbDownloadRecordsNew.setCreateName(user.getName());
			tbDownloadRecordsNew.setCreateTime(LocalDateTime.now());
			tbDownloadRecordsNew.setUpdateId(user.getId());
			tbDownloadRecordsNew.setUpdateName(user.getName());
			tbDownloadRecordsNew.setUpdateTime(LocalDateTime.now());
	        baseMapper.insert(tbDownloadRecordsNew);
		}
	}

}
