package com.kidbear.plane.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidbear.plane.manager.account.AccountMgr;
import com.kidbear.plane.manager.junzhu.JunZhuMgr;
import com.kidbear.plane.manager.scene.MainSceneMgr;
import com.kidbear.plane.net.ProtoIds;
import com.kidbear.plane.net.ProtoMessage;
import com.kidbear.plane.net.socket.ChannelMgr;

/**
 * @ClassName: Router
 * @Description: 消息路由分发
 * @author 何金成
 * @date 2015年12月14日 下午7:12:57
 * 
 */
public class Router {
	private static Router router = new Router();
	public Logger logger = LoggerFactory.getLogger(Router.class);
	public AccountMgr accountMgr;
	public JunZhuMgr junZhuMgr;
	public ChannelMgr channelMgr;
	public MainSceneMgr mainSceneMgr;

	private Router() {

	}

	public void initMgr() {
		accountMgr = AccountMgr.getInstance();
		junZhuMgr = JunZhuMgr.getInstance();
		channelMgr = ChannelMgr.getInstance();
		mainSceneMgr = MainSceneMgr.getInstance();
	}

	public static Router getInstance() {
		if (null == router) {
			router = new Router();
		}
		return router;
	}

	public void initCsvData() {// 初始化Csv
		junZhuMgr.initCsvData();
	}

	public void initData() {// 初始化数值
		accountMgr.initData();
		junZhuMgr.initData();
		mainSceneMgr.initData();
	}

	/**
	 * @Title: route
	 * @Description: 消息路由分发
	 * @param val
	 * @param ctx
	 *            void
	 * @throws
	 */
	public void route(String val, ChannelHandlerContext ctx) {
		ProtoMessage msg = JSON.parseObject(val, ProtoMessage.class);
		Short typeid = msg.getTypeid();
		JSONObject data = msg.getData();
		switch (typeid) {
		case ProtoIds.ACCOUNT_LOGIN:
			accountMgr.login(ctx, typeid);
			break;
		case ProtoIds.MAIN_SCENE_ENTER:
			mainSceneMgr.enterScene(ctx, typeid);
			break;
		case ProtoIds.MAIN_SCENE_MOVE:
			mainSceneMgr.move(ctx, data, typeid);
			break;
		case ProtoIds.MAIN_SCENE_QUERY_ALL:
			mainSceneMgr.queryAllUsers(ctx, typeid);
		default:
			break;
		}
	}

	public void test(ProtoMessage msg, ChannelHandlerContext ctx) {
		// logger.info("收到客户端的测试消息:");
		// logger.info("id:" + msg.getId());
		// logger.info("msg:" +
		// JsonUtils.objectToJson(msg.getData(TestReq.class)));
		// JSONObject object = new JSONObject();
		// object.put("msg", "服务器收到测试消息");
		// ProtoMessage message = new ProtoMessage(ProtoIds.TEST, object);
		// HttpHandler.writeJSON(ctx, message);
	}
}
