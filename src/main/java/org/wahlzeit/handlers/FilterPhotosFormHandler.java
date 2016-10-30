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
import org.wahlzeit.model.PhotoFilter;
import org.wahlzeit.model.Tags;
import org.wahlzeit.model.UserSession;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.StringUtil;
import org.wahlzeit.webparts.WebPart;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A handler class for a specific web form.
 */
public class FilterPhotosFormHandler extends AbstractWebFormHandler {

	private static final Logger log = Logger.getLogger(FilterPhotosFormHandler.class.getName());

	/**
	 *
	 */
	public FilterPhotosFormHandler() {
		initialize(PartUtil.FILTER_PHOTOS_FORM_FILE, AccessRights.GUEST);
	}

	/**
	 *
	 */
	@Override
	protected void doMakeWebPart(UserSession us, WebPart part) {
		PhotoFilter filter = us.getPhotoFilter();

		part.maskAndAddString(PhotoFilter.USER_NAME, filter.getUserName());
		part.maskAndAddString(PhotoFilter.TAGS, filter.getTags().asString());
	}

	/**
	 *
	 */
	@Override
	protected String doHandlePost(UserSession us, Map args) {
		PhotoFilter filter = us.getPhotoFilter();

		String un = us.getAsString(args, PhotoFilter.USER_NAME);
		if (StringUtil.isLegalUserName(un)) {
			filter.setUserName(un);
		}

		String tags = us.getAsString(args, PhotoFilter.TAGS);
		if (StringUtil.isLegalTagsString(tags)) {
			filter.setTags(new Tags(tags));
		}

		log.info(LogBuilder.createUserMessage().addAction("Filter Photos")
				.addParameter("Tags", filter.getTags().asString()).toString());

		return PartUtil.SHOW_PHOTO_PAGE_NAME;
	}

}
