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
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoCase;
import org.wahlzeit.model.PhotoCaseManager;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoStatus;
import org.wahlzeit.model.UserSession;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.utils.HtmlUtil;
import org.wahlzeit.utils.StringUtil;
import org.wahlzeit.webparts.WebPart;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A handler class for a specific web form.
 */
public class EditPhotoCaseFormHandler extends AbstractWebFormHandler {

	private static final Logger log = Logger.getLogger(EditPhotoCaseFormHandler.class.getName());

	/**
	 *
	 */
	public EditPhotoCaseFormHandler() {
		initialize(PartUtil.EDIT_PHOTO_CASE_FORM_FILE, AccessRights.MODERATOR);
	}

	/**
	 *
	 */
	@Override
	protected void doMakeWebPart(UserSession us, WebPart part) {
		PhotoCase photoCase = us.getPhotoCase();
		Photo photo = photoCase.getPhoto();

		part.addString(Photo.THUMB, getPhotoThumb(us, photo));

		String id = String.valueOf(photoCase.getId());
		part.addString(DataObject.ID, id);

		String description = getPhotoSummary(us, photo);
		part.maskAndAddString(Photo.DESCRIPTION, description);

		String tags = photo.getTags().asString();
		tags = !StringUtil.isNullOrEmptyString(tags) ? tags : us.getClient().getLanguageConfiguration().getNoTags();
		part.maskAndAddString(Photo.TAGS, tags);

		String photoId = photo.getId().asString();
		part.addString(Photo.LINK, HtmlUtil.asHref(getResourceAsRelativeHtmlPathString(photoId)));

		part.addString(PhotoCase.FLAGGER, photoCase.getFlagger());
		part.addString(PhotoCase.REASON,
				us.getClient().getLanguageConfiguration().asValueString(photoCase.getReason()));
		part.addString(PhotoCase.EXPLANATION, photoCase.getExplanation());
	}

	/**
	 *
	 */
	@Override
	protected String doHandlePost(UserSession us, Map args) {
		String id = us.getAndSaveAsString(args, DataObject.ID);
		PhotoCaseManager pcm = PhotoCaseManager.getInstance();

		PhotoCase photoCase = pcm.getPhotoCase(PhotoId.getIdFromString(id));
		Photo photo = photoCase.getPhoto();
		PhotoStatus status = photo.getStatus();
		if (us.isFormType(args, "unflag")) {
			status = status.asFlagged(false);
		} else if (us.isFormType(args, "moderate")) {
			status = status.asModerated(true);
		} else { // something wrong?
			return PartUtil.SHOW_PHOTO_CASES_PAGE_NAME;
		}

		photo.setStatus(status);

		log.info(LogBuilder.createUserMessage().addAction("EditPhotoCase")
				.addParameter("Photo", photo.getId().asString()).toString());

		photoCase.setDecided();
		pcm.removePhotoCase(photoCase);

		log.info(LogBuilder.createUserMessage().addAction("EditPhotoCase").addParameter("PhotoCase", photoCase.getId())
				.toString());

		return PartUtil.SHOW_PHOTO_CASES_PAGE_NAME;
	}

}
