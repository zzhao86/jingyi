package com.seglino.jingyi.dingtalk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto.BtnJsonList;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto.Form;
import com.seglino.jingyi.dingtalk.service.DingtalkMessageService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.taobao.api.ApiException;

@Service
public class DingtalkMessageServiceImpl implements DingtalkMessageService {
	/**
	 * 发送工作通知
	 * 
	 * @param workMsg 消息参数和内容
	 * @return
	 */
	@Override
	public OapiMessageCorpconversationAsyncsendV2Response sendWorkMessage(DingtalkWorkMessageDto workMsg) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.SendWorkMessage);
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype(workMsg.getMsgType().getType());
		switch (workMsg.getMsgType()) {
		case TEXT:
			OapiMessageCorpconversationAsyncsendV2Request.Text text = new OapiMessageCorpconversationAsyncsendV2Request.Text();
			text.setContent(workMsg.getText().getContent());
			msg.setText(text);
			break;
		case IMAGE:
			OapiMessageCorpconversationAsyncsendV2Request.Image image = new OapiMessageCorpconversationAsyncsendV2Request.Image();
			image.setMediaId(workMsg.getImage().getMediaId());
			msg.setImage(image);
			break;
		case VOICE:
			OapiMessageCorpconversationAsyncsendV2Request.Voice voice = new OapiMessageCorpconversationAsyncsendV2Request.Voice();
			voice.setDuration(workMsg.getVoice().getDuration());
			voice.setMediaId(workMsg.getVoice().getMediaId());
			msg.setVoice(voice);
			break;
		case FILE:
			OapiMessageCorpconversationAsyncsendV2Request.File file = new OapiMessageCorpconversationAsyncsendV2Request.File();
			file.setMediaId(workMsg.getFile().getMediaId());
			msg.setFile(file);
			break;
		case LINK:
			OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
			link.setMessageUrl(workMsg.getLink().getMessageUrl());
			link.setPicUrl(workMsg.getLink().getPicUrl());
			link.setText(workMsg.getLink().getText());
			link.setTitle(workMsg.getLink().getTitle());
			msg.setLink(link);
			break;
		case OA:
			// form
			List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<OapiMessageCorpconversationAsyncsendV2Request.Form>();
			for (Form form : workMsg.getOa().getBody().getForm()) {
				OapiMessageCorpconversationAsyncsendV2Request.Form f = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				f.setKey(form.getKey());
				f.setValue(form.getValue());
				forms.add(f);
			}
			// rich
			OapiMessageCorpconversationAsyncsendV2Request.Rich rich = new OapiMessageCorpconversationAsyncsendV2Request.Rich();
			rich.setNum(workMsg.getOa().getBody().getRich().getNum());
			rich.setUnit(workMsg.getOa().getBody().getRich().getUnit());
			// body
			OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
			body.setAuthor(workMsg.getOa().getBody().getAuthor());
			body.setContent(workMsg.getOa().getBody().getContent());
			body.setFileCount(workMsg.getOa().getBody().getFileCount());
			body.setForm(forms);
			body.setImage(workMsg.getOa().getBody().getImage());
			body.setRich(rich);
			body.setTitle(workMsg.getOa().getBody().getTitle());
			// head
			OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
			head.setBgcolor(workMsg.getOa().getHead().getBgcolor());
			head.setText(workMsg.getOa().getHead().getText());
			// oa
			OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();
			oa.setBody(body);
			oa.setHead(head);
			oa.setMessageUrl(workMsg.getOa().getMessageUrl());
			oa.setPcMessageUrl(workMsg.getOa().getPcMessageUrl());
			msg.setOa(oa);
			break;
		case MARKDOWN:
			OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
			markdown.setText(workMsg.getMarkdown().getText());
			markdown.setTitle(workMsg.getMarkdown().getTitle());
			msg.setMarkdown(markdown);
			break;
		case ACTION_CARD:
			List<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList> btnJsonList = new ArrayList<OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList>();
			for (BtnJsonList btnJson : workMsg.getActionCard().getBtnJsonList()) {
				OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList btn = new OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList();
				btn.setActionUrl(btnJson.getActionUrl());
				btn.setTitle(btnJson.getTitle());
				btnJsonList.add(btn);
			}
			
			OapiMessageCorpconversationAsyncsendV2Request.ActionCard actionCard = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
			actionCard.setBtnJsonList(btnJsonList);
			actionCard.setBtnOrientation(workMsg.getActionCard().getBtnOrientation());
			actionCard.setMarkdown(workMsg.getActionCard().getMarkdown());
			actionCard.setSingleTitle(workMsg.getActionCard().getSingleTitle());
			actionCard.setSingleUrl(workMsg.getActionCard().getSingleUrl());
			actionCard.setTitle(workMsg.getActionCard().getTitle());
			break;
		default:
			break;
		}

		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setAgentId(Long.parseLong(DingtalkConfig.AgentId));
		request.setUseridList(workMsg.getUseridList());
		request.setDeptIdList(workMsg.getDeptIdList());
		request.setToAllUser(workMsg.getToAllUser());
		request.setMsg(msg);
		request.setHttpMethod("POST");

		OapiMessageCorpconversationAsyncsendV2Response response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return response;
	}

}
