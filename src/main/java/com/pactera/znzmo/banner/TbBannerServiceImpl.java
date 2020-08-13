package com.pactera.znzmo.banner;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.banner.dao.TbBannerMapper;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.vo.banner.BannerAddParam;
import com.pactera.znzmo.vo.banner.BannerQueryParam;
import com.pactera.znzmo.vo.banner.BannerUpdateParam;
import com.pactera.znzmo.vo.common.UploadInfo;

/**
 * <p>
 * 轮播管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbBannerServiceImpl extends ServiceImpl<TbBannerMapper, TbBanner> implements TbBannerService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Override
	public IPage<TbBanner> selectBannerPages(Page<TbBanner> page, BannerQueryParam bannerQueryParam) {
		QueryWrapper<TbBanner> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbBanner.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(bannerQueryParam.getType().toString())) {
			queryWrapper.like(TbBanner.TYPE, bannerQueryParam.getType());
		}
		if(StringUtil.isNotEmpty(bannerQueryParam.getStatus().toString())) {
			queryWrapper.eq(TbBanner.STATUS, bannerQueryParam.getStatus());
		}
		queryWrapper.orderByDesc(TbBanner.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addBanner(BannerAddParam bannerAddParam) {
		TbBanner tbBanner = new TbBanner();
		tbBanner.setTitle(bannerAddParam.getTitle());
		tbBanner.setSort(bannerAddParam.getSort());
		tbBanner.setType(bannerAddParam.getType());
		tbBanner.setStartTime(bannerAddParam.getStartTime());
		tbBanner.setEndTime(bannerAddParam.getEndTime());
		tbBanner.setIsValid(IsValidEnum.YES.getKey());
		tbBanner.setStatus(StatusEnum.START_USE.getKey());
//		tbBanner.setCreateId(user.getUserId());
//		tbBanner.setCreateName(user.getUserName());
		tbBanner.setCreateTime(LocalDateTime.now());
//		tbBanner.setUpdateId(user.getUserId());
//		tbBanner.setUpdateName(user.getUserName());
		tbBanner.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbBanner);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : bannerAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbBanner.getId());
            tbAttachment.setAttachmentName(keyAndUrl.getFileName());
            tbAttachment.setAttachmentPath(keyAndUrl.getFile());
            tbAttachment.setPhysicalPath(keyAndUrl.getFile());
            tbAttachment.setAliasName(keyAndUrl.getRealName());
            tbAttachment.setReType(IsValidEnum.YES.getValue());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//            tbAttachment.setCreateId(user.getUserId());
//            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
        }
	}

	@Override
	public void updteBanner(BannerUpdateParam bannerUpdateParam) {
		TbBanner tbBanner = baseMapper.selectById(bannerUpdateParam.getBannerId());
		tbBanner.setTitle(bannerUpdateParam.getTitle());
		tbBanner.setSort(bannerUpdateParam.getSort());
		tbBanner.setType(bannerUpdateParam.getType());
		tbBanner.setStartTime(bannerUpdateParam.getStartTime());
		tbBanner.setEndTime(bannerUpdateParam.getEndTime());
		tbBanner.setStatus(StatusEnum.START_USE.getKey());
//		tbBanner.setUpdateId(user.getUserId());
//		tbBanner.setUpdateName(user.getUserName());
		tbBanner.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbBanner);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, tbBanner.getId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : bannerUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbBanner.getId());
            tbAttachment.setAttachmentName(keyAndUrl.getFileName());
            tbAttachment.setAttachmentPath(keyAndUrl.getFile());
            tbAttachment.setPhysicalPath(keyAndUrl.getFile());
            tbAttachment.setAliasName(keyAndUrl.getRealName());
            tbAttachment.setReType(IsValidEnum.YES.getValue());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//            tbAttachment.setCreateId(user.getUserId());
//            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
        }
		
	}

}
