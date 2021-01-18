package com.seven.admin.service.impl;

import com.seven.admin.bean.entity.SysNoticeEntity;
import com.seven.admin.bean.query.SysNoticeQuery;
import com.seven.admin.mapper.SysNoticeMapper;
import com.seven.comm.core.config.SevenQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seven.admin.service.SysNoticeService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;


/**
 * 通知公告表
 *
 * @author chendongdong
 * @version 1.0
 * @date 2021-01-04 10:43:24
 */

@Slf4j
@Service("sysNoticeService")
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public SysNoticeEntity selectNoticeById(Long noticeId) {
        return sysNoticeMapper.selectById(noticeId);
    }

    @Override
    public List<SysNoticeEntity> selectNoticeList(SysNoticeQuery notice) {
        SevenQueryWrapper<SysNoticeEntity> queryWrapper = new SevenQueryWrapper<>();
        queryWrapper.slike("notice_title", notice.getNoticeTitle());
        queryWrapper.seq("notice_type", notice.getNoticeType());
        queryWrapper.slike("create_by", notice.getCreateBy());
        return sysNoticeMapper.selectList(queryWrapper);
    }

    @Override
    public int insertNotice(SysNoticeEntity notice) {
        return sysNoticeMapper.insert(notice);
    }

    @Override
    public int updateNotice(SysNoticeEntity notice) {
        return sysNoticeMapper.updateById(notice);
    }

    @Override
    public int deleteNoticeById(Long noticeId) {
        return sysNoticeMapper.deleteById(noticeId);
    }

    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return sysNoticeMapper.deleteBatchIds(Arrays.asList(noticeIds));
    }
}
