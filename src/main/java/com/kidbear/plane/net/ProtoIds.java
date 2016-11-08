package com.kidbear.plane.net;

/**
 * 
 * @ClassName: ProtoIds
 * @Description: 存储协议号，添加协议号时，既要定义静态常量的协议号，也要在init方法中调用regist注册，方便协议号的管理
 * @author 何金成
 * @date 2015年5月23日 下午4:34:41
 * 
 */
public class ProtoIds {

	/** 登录请求 **/
	public static final short TEST = 10000;
	public static final short ACCOUNT_LOGIN = 1;// 登录
	public static final short MAIN_SCENE_ENTER = 2;// 进入主场景
	public static final short MAIN_SCENE_MOVE = 3;// 主场景移动
	public static final short MAIN_SCENE_QUERY_ALL = 4;// 查询主场景所有玩家
	public static final short USER_ENTER_MAIN_SCENE = 5;// 其他玩家进入主场景
	public static final short USER_MOVE = 6;// 其他玩家移动
}
