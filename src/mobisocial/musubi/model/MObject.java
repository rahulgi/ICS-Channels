/*
 * Copyright (C) 2011 The Stanford MobiSocial Laboratory
 *
 * This file is part of Musubi, a mobile social network.
 *
 *  This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package mobisocial.musubi.model;

import mobisocial.socialkit.musubi.DbObj;

/**
 * <p>DO NOT USE AS A REPRESENTATION OF A MUSUBI OBJ.
 * <ul>
 * <li>Obj is an interface for basic Musubi content.
 * <li>MemObj is a concrete implementation stored in memory.
 * <li>SignedObj represents an obj that has been signed for sending by some user.
 * <li>DbObj represents an obj that has been sent or received and is held
 * in Musubi's database.
 * </ul></p>
 * 
 * <p>Note that this class used as both a representation of Objs, and a set of
 * utility methods and constants. Only the use as an Obj is deprecated,
 * the rest will be moved to a new class.</p>
 */
public class MObject {
    public static final String TABLE = DbObj.TABLE;

    public static final String COL_ID = DbObj.COL_ID;

    /* link to the Feed table that specifies where this obj goes */
    public static final String COL_FEED_ID = DbObj.COL_FEED_ID;

    /* sender */
    public static final String COL_IDENTITY_ID = DbObj.COL_IDENTITY_ID;
    /* sender device */
    public static final String COL_DEVICE_ID = DbObj.COL_DEVICE_ID;

    public static final String COL_PARENT_ID = DbObj.COL_PARENT_ID;

    public static final String COL_APP_ID = DbObj.COL_APP_ID;

    public static final String COL_TIMESTAMP = DbObj.COL_TIMESTAMP;

    public static final String COL_UNIVERSAL_HASH = DbObj.COL_UNIVERSAL_HASH;

    public static final String COL_SHORT_UNIVERSAL_HASH = DbObj.COL_SHORT_UNIVERSAL_HASH;

    public static final String COL_TYPE = DbObj.COL_TYPE;

    public static final String COL_JSON = DbObj.COL_JSON;

    public static final String COL_RAW = DbObj.COL_RAW;

    public static final String COL_INT_KEY = DbObj.COL_INT_KEY;

    public static final String COL_STRING_KEY = DbObj.COL_STRING_KEY;

    public static final String COL_LAST_MODIFIED_TIMESTAMP = DbObj.COL_LAST_MODIFIED_TIMESTAMP;

	public static final String COL_ENCODED_ID = DbObj.COL_ENCODED_ID;

	public static final String COL_DELETED = DbObj.COL_DELETED;

	public static final String COL_RENDERABLE = DbObj.COL_RENDERABLE;

	public static final String COL_PROCESSED = "processed";

	public long id_;
	public long feedId_;
    public long identityId_;
    public long deviceId_;
    public Long parentId_;
    public long appId_;
    public long timestamp_;
    public byte[] universalHash_;
    public Long shortUniversalHash_;
    public String type_;
	public String json_;
	public byte[] raw_;
    public Integer intKey_;
    public String stringKey_;
	public long lastModifiedTimestamp_;
	public Long encodedId_;
	public boolean deleted_;
	public boolean renderable_;
	public boolean processed_;
}