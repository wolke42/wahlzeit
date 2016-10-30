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
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.webparts.WebPart;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A superclass for handling forms.
 */
public abstract class AbstractWebFormHandler extends AbstractWebPartHandler implements WebFormHandler {

	private static final Logger log = Logger.getLogger(AbstractWebFormHandler.class.getName());

	/**
	 *
	 */
	@Override
	protected void initialize(String partTmplName, AccessRights neededRights) {
		super.initialize(partTmplName, neededRights);
	}

	/**
	 *
	 */
	@Override
	public WebPart makeWebPart(UserSession us) {
		WebPart result = createWebPart(us);
		doMakeWebPart(us, result);
		return result;
	}

	/**
	 *
	 */
	protected abstract void doMakeWebPart(UserSession us, WebPart part);

	/**
	 *
	 */
	@Override
	public final String handlePost(UserSession us, Map args) {
		String emailAddress = us.getClient().getEmailAddress().asString();
		if (!hasAccessRights(us, args)) {
			log.warning(LogBuilder.createSystemMessage().addParameter("insufficient rights for POST from", emailAddress)
					.toString());
			return getIllegalAccessErrorPage(us);
		}

		if (!isWellFormedPost(us, args)) {
			log.warning(LogBuilder.createSystemMessage().addParameter("received ill-formed POST from", emailAddress)
					.toString());
			return getIllegalArgumentErrorPage(us);
		}

		try {
			// may throw Exception
			return doHandlePost(us, args);
		} catch (Throwable t) {
			log.warning(LogBuilder.createSystemMessage().addException("Handle post failed", t).toString());
			return getInternalProcessingErrorPage(us);
		}
	}

	/**
	 * @methodtype boolean-query
	 */
	protected boolean isWellFormedPost(UserSession us, Map args) {
		return true;
	}

	/**
	 *
	 */
	protected String doHandlePost(UserSession us, Map args) {
		return PartUtil.DEFAULT_PAGE_NAME;
	}

}
