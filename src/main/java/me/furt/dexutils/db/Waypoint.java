package me.furt.dexutils.db;

import me.furt.dexutils.DexUtils;
import me.furt.dexutils.util.Location;
import me.furt.mexdb.MexDB;
import me.furt.mexdb.exception.EmptyIndexException;
import me.furt.mexdb.system.Entry;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Furt on 6/23/2016.
 */
public class Waypoint {

	public Location loc;
	public String index, pName, uuid, waypointName, dim;
	private MexDB db = DexUtils.waypointDB;

	public Waypoint(EntityPlayer player, String waypointName, Waypoints wp) {
		this.waypointName = waypointName;
		this.loc = new Location(player);

		if (wp.equals(Waypoints.HOME)) {
			this.uuid = player.getUniqueID().toString();
			this.pName = player.getDisplayNameString();
			this.index = uuid + ":" + waypointName;
		} else if (wp.equals(Waypoints.SPAWN)) {
			this.uuid = waypointName;
			this.pName = waypointName;
			this.index = waypointName;
		} else if (wp.equals(Waypoints.WARP)) {

		}
	}

	public boolean setWaypoint() {
		try {
			Entry addHome = new Entry(index);
			addHome.addValue("name", pName);
			addHome.addValue("dim", dim);
			addHome.addValue("x", loc.getX());
			addHome.addValue("y", loc.getY());
			addHome.addValue("z", loc.getZ());
			addHome.addValue("pitch", loc.getPitch());
			addHome.addValue("yaw", loc.getYaw());
			db.addEntry(addHome);
			return true;

		} catch (EmptyIndexException e) {
		}
		return false;
	}

	public boolean hasWaypoint() {
		return db.hasIndex(index);
	}

	public Location getWaypoint() {
		if (this.hasWaypoint()) {
			Location l = new Location();
			l.setDim(db.getString(index, "world"));
			l.setX(db.getDouble(index, "x"));
			l.setY(db.getDouble(index, "y"));
			l.setZ(db.getDouble(index, "z"));
			l.setPitch(db.getFloat(index, "pitch"));
			l.setYaw(db.getFloat(index, "yaw"));
			return l;
		}

		return null;

	}

	public boolean deleteWaypoint() {
		return db.removeEntry(index);
	}

	// TODO implement home list
	public void getHomeList() {
	}

	public String getPlayerName() {
		return this.pName;
	}

	public String getPlayerUUID() {
		return this.uuid;
	}

	public String getWaypointName() {
		return this.waypointName;
	}

	public void destroy() {
		this.pName = null;
		this.waypointName = null;
		this.index = null;
		this.loc = null;
		this.uuid = null;
		this.db = null;
	}

	public enum Waypoints {
		HOME, SPAWN, WARP
	}

}
