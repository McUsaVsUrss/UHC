package net.minetopix.library.main.nms.classes;

import net.minetopix.library.main.utils.ReflectionUtil;

public enum REnumPlayerInfoAction {

	 ADD_PLAYER (0), UPDATE_GAME_MODE (1), UPDATE_LATENCY (2), UPDATE_DISPLAY_NAME (3), REMOVE_PLAYER (4);

	 private int index;
	 
	 private REnumPlayerInfoAction(int enumIndex) {
		 this.index = enumIndex;
	 }
	 
	 public Object getPlayerInfoAction() {
		 return ReflectionUtil.getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants()[index];
	 }
}
