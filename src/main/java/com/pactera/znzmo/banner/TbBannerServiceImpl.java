package com.pactera.znzmo.banner;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.banner.dao.TbBannerMapper;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
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
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbBanner> selectBannerPages(Page<TbBanner> page, BannerQueryParam bannerQueryParam) {
		QueryWrapper<TbBanner> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbBanner.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(bannerQueryParam.getType().toString())) {
			queryWrapper.like(TbBanner.TYPE, bannerQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(bannerQueryParam.getStatus().toString())) {
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
		tbBanner.setStartTime(DateUtils.parseDate(bannerAddParam.getStartTime() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbBanner.setEndTime(DateUtils.parseDate(bannerAddParam.getEndTime() + " 23:59:59", DateUtils.FORMAT_ONE));
		tbBanner.setIsValid(IsValidEnum.YES.getKey());
		tbBanner.setStatus(StatusEnum.START_USE.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbBanner.setCreateId(user.getId());
		tbBanner.setCreateName(user.getName());
		tbBanner.setCreateTime(LocalDateTime.now());
		tbBanner.setUpdateId(user.getId());
		tbBanner.setUpdateName(user.getName());
		tbBanner.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbBanner);
		
		//新增上传文件
        for (UploadInfo uploadInfo : bannerAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbBanner.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(ReTypeEnum.BANNER.getKey());
            tbAttachment.setUploadVersion(uploadInfo.getUploadVersion());
            tbAttachment.setImgType(uploadInfo.getImgType());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
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
		tbBanner.setStartTime(DateUtils.parseDate(bannerUpdateParam.getStartTime() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbBanner.setEndTime(DateUtils.parseDate(bannerUpdateParam.getEndTime() + " 23:59:59", DateUtils.FORMAT_ONE));
		tbBanner.setStatus(StatusEnum.START_USE.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbBanner.setUpdateId(user.getId());
		tbBanner.setUpdateName(user.getName());
		tbBanner.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbBanner);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, tbBanner.getId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo uploadInfo : bannerUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbBanner.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(ReTypeEnum.BANNER.getKey());
            tbAttachment.setUploadVersion(uploadInfo.getUploadVersion());
            tbAttachment.setImgType(uploadInfo.getImgType());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
        }
		
	}

}
