package com.kidbear.plane.net.socket;

import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidbear.plane.core.GameInit;
import com.kidbear.plane.notification.ServerNotify;

/**
 * @ClassName: ChannelMgr
 * @Description: Channel管理类
 * @author 何金成
 * @date 2015年12月25日 下午3:34:06
 * 
 */
public class ChannelMgr {
	public Logger logger = LoggerFactory.getLogger(ChannelMgr.class);
	public ConcurrentHashMap<String, ChannelUser> channelMap;
	private AtomicLong channelIdGen;
	private static ChannelMgr inst;

	private ChannelMgr() {
		channelMap = new ConcurrentHashMap<String, ChannelUser>();
		channelIdGen = new AtomicLong(0);
	}

	public static ChannelMgr getInstance() {
		if (inst == null) {
			inst = new ChannelMgr();
		}
		return inst;
	}

	/**
	 * @Title: addChannelUser
	 * @Description: channel管理
	 * @param ctx
	 * @param userId
	 * @return ChannelUser
	 * @throws
	 */
	public ChannelUser addChannelUser(ChannelHandlerContext ctx, Long userid) {
		// Long channelId =
		// Long.valueOf(ChannelMgr.getInstance().genChannelId());
		String channelId = ctx.channel().id().asShortText();
		ChannelUser ret = new ChannelUser();
		ret.channelId = channelId;
		ret.ctx = ctx;
		ret.userId = userid;
		synchronized (channelMap) {
			channelMap.put(channelId, ret);
		}
		return ret;
	}

	/**
	 * @Title: closeAllChannel
	 * @Description: 关闭所有Channel void
	 * @throws
	 */
	public void closeAllChannel() {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				it.remove();
				u.ctx.close();
			}
		}
		logger.info("关闭所有channel");
	}

	/**
	 * @Title: removeChannel
	 * @Description: 移除channel
	 * @param ctx
	 *            void
	 * @throws
	 */
	public void removeChannel(ChannelHandlerContext ctx) {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				if (u.ctx.equals(ctx)) {
					it.remove();
					ctx.close();
					// 离线通知登录服务器
					// ServerNotify
					// .logout((int) (u.junZhuId - GameInit.serverId) / 1000);
				}
			}
		}
	}

	/**
	 * @Title: findByJunZhuId
	 * @Description: 根据君主id找到ChannelUser
	 * @param junZhuId
	 * @return ChannelUser
	 * @throws
	 */
	public ChannelUser findByJunZhuId(Long junZhuId) {
		synchronized (channelMap) {
			Iterator<ChannelUser> it = channelMap.values().iterator();
			while (it.hasNext()) {
				ChannelUser u = it.next();
				Long v = u.userId;
				if (v != null && v.longValue() == junZhuId.longValue()) {
					return u;
				}
			}
		}
		return null;
	}

	public ChannelUser findByChannelId(String channelId) {
		ChannelUser user = channelMap.get(channelId);
		return user;
	}

	/**
	 * @Title: getChannel
	 * @Description: 根据君主id获取Channel
	 * @param junZhuId
	 * @return ChannelHandlerContext
	 * @throws
	 */
	public ChannelHandlerContext getChannel(Long junZhuId) {
		ChannelUser cu = findByJunZhuId(junZhuId);
		if (cu == null) {
			return null;
		}
		return cu.ctx;
	}

	/**
	 * @Title: getAllChannels
	 * @Description: 获取所有Channel
	 * @return List<ChannelUser>
	 * @throws
	 */
	public List<ChannelUser> getAllChannels() {
		List<ChannelUser> list = new LinkedList<ChannelUser>();
		synchronized (channelMap) {
			for (ChannelUser user : channelMap.values()) {
				list.add(user);
			}
		}
		return list;
	}

	public long genChannelId() {
		return channelIdGen.getAndIncrement();
	}
}
