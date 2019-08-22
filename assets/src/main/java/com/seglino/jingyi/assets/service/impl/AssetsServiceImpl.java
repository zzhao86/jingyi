package com.seglino.jingyi.assets.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.assets.dao.AssetsDao;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;

@Service
public class AssetsServiceImpl extends BaseServiceImpl<AssetsDao, Assets> implements AssetsService {

}
