package com.kidbear.plane.manager.scene;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kidbear.plane.net.ProtoIds;
import com.kidbear.plane.net.ProtoMessage;
import com.kidbear.plane.net.ResultCode;
import com.kidbear.plane.net.socket.ChannelMgr;
import com.kidbear.plane.net.socket.WebSocketServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class MainSceneMgr {
	private static MainSceneMgr mainScene;
	private static final Logger logger = LoggerFactory
			.getLogger(MainSceneMgr.class);
	public ConcurrentHashMap<Long, MainSceneUser> users = new ConcurrentHashMap<Long, MainSceneUser>();
	public ChannelGroup userGroup = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);
	public static int maxWidth = 600;
	public static int maxheight = 400;

	private MainSceneMgr() {
	}

	public static MainSceneMgr getInstance() {
		if (null == mainScene) {
			mainScene = new MainSceneMgr();
		}
		return mainScene;
	}

	public void initData() {
		logger.info("MainScene initData");
	}

	public void enterScene(ChannelHandlerContext ctx, short typeid) {
		JSONArray ret = new JSONArray();
		double x = Math.random() * maxWidth;
		double y = Math.random() * maxheight;
		MainSceneUser user = new MainSceneUser();
		user.setUserid(ChannelMgr.getInstance().findByChannelId(
				ctx.channel().id().asShortText()).userId);
		user.setX(x);
		user.setY(y);
		users.put(user.getUserid(), user);
		ret.add(x);
		ret.add(y);
		WebSocketServerHandler.writeJSON(ctx, ProtoMessage.getResp(typeid,
				ret.toJSONString(), ResultCode.SUCCESS));
		userGroup.add(ctx.channel());
		// 广播所有
		userEnterMainSceneNotify(user);
	}

	public void move(ChannelHandlerContext ctx, JSONObject data, short typeid) {
		double x = data.getDoubleValue("x");
		double y = data.getDoubleValue("y");
		long userid = ChannelMgr.getInstance().findByChannelId(
				ctx.channel().id().asShortText()).userId;
		MainSceneUser user = users.get(userid);
		user.setX(x);
		user.setY(y);
		logger.info("user {} move to x:{},y:{}", userid, user.getX(),
				user.getY());
		userMoveNotify(user);
	}

	public void userMoveNotify(MainSceneUser user) {
		JSONArray ret = new JSONArray();
		ret.add(user.getUserid());
		ret.add(user.getX());
		ret.add(user.getY());
		WebSocketServerHandler.writeJSON(userGroup, ProtoMessage.getResp(
				ProtoIds.USER_MOVE, ret.toJSONString(), ResultCode.SUCCESS));
	}

	public void userEnterMainSceneNotify(MainSceneUser user) {
		JSONArray ret = new JSONArray();
		ret.add(user.getUserid());
		ret.add(user.getX());
		ret.add(user.getY());
		WebSocketServerHandler.writeJSON(
				userGroup,
				ProtoMessage.getResp(ProtoIds.USER_ENTER_MAIN_SCENE,
						ret.toJSONString(), ResultCode.SUCCESS));
	}

	public void queryAllUsers(ChannelHandlerContext ctx, Short typeid) {
		JSONArray ret = queryAllUsers();
		WebSocketServerHandler.writeJSON(ctx, ProtoMessage.getResp(typeid,
				ret.toJSONString(), ResultCode.SUCCESS));
	}

	private JSONArray queryAllUsers() {
		JSONArray ret = new JSONArray();
		for (Long userid : users.keySet()) {
			JSONArray userArr = new JSONArray();
			userArr.add(userid.longValue());
			userArr.add(users.get(userid).getX());
			userArr.add(users.get(userid).getY());
			ret.add(userArr);
		}
		return ret;
	}
}
