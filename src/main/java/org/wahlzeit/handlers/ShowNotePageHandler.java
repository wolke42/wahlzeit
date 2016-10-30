/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.handlers;

import org.wahlzeit.model.AccessRights;
import org.wahlzeit.model.UserSession;
import org.wahlzeit.utils.StringUtil;
import org.wahlzeit.webparts.WebPart;

import java.util.Map;

/**
 * A handler class for a specific web page.
 */
public class ShowNotePageHandler extends AbstractWebPageHandler {

	/**
	 *
	 */
	public ShowNotePageHandler() {
		initialize(PartUtil.SHOW_NOTE_PAGE_FILE, AccessRights.GUEST);
	}

	/**
	 *
	 */
	@Override
	protected boolean isWellFormedGet(UserSession us, String link, Map args) {
		return hasSavedMessage(us);
	}

	/**
	 *
	 */
	@Override
	protected void makeWebPageBody(UserSession us, WebPart page) {
		String heading = us.getHeading();
		heading = StringUtil.isNullOrEmptyString(heading) ? us.getClient().getLanguageConfiguration().getThankYou()
				: heading;
		page.addString("noteHeading", heading);

		page.addString("note", us.getMessage());
	}

}
