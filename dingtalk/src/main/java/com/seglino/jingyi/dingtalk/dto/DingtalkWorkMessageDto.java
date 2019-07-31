package com.seglino.jingyi.dingtalk.dto;

import java.util.List;

public class DingtalkWorkMessageDto {
	/**
	 * 消息类型
	 */
	private MessageType msgType;
	/**
	 * 接收者的用户userid列表，最大列表长度：100
	 */
	private String useridList;
	/**
	 * 接收者的部门id列表，最大列表长度：20, 接收者是部门id下(包括子部门下)的所有用户
	 */
	private String deptIdList;
	/**
	 * 是否发送给企业全部用户
	 */
	private Boolean toAllUser = false;
	/**
	 * 卡片消息
	 */
	private ActionCard actionCard;
	/**
	 * 文件消息
	 */
	private File file;
	/**
	 * 图片消息
	 */
	private Image image;
	/**
	 * 链接消息
	 */
	private Link link;
	/**
	 * markdown消息
	 */
	private Markdown markdown;
	/**
	 * oa消息
	 */
	private OA oa;
	/**
	 * 文本消息
	 */
	private Text text;
	/**
	 * 语音消息
	 */
	private Voice voice;

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	public String getUseridList() {
		return useridList;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	public String getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(String deptIdList) {
		this.deptIdList = deptIdList;
	}

	public Boolean getToAllUser() {
		return toAllUser;
	}

	public void setToAllUser(Boolean toAllUser) {
		this.toAllUser = toAllUser;
	}

	public ActionCard getActionCard() {
		return actionCard;
	}

	public void setActionCard(ActionCard actionCard) {
		this.actionCard = actionCard;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Markdown getMarkdown() {
		return markdown;
	}

	public void setMarkdown(Markdown markdown) {
		this.markdown = markdown;
	}

	public OA getOa() {
		return oa;
	}

	public void setOa(OA oa) {
		this.oa = oa;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public static class Text {
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public static class Image {
		private String mediaId;

		public String getMediaId() {
			return this.mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
	}

	public static class Link {
		private String messageUrl;
		private String picUrl;
		private String text;
		private String title;

		public String getMessageUrl() {
			return messageUrl;
		}

		public void setMessageUrl(String messageUrl) {
			this.messageUrl = messageUrl;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static class File {
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
	}

	public static class Voice {
		private String duration;
		private String mediaId;

		public String getDuration() {
			return duration;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}

	}

	public static class Head {
		private String bgcolor;
		private String text;

		public String getBgcolor() {
			return bgcolor;
		}

		public void setBgcolor(String bgcolor) {
			this.bgcolor = bgcolor;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	public static class Body {
		private String author;
		private String content;
		private String fileCount;
		private List<Form> form;
		private String image;
		private Rich rich;
		private String title;

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getFileCount() {
			return fileCount;
		}

		public void setFileCount(String fileCount) {
			this.fileCount = fileCount;
		}

		public List<Form> getForm() {
			return form;
		}

		public void setForm(List<Form> form) {
			this.form = form;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Rich getRich() {
			return rich;
		}

		public void setRich(Rich rich) {
			this.rich = rich;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static class Form {
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public static class Rich {
		private String num;
		private String unit;

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

	}

	public static class OA {
		private Body body;
		private Head head;
		private String messageUrl;
		private String pcMessageUrl;

		public Body getBody() {
			return body;
		}

		public void setBody(Body body) {
			this.body = body;
		}

		public Head getHead() {
			return head;
		}

		public void setHead(Head head) {
			this.head = head;
		}

		public String getMessageUrl() {
			return messageUrl;
		}

		public void setMessageUrl(String messageUrl) {
			this.messageUrl = messageUrl;
		}

		public String getPcMessageUrl() {
			return pcMessageUrl;
		}

		public void setPcMessageUrl(String pcMessageUrl) {
			this.pcMessageUrl = pcMessageUrl;
		}

	}

	public static class Markdown {
		private String text;
		private String title;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static class BtnJsonList {
		private String actionUrl;
		private String title;

		public String getActionUrl() {
			return actionUrl;
		}

		public void setActionUrl(String actionUrl) {
			this.actionUrl = actionUrl;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

	public static class ActionCard {
		private List<BtnJsonList> btnJsonList;
		private String btnOrientation;
		private String markdown;
		private String singleTitle;
		private String singleUrl;
		private String title;

		public List<BtnJsonList> getBtnJsonList() {
			return btnJsonList;
		}

		public void setBtnJsonList(List<BtnJsonList> btnJsonList) {
			this.btnJsonList = btnJsonList;
		}

		public String getBtnOrientation() {
			return btnOrientation;
		}

		public void setBtnOrientation(String btnOrientation) {
			this.btnOrientation = btnOrientation;
		}

		public String getMarkdown() {
			return markdown;
		}

		public void setMarkdown(String markdown) {
			this.markdown = markdown;
		}

		public String getSingleTitle() {
			return singleTitle;
		}

		public void setSingleTitle(String singleTitle) {
			this.singleTitle = singleTitle;
		}

		public String getSingleUrl() {
			return singleUrl;
		}

		public void setSingleUrl(String singleUrl) {
			this.singleUrl = singleUrl;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}
}
